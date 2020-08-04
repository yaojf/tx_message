package com.yaojiafeng.tx.message.task;

import com.yaojiafeng.tx.message.service.TxMessageService;
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
 * @since 2020/7/29 4:30 下午
 */
public class TxMessageDeleteTask implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(TxMessageDeleteTask.class);

    @Autowired
    private TxMessageService txMessageService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        try {
            txMessageService.deleteSuccessMessage();
        } catch (Exception ex) {
            logger.error("删除发送成功的消息异常", ex);
        }
        // 无论如何都提交
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
