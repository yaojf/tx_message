package com.yaojiafeng.tx.message.domain.dto.mq;

import com.alibaba.fastjson.JSONObject;
import com.yaojiafeng.tx.message.domain.converter.ITxMessageConverter;
import com.yaojiafeng.tx.message.domain.converter.mq.MQTxMessageConverter;
import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;

import java.io.Serializable;

/**
 * mq消息类型
 *
 * @author yaojiafeng
 * @since 2020/7/28 5:53 下午
 */
public class MQTxMessage extends BaseTxMessage implements Serializable {

    private static final long serialVersionUID = 7478025070182799758L;

    /**
     * 消息topic
     */
    private String topic;

    /**
     * 消息标识
     */
    private String tag;

    /**
     * 消息key
     */
    private String key;

    /**
     * 消息业务对象
     */
    private String obj;

    /**
     * 消息发送结果
     */
    private Object sendResult;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public Object getSendResult() {
        return sendResult;
    }

    public void setSendResult(Object sendResult) {
        this.sendResult = sendResult;
    }

    @Override
    public String getMessageContent() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MQTxMessageConverter.TOPIC, this.getTopic());
        jsonObject.put(MQTxMessageConverter.TAG, this.getTag());
        jsonObject.put(MQTxMessageConverter.KEY, this.getKey());
        jsonObject.put(MQTxMessageConverter.OBJ, this.getObj());
        jsonObject.put(ITxMessageConverter.ASYNC, this.getAsync());
        return jsonObject.toJSONString();
    }

    @Override
    public String getExpand() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MQTxMessageConverter.SEND_RESULT, this.getSendResult());
        return jsonObject.toJSONString();
    }

}
