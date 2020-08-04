package com.yaojiafeng.tx.message.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 不太会变的放配置
 *
 * @author yaojiafeng
 * @since 2020/7/29 3:22 下午
 */
@Component
public class TxMessageConfig implements InitializingBean {
    /**
     * 应用名
     */
    private static String APP_NAME;

    /**
     * 应用环境
     */
    private static String APP_ENV;

    @Value("${tx.message.appName}")
    void setAppName(String appName) {
        APP_NAME = appName;
    }

    @Value("${tx.message.appEnv}")
    void setAppEnv(String appEnv) {
        APP_ENV = appEnv;
    }

    public static String getAppName() {
        return APP_NAME;
    }

    public static String getAppEnv() {
        return APP_ENV;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isEmpty(APP_NAME)) {
            throw new IllegalArgumentException("tx.message.appName属性不能为空");
        }
        if (StringUtils.isEmpty(APP_ENV)) {
            throw new IllegalArgumentException("tx.message.appEnv属性不能为空");
        }
    }
}
