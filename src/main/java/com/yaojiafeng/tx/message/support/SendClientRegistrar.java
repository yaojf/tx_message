package com.yaojiafeng.tx.message.support;

/**
 * 不同客户端的注册器
 *
 * @author yaojiafeng
 * @since 2020/7/28 7:45 下午
 */
public interface SendClientRegistrar<Router extends SendClientRouter, SendClient> {

    /**
     * 注册
     *
     * @param router
     * @param sendClient
     * @return
     */
    boolean register(Router router, SendClient sendClient);

    /**
     * 获取
     *
     * @param router
     * @return
     */
    SendClient acquire(Router router);

}
