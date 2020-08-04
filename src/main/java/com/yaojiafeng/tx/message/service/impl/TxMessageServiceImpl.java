package com.yaojiafeng.tx.message.service.impl;

import com.yaojiafeng.tx.message.config.TxMessageConfig;
import com.yaojiafeng.tx.message.config.TxMessageDisconf;
import com.yaojiafeng.tx.message.dal.entity.TxMessage;
import com.yaojiafeng.tx.message.dal.mapper.TxMessageMapper;
import com.yaojiafeng.tx.message.dal.query.TxMessageQuery;
import com.yaojiafeng.tx.message.domain.converter.TxMessageConverterManager;
import com.yaojiafeng.tx.message.domain.dto.BaseTxMessage;
import com.yaojiafeng.tx.message.enums.TxMessageStatusEnum;
import com.yaojiafeng.tx.message.service.TxMessageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yaojiafeng
 * @since 2020/7/28 7:16 下午
 */
@Service
public class TxMessageServiceImpl implements TxMessageService {

    @Autowired
    private TxMessageConverterManager txMessageConverterManager;

    @Autowired
    private TxMessageMapper txMessageMapper;

    @Override
    public int insertMessage(BaseTxMessage baseTxMessage) {
        TxMessage txMessage = txMessageConverterManager.toTxMessage(baseTxMessage);
        int result = txMessageMapper.insert(txMessage);
        baseTxMessage.setId(txMessage.getId());
        return result;
    }

    @Override
    public int updateMessageStatus(Long id, TxMessageStatusEnum messageStatus, String expand) {
        return txMessageMapper.updateMessageStatus(id, messageStatus.getStatus(), expand);
    }

    @Override
    public List<BaseTxMessage> queryRetryMessage() {
        Date now = new Date();
        Date endDate = DateUtils.addMinutes(now, 0 - TxMessageDisconf.getRetryMessageDelay());

        TxMessageQuery txMessageQuery = new TxMessageQuery();
        txMessageQuery.setEndEditTime(endDate);
        txMessageQuery.setStatusList(Arrays.asList(TxMessageStatusEnum.UN_SEND.getStatus(), TxMessageStatusEnum.SEND_FAIL.getStatus()));
        txMessageQuery.setAppName(TxMessageConfig.getAppName());
        txMessageQuery.setAppEnv(TxMessageConfig.getAppEnv());
        txMessageQuery.setLimit(TxMessageDisconf.getRetryMessageCount());
        txMessageQuery.setQueryRetry(true);
        List<TxMessage> txMessageList = txMessageMapper.queryMessage(txMessageQuery);
        if (CollectionUtils.isEmpty(txMessageList)) {
            return Collections.emptyList();
        }
        return txMessageConverterManager.toMessageList(txMessageList);
    }

    @Override
    public int deleteSuccessMessage() {
        TxMessageQuery txMessageQuery = new TxMessageQuery();
        Date now = new Date();
        Date endDate = DateUtils.addDays(now, 0 - TxMessageDisconf.getMessageDeleteExpireDays());
        txMessageQuery.setEndEditTime(endDate);
        txMessageQuery.setStatusList(Arrays.asList(TxMessageStatusEnum.SEND_SUCCESS.getStatus()));
        txMessageQuery.setAppName(TxMessageConfig.getAppName());
        txMessageQuery.setAppEnv(TxMessageConfig.getAppEnv());
        txMessageQuery.setLimit(TxMessageDisconf.getMessageDeleteCount());
        List<TxMessage> txMessageList = txMessageMapper.queryMessage(txMessageQuery);
        if (CollectionUtils.isEmpty(txMessageList)) {
            return 0;
        }
        return txMessageMapper.deleteByIds(txMessageList.stream().map(TxMessage::getId).collect(Collectors.toList()));
    }

    @Override
    public BaseTxMessage getById(Long messageId) {
        if (messageId == null) {
            return null;
        }
        TxMessage txMessage = txMessageMapper.selectByPrimaryKey(messageId);
        return txMessageConverterManager.toMessage(txMessage);
    }
}
