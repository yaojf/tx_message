package com.yaojiafeng.tx.message.dal.mapper;

import com.yaojiafeng.tx.message.dal.entity.TxMessage;
import com.yaojiafeng.tx.message.dal.query.TxMessageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yaojiafeng
 * @since 2020/7/28 5:32 下午
 */
public interface TxMessageMapper {

    int insert(TxMessage txMessage);

    TxMessage selectByPrimaryKey(Long id);

    int updateMessageStatus(@Param("id") Long id, @Param("status") int status, @Param("expand") String expand);

    int deleteByIds(List<Long> ids);

    List<TxMessage> queryMessage(TxMessageQuery txMessageQuery);
}