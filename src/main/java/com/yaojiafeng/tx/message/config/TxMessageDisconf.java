package com.yaojiafeng.tx.message.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;

/**
 * 可能会频繁改动的放disconf
 *
 * @author yaojiafeng
 * @since 2020/7/29 3:22 下午
 */
@DisconfFile(filename = "TxMessage.properties")
public class TxMessageDisconf {

    /**
     * 删除成功消息的过期时间天数
     */
    private static int messageDeleteExpireDays = 7;

    @DisconfFileItem(name = "messageDeleteExpireDays", associateField = "messageDeleteExpireDays")
    public static int getMessageDeleteExpireDays() {
        return messageDeleteExpireDays;
    }

    public static void setMessageDeleteExpireDays(int messageDeleteExpireDays) {
        TxMessageDisconf.messageDeleteExpireDays = messageDeleteExpireDays;
    }

    /**
     * 一次删除成功消息的条数
     */
    private static int messageDeleteCount = 100;

    @DisconfFileItem(name = "messageDeleteCount", associateField = "messageDeleteCount")
    public static int getMessageDeleteCount() {
        return messageDeleteCount;
    }

    public static void setMessageDeleteCount(int messageDeleteCount) {
        TxMessageDisconf.messageDeleteCount = messageDeleteCount;
    }

    /**
     * 一次查询重试消息条数
     */
    private static int retryMessageCount = 100;

    @DisconfFileItem(name = "retryMessageCount", associateField = "retryMessageCount")
    public static int getRetryMessageCount() {
        return retryMessageCount;
    }

    public static void setRetryMessageCount(int retryMessageCount) {
        TxMessageDisconf.retryMessageCount = retryMessageCount;
    }

    /**
     * 重试消息查询编辑时间延迟分钟
     * 防止定时任务和线程发送一起执行的情况
     */
    private static int retryMessageDelay = 5;

    @DisconfFileItem(name = "retryMessageDelay", associateField = "retryMessageDelay")
    public static int getRetryMessageDelay() {
        return retryMessageDelay;
    }

    public static void setRetryMessageDelay(int retryMessageDelay) {
        TxMessageDisconf.retryMessageDelay = retryMessageDelay;
    }

    /**
     * 默认最大重试次数
     */
    private static int maxRetryCount = 3;

    @DisconfFileItem(name = "maxRetryCount", associateField = "maxRetryCount")
    public static int getMaxRetryCount() {
        return maxRetryCount;
    }

    public static void setMaxRetryCount(int maxRetryCount) {
        TxMessageDisconf.maxRetryCount = maxRetryCount;
    }
}
