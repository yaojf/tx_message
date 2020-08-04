package com.yaojiafeng.tx.message.service;


import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageStatusEnum;

import java.util.List;

/**
 * @author yaojiafeng
 * @since 2020/7/28 7:15 下午
 */
public interface TxMessageService {

    /**
     * 插入消息
     *
     * @param baseTxMessage
     * @return
     */
    int insertMessage(BaseTxMessage baseTxMessage);

    /**
     * 更新消息状态
     *
     * @param id
     * @param messageStatus
     * @param expand
     * @return
     */
    int updateMessageStatus(Long id, TxMessageStatusEnum messageStatus, String expand);

    /**
     * 查询需要重试发送的消息
     *
     * @return
     */
    List<BaseTxMessage> queryRetryMessage();

    /**
     * 删除发送成功的消息
     *
     * @return
     */
    int deleteSuccessMessage();

    /**
     * 根据主键查询
     *
     * @param messageId
     * @return
     */
    BaseTxMessage getById(Long messageId);
}
