package com.yaojiafeng.tx.message.domain.converter;

import com.yaojiafeng.tx.message.dal.entity.TxMessage;
import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;
import com.yaojiafeng.tx.message.exception.TxMessageException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yaojiafeng
 * @since 2020/7/29 10:33 上午
 */
@Component
public class TxMessageConverterManager implements ITxMessageConverter<BaseTxMessage> {

    private Map<TxMessageTypeEnum, ITxMessageConverter> converterMap = new HashMap<>();

    @Override
    public List<TxMessage> toTxMessageList(List<BaseTxMessage> baseTxMessages) {
        if (CollectionUtils.isEmpty(baseTxMessages)) {
            return Collections.emptyList();
        }
        return baseTxMessages.stream().map(TxMessageConverterManager.this::toTxMessage).collect(Collectors.toList());
    }

    @Override
    public List<BaseTxMessage> toMessageList(List<TxMessage> txMessageList) {
        if (CollectionUtils.isEmpty(txMessageList)) {
            return Collections.emptyList();
        }
        return txMessageList.stream().map(TxMessageConverterManager.this::toMessage).collect(Collectors.toList());
    }

    @Override
    public TxMessage toTxMessage(BaseTxMessage message) {
        if (message == null) {
            return null;
        }
        return getConverter(message.getMessageType()).toTxMessage(message);
    }

    @Override
    public BaseTxMessage toMessage(TxMessage txMessage) {
        if (txMessage == null) {
            return null;
        }
        return getConverter(TxMessageTypeEnum.getByType(txMessage.getMessageType())).toMessage(txMessage);
    }

    public ITxMessageConverter getConverter(TxMessageTypeEnum messageType) {
        ITxMessageConverter converter = converterMap.get(messageType);
        if (converter == null) {
            throw new TxMessageException("找不到messageType=" + messageType.getCode() + "的转化器");
        }
        return converter;
    }

    public void register(TxMessageTypeEnum messageType, ITxMessageConverter converter) {
        converterMap.put(messageType, converter);
    }
}
