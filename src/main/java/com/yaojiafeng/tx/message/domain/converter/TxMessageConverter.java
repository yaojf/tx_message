package com.yaojiafeng.tx.message.domain.converter;

import com.yaojiafeng.tx.message.dal.entity.TxMessage;
import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BaseTxMessage转换类
 *
 * @author yaojiafeng
 * @since 2020/7/29 9:53 上午
 */
public abstract class TxMessageConverter<Message extends BaseTxMessage> implements ITxMessageConverter<Message> {

    @Autowired
    private TxMessageConverterManager txMessageConverterManager;

    @Override
    public final List<TxMessage> toTxMessageList(List<Message> messageList) {
        if (CollectionUtils.isEmpty(messageList)) {
            return Collections.emptyList();
        }
        return messageList.stream().map(TxMessageConverter.this::toTxMessage).collect(Collectors.toList());
    }

    @Override
    public final List<Message> toMessageList(List<TxMessage> txMessageList) {
        if (CollectionUtils.isEmpty(txMessageList)) {
            return Collections.emptyList();
        }
        return txMessageList.stream().map(TxMessageConverter.this::toMessage).collect(Collectors.toList());
    }

    protected abstract TxMessageTypeEnum messageType();

    @PostConstruct
    public final void register() {
        txMessageConverterManager.register(messageType(), this);
    }
}
