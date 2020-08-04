package com.yaojiafeng.tx.message.domain.dto;

import com.yaojiafeng.tx.message.enums.TxMessageStatusEnum;
import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * 抽象不同的消息类型基类
 *
 * @author yaojiafeng
 * @since 2020/7/28 5:46 下午
 */
public abstract class BaseTxMessage implements Serializable {

    private static final long serialVersionUID = -7248168706389831378L;

    private Long id;

    private TxMessageTypeEnum messageType;

    private Integer retryCount;

    private Integer maxRetryCount;

    private TxMessageStatusEnum status;

    private String appName;

    private String appEnv;

    private Date createTime;

    private Date editTime;

    private String editor;

    private String creator;

    /**
     * 是否异步发送消息
     */
    private Boolean async;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TxMessageTypeEnum getMessageType() {
        return messageType;
    }

    public void setMessageType(TxMessageTypeEnum messageType) {
        this.messageType = messageType;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getMaxRetryCount() {
        return maxRetryCount;
    }

    public void setMaxRetryCount(Integer maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    public TxMessageStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TxMessageStatusEnum status) {
        this.status = status;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public abstract String getMessageContent();

    public abstract String getExpand();

}
