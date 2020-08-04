package com.yaojiafeng.tx.message.utils;

/**
 * 有些参数无法强制绑定在方法里，通过ThreadLocal传递
 *
 * @author yaojiafeng
 * @since 2020/7/29 7:22 下午
 */
public class TxMessageContext {

    private static final ThreadLocal<TxMessageContext> LOCAL = new ThreadLocal<TxMessageContext>() {
        @Override
        protected TxMessageContext initialValue() {
            return new TxMessageContext();
        }
    };

    /**
     * 最大重试次数自定义传递
     */
    private Integer maxRetryCount;

    public Integer getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(Integer maxRetryCount) {
        if (maxRetryCount == null) {
            this.maxRetryCount = maxRetryCount;
            return;
        }
        if (maxRetryCount < 0 || maxRetryCount > 10) {
            throw new IllegalArgumentException("最大重试次数范围超限");
        }
        this.maxRetryCount = maxRetryCount;
    }

    /**
     * 是否异步发送消息
     */
    private Boolean async;

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public static TxMessageContext getContext() {
        return LOCAL.get();
    }

    public static void removeContext() {
        LOCAL.remove();
    }


}
