package com.yaojiafeng.tx.message.support;

import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;

/**
 * 发送客户端路由器
 *
 * @author yaojiafeng
 * @since 2020/7/28 7:52 下午
 */
public interface SendClientRouter {
    /**
     * 构造Key
     *
     * @return
     */
    String key();

    TxMessageTypeEnum messageType();
}
