package com.fly.example.util;

import com.fly.trans4j.core.DataWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 谢飞
 * @since 2021/12/9 15:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> implements Serializable, DataWrapper {
    private static final long serialVersionUID = 1L;

    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = 500;

    /**
     * 响应是否成功
     */
    private boolean success;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    public static <T> R<T> success() {
        return result(null, SUCCESS, "执行成功");
    }

    public static <T> R<T> success(String msg) {
        return result(null, SUCCESS, msg);
    }

    public static <T> R<T> success(T data) {
        return result(data, SUCCESS, "执行成功");
    }

    public static <T> R<T> success(T data, String msg) {
        return result(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return result(null, FAIL, "执行失败");
    }

    public static <T> R<T> fail(String msg) {
        return result(null, FAIL, msg);
    }

    public static <T> R<T> state(boolean b) {
        return b ? R.success() : R.fail();
    }

    private static <T> R<T> result(T data, Integer code, String msg) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setSuccess(code.equals(SUCCESS));
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    @Override
    public Object getTransData() {
        return data;
    }
}
