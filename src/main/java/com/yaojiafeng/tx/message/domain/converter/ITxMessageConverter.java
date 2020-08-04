package com.yaojiafeng.tx.message.domain.converter;

import com.yaojiafeng.tx.message.dal.entity.TxMessage;
import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;

import java.util.List;

/**
 * @author yaojiafeng
 * @since 2020/7/29 10:43 上午
 */
public interface ITxMessageConverter<Message extends BaseTxMessage> {

    String ASYNC = "async";

    /**
     * BaseTxMessage列表转为TxMessage列表
     *
     * @param messageList
     * @return
     */
    List<TxMessage> toTxMessageList(List<Message> messageList);

    /**
     * TxMessage列表转为BaseTxMessage列表
     *
     * @param txMessageList
     * @return
     */
    List<Message> toMessageList(List<TxMessage> txMessageList);

    /**
     * BaseTxMessage转为TxMessage
     *
     * @param message
     * @return
     */
    TxMessage toTxMessage(Message message);

    /**
     * TxMessage转为BaseTxMessage
     *
     * @param txMessage
     * @return
     */
    Message toMessage(TxMessage txMessage);
}
