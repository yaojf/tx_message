package com.yaojiafeng.tx.message.dal.query;

import java.util.Date;
import java.util.List;

/**
 * @author yaojiafeng
 * @since 2020/7/29 3:16 下午
 */
public class TxMessageQuery {

    /**
     * 截止编辑时间
     */
    private Date endEditTime;

    /**
     * 状态列表
     */
    private List<Integer> statusList;

    /**
     * 应用
     */
    private String appName;

    /**
     * 环境
     */
    private String appEnv;

    /**
     * 分页限制
     */
    private int limit;

    /**
     * 是否查询重试消息
     */
    private boolean queryRetry;

    public Date getEndEditTime() {
        return endEditTime;
    }

    public void setEndEditTime(Date endEditTime) {
        this.endEditTime = endEditTime;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppEnv() {
        return appEnv;
    }

    public void setAppEnv(String appEnv) {
        this.appEnv = appEnv;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean getQueryRetry() {
        return queryRetry;
    }

    public void setQueryRetry(boolean queryRetry) {
        this.queryRetry = queryRetry;
    }
}
