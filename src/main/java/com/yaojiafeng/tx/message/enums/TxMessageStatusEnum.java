package com.yaojiafeng.tx.message.enums;

/**
 * 消息状态枚举
 *
 * @author yaojiafeng
 * @since 2020/7/23 11:41 上午
 */
public enum TxMessageStatusEnum {

    UN_SEND(0, "未发送"),
    SEND_SUCCESS(1, "发送成功"),
    SEND_FAIL(2, "发送失败");


    private int status;

    private String desc;

    TxMessageStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static TxMessageStatusEnum getByStatus(int status) {
        for (TxMessageStatusEnum messageStatus : values()) {
            if (messageStatus.status == status) {
                return messageStatus;
            }
        }
        return null;
    }
}
