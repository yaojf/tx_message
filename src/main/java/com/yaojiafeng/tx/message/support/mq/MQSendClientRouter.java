package com.yaojiafeng.tx.message.support.mq;

import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;
import com.yaojiafeng.tx.message.support.SendClientRouter;
import org.apache.commons.lang3.StringUtils;

/**
 * MQ发送客户端路由器
 *
 * @author yaojiafeng
 * @since 2020/7/28 7:54 下午
 */
public class MQSendClientRouter implements SendClientRouter {

    /**
     * 消息主题
     */
    private String topic;

    public MQSendClientRouter() {
    }

    public MQSendClientRouter(String topic) {
        this.topic = topic;
    }

    @Override
    public String key() {
        if (StringUtils.isEmpty(topic)) {
            return messageType().getCode();
        } else {
            return messageType().getCode() + "_" + topic;
        }
    }

    @Override
    public TxMessageTypeEnum messageType() {
        return TxMessageTypeEnum.MQ;
    }
}
