package com.fly.trans4j.util;

import com.fly.trans4j.annotation.TransVO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 谢飞
 * @since 2023/6/27 17:51
 */
public class TransHolder {

    private final static ThreadLocal<Map<String, Map<String, Object>>> holder = ThreadLocal.withInitial(HashMap::new);


    public static void set(TransVO vo, String key, Object value) {
        String namespace = getNamespace(vo);
        Map<String, Object> transMap = Optional.ofNullable(getNamespaceTransMap(namespace)).orElse(new HashMap<>());
        transMap.put(key, value);
        setNamespaceTransMap(namespace, transMap);
    }

    public static Map<String, Object> get(TransVO vo) {
        String namespace = getNamespace(vo);
        return holder.get().get(namespace);
    }

    public static void remove() {
        holder.remove();
    }

    // ---------------------------------------------------------------------------------------------- private method start

    private static void setNamespaceTransMap(String namespace, Map<String, Object> transMap) {
        Map<String, Map<String, Object>> map = holder.get();
        map.put(namespace, transMap);
    }

    private static Map<String, Object> getNamespaceTransMap(String namespace) {
        return holder.get().get(namespace);
    }


    private static String getNamespace(TransVO vo) {
        return vo.getClass().getName() + vo.transUnique();
    }
}
