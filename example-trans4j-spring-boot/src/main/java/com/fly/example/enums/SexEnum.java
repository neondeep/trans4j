package com.fly.example.enums;

/**
 * 性别枚举
 *
 * @author 谢飞
 * @since 2020/5/29 9:32
 */
public enum SexEnum {
    /**
     * 未知
     */
    UN_KNOWN(0, "未知"),
    /**
     * 男
     */
    MALE(1, "男"),
    /**
     * 女
     */
    FEMALE(2, "女"),
    ;
    private final Integer value;
    private final String desc;

    SexEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static SexEnum getEnum(Integer value) {
        for (SexEnum e : SexEnum.values()) {
            if (value.equals(e.value)) {
                return e;
            }
        }
        throw new IllegalArgumentException("性别状态非法!");
    }

    public static String getDesc(Integer value) {
        for (SexEnum e : SexEnum.values()) {
            if (e.value.equals(value)) {
                return e.desc;
            }
        }
        return "";
    }
}
