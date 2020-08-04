package com.yaojiafeng.tx.message.support.mq;

import com.yaojiafeng.tx.message.exception.TxMessageException;
import com.yaojiafeng.tx.message.support.SendClientRegistrar;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * MQ发送客户端的注册器,单例
 *
 * @author yaojiafeng
 * @since 2020/7/28 7:47 下午
 */
public class MQSendClientRegistrar implements SendClientRegistrar<MQSendClientRouter, DefaultMQProducer> {

    /**
     * 缓存MQ发送客户端
     */
    private final ConcurrentHashMap<String, DefaultMQProducer> mqSendClientCache = new ConcurrentHashMap<>();

    @Override
    public boolean register(MQSendClientRouter router, DefaultMQProducer defaultMQProducer) {
        DefaultMQProducer sendClient = mqSendClientCache.putIfAbsent(router.key(), defaultMQProducer);
        if (sendClient != null) {
            throw new TxMessageException("存在相同的客户端，Key=" + router.key());
        }
        return true;
    }

    @Override
    public DefaultMQProducer acquire(MQSendClientRouter router) {
        return mqSendClientCache.get(router.key());
    }

    private static MQSendClientRegistrar SINGLETON = new MQSendClientRegistrar();

    public static MQSendClientRegistrar getSingleton() {
        return SINGLETON;
    }
}
