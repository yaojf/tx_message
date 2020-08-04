package com.yaojiafeng.tx.message.task;

import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.service.TxMessageService;
import com.yaojiafeng.tx.message.support.SendClientAdapterManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * TOC定时任务
 *
 * @author yaojiafeng
 * @since 2020/7/29 4:33 下午
 */
public class TxMessageRetryTask implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(TxMessageRetryTask.class);

    @Autowired
    private TxMessageService txMessageService;

    @Autowired
    private SendClientAdapterManager sendClientAdapterManager;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            List<BaseTxMessage> baseTxMessageList = txMessageService.queryRetryMessage();
            if (CollectionUtils.isEmpty(baseTxMessageList)) {
                logger.info("没有需要重试的消息");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            for (BaseTxMessage baseTxMessage : baseTxMessageList) {
                sendClientAdapterManager.sendMessage(baseTxMessage);
            }
        } catch (Exception e) {
            logger.error("执行重试发送消息任务失败", e);
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
