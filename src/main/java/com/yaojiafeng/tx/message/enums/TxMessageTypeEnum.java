package com.yaojiafeng.tx.message.enums;

/**
 * 消息类型枚举
 *
 * @author yaojiafeng
 * @since 2020/7/22 6:04 下午
 */
public enum TxMessageTypeEnum {
    MQ(1, "MQ");

    private int type;

    private String code;

    TxMessageTypeEnum(int type, String code) {
        this.type = type;
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public static TxMessageTypeEnum getByType(int type) {
        for (TxMessageTypeEnum messageType : values()) {
            if (messageType.type == type) {
                return messageType;
            }
        }
        return null;
    }
}
