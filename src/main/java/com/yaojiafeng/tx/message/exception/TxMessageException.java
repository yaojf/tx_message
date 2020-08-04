package com.yaojiafeng.tx.message.exception;

/**
 * 自定义异常，区分异常来源
 *
 * @author yaojiafeng
 * @since 2020/7/28 5:32 下午
 */
public class TxMessageException extends RuntimeException {

    public TxMessageException(String message) {
        super(message);
    }

    public TxMessageException(String message, Exception cause) {
        super(message, cause);
    }
}
