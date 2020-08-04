package com.yaojiafeng.tx.message.domain.converter.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yaojiafeng.tx.message.dal.entity.TxMessage;
import com.yaojiafeng.tx.message.domain.converter.ITxMessageConverter;
import com.yaojiafeng.tx.message.domain.converter.TxMessageConverter;
import com.yaojiafeng.tx.message.domain.dto.mq.MQTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageStatusEnum;
import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * MQTxMessage转化类
 *
 * @author yaojiafeng
 * @since 2020/7/29 9:55 上午
 */
@Component
public class MQTxMessageConverter extends TxMessageConverter<MQTxMessage> {

    public static final String SEND_RESULT = "sendResult";

    public static final String TOPIC = "topic";

    public static final String TAG = "tag";

    public static final String KEY = "key";

    public static final String OBJ = "obj";

    @Override
    public TxMessage toTxMessage(MQTxMessage message) {
        if (message == null) {
            return null;
        }
        TxMessage txMessage = new TxMessage();
        txMessage.setId(message.getId());
        txMessage.setMessageType(message.getMessageType().getType());
        txMessage.setMessageContent(message.getMessageContent());
        txMessage.setRetryCount(message.getRetryCount());
        txMessage.setMaxRetryCount(message.getMaxRetryCount());
        txMessage.setStatus(message.getStatus().getStatus());
        txMessage.setAppName(message.getAppName());
        txMessage.setAppEnv(message.getAppEnv());
        txMessage.setExpand(message.getExpand());
        txMessage.setCreateTime(message.getCreateTime());
        txMessage.setEditTime(message.getEditTime());
        txMessage.setCreator(message.getCreator());
        txMessage.setEditor(message.getEditor());
        return txMessage;
    }

    @Override
    public MQTxMessage toMessage(TxMessage txMessage) {
        if (txMessage == null) {
            return null;
        }
        MQTxMessage MQTxMessage = new MQTxMessage();
        MQTxMessage.setId(txMessage.getId());
        MQTxMessage.setMessageType(TxMessageTypeEnum.getByType(txMessage.getMessageType()));
        MQTxMessage.setRetryCount(txMessage.getRetryCount());
        MQTxMessage.setMaxRetryCount(txMessage.getMaxRetryCount());
        MQTxMessage.setStatus(TxMessageStatusEnum.getByStatus(txMessage.getStatus()));
        MQTxMessage.setAppName(txMessage.getAppName());
        MQTxMessage.setAppEnv(txMessage.getAppEnv());
        MQTxMessage.setCreateTime(txMessage.getCreateTime());
        MQTxMessage.setEditTime(txMessage.getEditTime());
        MQTxMessage.setCreator(txMessage.getCreator());
        MQTxMessage.setEditor(txMessage.getEditor());

        if (StringUtils.isNotEmpty(txMessage.getMessageContent())) {
            JSONObject jsonObject = JSON.parseObject(txMessage.getMessageContent());
            MQTxMessage.setTopic(jsonObject.getString(TOPIC));
            MQTxMessage.setTag(jsonObject.getString(TAG));
            MQTxMessage.setKey(jsonObject.getString(KEY));
            MQTxMessage.setObj(jsonObject.getString(OBJ));
            MQTxMessage.setAsync(jsonObject.getBoolean(ITxMessageConverter.ASYNC));
        }

        if (StringUtils.isNotEmpty(txMessage.getExpand())) {
            JSONObject jsonObject = JSON.parseObject(txMessage.getExpand());
            Object sendResult = jsonObject.get(SEND_RESULT);
            MQTxMessage.setSendResult(sendResult);
        }

        return MQTxMessage;
    }

    @Override
    protected TxMessageTypeEnum messageType() {
        return TxMessageTypeEnum.MQ;
    }
}
