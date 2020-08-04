package com.yaojiafeng.tx.message.transaction;

import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.support.SendClientAdapterManager;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

/**
 * spring事务回调适配器
 *
 * @author yaojiafeng
 * @since 2020/7/28 5:42 下午
 */
public abstract class AbstractTransactionSynchronizationAdapter<Message extends BaseTxMessage> extends TransactionSynchronizationAdapter {

    /**
     * 发送适配管理器
     */
    protected SendClientAdapterManager sendClientAdapterManager;

    /**
     * 发送的消息
     */
    protected Message message;

    public AbstractTransactionSynchronizationAdapter(SendClientAdapterManager sendClientAdapterManager, Message message) {
        this.sendClientAdapterManager = sendClientAdapterManager;
        this.message = message;
    }

    public SendClientAdapterManager getSendClientAdapterManager() {
        return sendClientAdapterManager;
    }

    public Message getMessage() {
        return message;
    }
}
