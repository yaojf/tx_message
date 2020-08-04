package com.yaojiafeng.tx.message.support.mq;

import com.yaojiafeng.tx.message.config.TxMessageConfig;
import com.yaojiafeng.tx.message.config.TxMessageConstants;
import com.yaojiafeng.tx.message.config.TxMessageDisconf;
import com.yaojiafeng.tx.message.domain.dto.mq.MQTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageStatusEnum;
import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;
import com.yaojiafeng.tx.message.exception.TxMessageException;
import com.yaojiafeng.tx.message.service.TxMessageService;
import com.yaojiafeng.tx.message.support.SendClientAdapterManager;
import com.yaojiafeng.tx.message.transaction.CommonTransactionSynchronizationAdapter;
import com.yaojiafeng.tx.message.utils.TxMessageContext;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.UnsupportedEncodingException;

/**
 * MQ发送端的静态代理,需要代理的MQSendClient自行注入
 * 对于DefaultMQProducer需要的方法实现静态代理
 *
 * @author yaojiafeng
 * @since 2020/7/28 7:38 下午
 */
public class TxMQSendClientProxy extends DefaultMQProducer implements InitializingBean, DisposableBean {

    private static final Logger logger = LoggerFactory.getLogger(TxMQSendClientProxy.class);

    /**
     * 原生消息客户端
     */
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private TxMessageService txMessageService;

    @Autowired
    private SendClientAdapterManager sendClientAdapterManager;

    public void setDefaultMQProducer(DefaultMQProducer defaultMQProducer) {
        this.defaultMQProducer = defaultMQProducer;
    }

    @Override
    public SendResult send(Message msg) {
        return sendTxMessage(msg);
    }

    protected SendResult sendTxMessage(Message msg) {
        // 事务状态检查
        if (!TransactionSynchronizationManager.isActualTransactionActive()) {
            throw new TxMessageException("请在本地事务中发送消息");
        }

        // 事务消息构造
        MQTxMessage message = new MQTxMessage();
        message.setMessageType(TxMessageTypeEnum.MQ);
        message.setRetryCount(0);
        message.setMaxRetryCount(getMaxRetryCount());
        message.setStatus(TxMessageStatusEnum.UN_SEND);
        message.setTopic(msg.getTopic());
        message.setTag(msg.getTags());
        message.setKey(msg.getKeys());
        // MQ底层也是转成JSON字符串
        try {
            message.setObj(new String(msg.getBody(), TxMessageConstants.UTF8));
        } catch (UnsupportedEncodingException e) {
            throw new TxMessageException("消息体解码异常", e);
        }
        message.setAsync(getAsync());
        message.setAppName(TxMessageConfig.getAppName());
        message.setAppEnv(TxMessageConfig.getAppEnv());
        message.setCreator(TxMessageConstants.SYSTEM);
        message.setEditor(TxMessageConstants.SYSTEM);

        // 插入消息
        txMessageService.insertMessage(message);

        // 注册事务提交监听器
        TransactionSynchronizationManager.registerSynchronization(new CommonTransactionSynchronizationAdapter<MQTxMessage>(sendClientAdapterManager, message));

        SendResult sendResult = new SendResult();
        sendResult.setMsgId(String.valueOf(message.getId()));
        sendResult.setSendStatus(SendStatus.SEND_OK);
        return sendResult;
    }

    private Boolean getAsync() {
        if (TxMessageContext.getContext().getAsync() != null) {
            return TxMessageContext.getContext().getAsync();
        }
        return true;
    }

    private Integer getMaxRetryCount() {
        if (TxMessageContext.getContext().getMaxRetryCount() != null) {
            return TxMessageContext.getContext().getMaxRetryCount();
        }
        return TxMessageDisconf.getMaxRetryCount();
    }

    public void init() {
        if (defaultMQProducer == null) {
            throw new IllegalArgumentException("defaultMQProducer不能为空");
        }

        // 注册客户端
        MQSendClientRouter router = new MQSendClientRouter();
        MQSendClientRegistrar.getSingleton().register(router, defaultMQProducer);
    }

    @Override
    public void destroy() throws Exception {
        super.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
