package com.yaojiafeng.tx.message.support;

import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaojiafeng
 * @since 2020/7/29 4:56 下午
 */
@Component
public class SendClientAdapterManager implements InitializingBean {

    @Autowired
    private List<SendClientAdapter> sendClientAdapterList;

    private Map<TxMessageTypeEnum, SendClientAdapter> sendClientAdapterMap = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isNotEmpty(sendClientAdapterList)) {
            for (SendClientAdapter sendClientAdapter : sendClientAdapterList) {
                sendClientAdapterMap.put(sendClientAdapter.messageType(), sendClientAdapter);
            }
        }
    }

    public void sendMessage(BaseTxMessage baseTxMessage) {
        if (baseTxMessage == null) {
            return;
        }
        SendClientAdapter sendClientAdapter = sendClientAdapterMap.get(baseTxMessage.getMessageType());
        sendClientAdapter.send(baseTxMessage);
    }

}
