package com.yaojiafeng.tx.message.transaction;

import com.alibaba.fastjson.JSON;
import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.support.SendClientAdapterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用spring事务回调实现
 *
 * @author yaojiafeng
 * @since 2020/7/28 7:04 下午
 */
public class CommonTransactionSynchronizationAdapter<Message extends BaseTxMessage> extends AbstractTransactionSynchronizationAdapter<Message> {

    private static final Logger logger = LoggerFactory.getLogger(CommonTransactionSynchronizationAdapter.class);

    public CommonTransactionSynchronizationAdapter(SendClientAdapterManager sendClientAdapterManager, Message message) {
        super(sendClientAdapterManager, message);
    }

    @Override
    public void afterCompletion(int status) {
        if (STATUS_COMMITTED == status) {
            logger.info("transaction committed，message={}", JSON.toJSONString(message));
            // 发送消息
            try {
                sendClientAdapterManager.sendMessage(message);
            } catch (Exception e) {
                // 若事务已提交，发送消息失败，则发送失败的消息等待定时任务重试发送
                logger.error(String.format("send message fail，message={}", JSON.toJSONString(message)), e);
            }
        } else if (STATUS_ROLLED_BACK == status) {
            logger.info("transaction rolled back，message={}", JSON.toJSONString(message));
        } else {
            logger.info("transaction unknown，message={}", JSON.toJSONString(message));
        }
    }

}
