package com.yaojiafeng.tx.message.support.mq;

import com.yaojiafeng.tx.message.config.TxMessageConstants;
import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.service.TxMessageService;
import com.yaojiafeng.tx.message.support.SendClientAdapterManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author yaojiafeng
 * @since 2020/7/29 8:26 下午
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:tx-message-test.xml"})
@Transactional(rollbackFor = Exception.class)
@TransactionConfiguration(transactionManager = "transactionManagerTxMessage", defaultRollback = true)
public class TxMQSendClientProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(TxMQSendClientProxyTest.class);

    @Autowired
    private TxMQSendClientProxy txMQSendClientProxy;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private TxMessageService txMessageService;

    @Autowired
    private SendClientAdapterManager sendClientAdapterManager;

    @Test
    @Rollback(value = false)
    public void test() {
        transactionTemplate.execute(new TransactionCallback<String>() {
            @Override
            public String doInTransaction(TransactionStatus status) {
                Message message = new Message();
                message.setTopic("topic");
                message.setKeys("keys");
                try {
                    message.setBody("obj".getBytes(TxMessageConstants.UTF8));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                message.setTags("tags");
                SendResult sendResult = txMQSendClientProxy.send(message);
                System.out.println("消息ID=" + sendResult.getMsgId());
                return "success";
            }
        });
    }

    @Test
    @Rollback(value = false)
    public void test3() {
        txMessageService.deleteSuccessMessage();
    }

    @Test
    public void test4() {
        List<BaseTxMessage> baseTxMessageList = txMessageService.queryRetryMessage();
        Assert.assertTrue(baseTxMessageList.isEmpty());
    }

    @Test
    public void test5() {
        BaseTxMessage baseTxMessage = txMessageService.getById(14L);
        Assert.assertNull(baseTxMessage);
    }

    @Test
    public void test6() {
        List<BaseTxMessage> baseTxMessageList = txMessageService.queryRetryMessage();
        if (CollectionUtils.isEmpty(baseTxMessageList)) {
            return;
        }
        for (BaseTxMessage baseTxMessage : baseTxMessageList) {
            sendClientAdapterManager.sendMessage(baseTxMessage);
        }
    }

}
