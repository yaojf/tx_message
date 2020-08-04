package com.yaojiafeng.tx.message.support.mq;

import com.yaojiafeng.tx.message.config.TxMessageConstants;
import com.yaojiafeng.tx.message.domain.dto.mq.MQTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageStatusEnum;
import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;
import com.yaojiafeng.tx.message.support.AbstractSendClientAdapter;
import com.yaojiafeng.tx.message.support.SendClientRegistrar;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author yaojiafeng
 * @since 2020/7/28 7:22 下午
 */
@Component
public class MQSendClientAdapter extends AbstractSendClientAdapter<MQSendClientRouter, DefaultMQProducer, MQTxMessage> {

    private static final Logger logger = LoggerFactory.getLogger(MQSendClientAdapter.class);

    @Override
    public TxMessageTypeEnum messageType() {
        return TxMessageTypeEnum.MQ;
    }

    @Override
    protected MQSendClientRouter router(MQTxMessage message) {
        return new MQSendClientRouter(message.getTopic());
    }

    @Override
    protected SendClientRegistrar<MQSendClientRouter, DefaultMQProducer> register() {
        return MQSendClientRegistrar.getSingleton();
    }

    @Override
    protected void syncSend(DefaultMQProducer defaultMQProducer, MQTxMessage message) throws Exception {
        Message mqMessage = new Message();
        mqMessage.setTopic(message.getTopic());
        mqMessage.setKeys(message.getKey());
        mqMessage.setBody(message.getObj().getBytes(TxMessageConstants.UTF8));
        mqMessage.setTags(message.getTag());
        SendResult sendResult = defaultMQProducer.send(mqMessage);
        message.setSendResult(sendResult);
        TxMessageStatusEnum status = sendResult == null ? TxMessageStatusEnum.SEND_FAIL :
                sendResult.getSendStatus() == SendStatus.SEND_OK ? TxMessageStatusEnum.SEND_SUCCESS : TxMessageStatusEnum.SEND_FAIL;
        int result = txMessageService.updateMessageStatus(message.getId(), status, message.getExpand());
    }

}
