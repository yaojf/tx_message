package com.yaojiafeng.tx.message.support;

import com.alibaba.fastjson.JSON;
import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.service.TxMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author yaojiafeng
 * @since 2020/7/28 7:05 下午
 */
public abstract class AbstractSendClientAdapter<Router extends SendClientRouter, SendClient, Message extends BaseTxMessage> implements SendClientAdapter<Message> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractSendClientAdapter.class);

    @Autowired
    protected TxMessageService txMessageService;

    @Autowired
    @Qualifier("messageAsyncSendThreadPool")
    protected ThreadPoolExecutor messageAsyncSendThreadPool;

    protected abstract Router router(Message message);

    protected abstract SendClientRegistrar<Router, SendClient> register();

    protected abstract void syncSend(SendClient sendClient, Message message) throws Exception;

    @Override
    public final void send(Message message) {
        Router router = router(message);
        SendClientRegistrar<Router, SendClient> registrar = register();
        SendClient sendClient = registrar.acquire(router);
        if (sendClient == null) {
            logger.warn("找不到SendClient，message=" + JSON.toJSONString(message));
            return;
        }
        Boolean async = message.getAsync();
        try {
            if (async != null && async) {
                messageAsyncSendThreadPool.execute(() -> {
                    try {
                        syncSend(sendClient, message);
                    } catch (Exception ex) {
                        logger.error("发送MQ消息失败，message=" + JSON.toJSONString(message), ex);
                    }
                });
            } else {
                syncSend(sendClient, message);
            }
        } catch (Exception ex) {
            logger.error("发送MQ消息失败，message=" + JSON.toJSONString(message), ex);
        }
    }
}
