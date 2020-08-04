package com.yaojiafeng.tx.message.support;

import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;

/**
 * 发送客户端的适配器
 * 包含事务消息表的操作和异步发送消息
 *
 * @author yaojiafeng
 * @since 2020/7/28 7:19 下午
 */
public interface SendClientAdapter<Message extends BaseTxMessage> {

    /**
     * 发送消息
     *
     * @param message
     */
    void send(Message message);

    TxMessageTypeEnum messageType();

}
