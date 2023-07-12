package com.fly.trans4j.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 谢飞
 * @since 2023/6/27 17:51
 */
public class TransHolder {

    private final static ThreadLocal<Map<String, Map<String, Object>>> holder = ThreadLocal.withInitial(HashMap::new);


    public static void set(Object obj, String key, Object value) {
        String namespace = getNamespace(obj);
        Map<String, Object> transMap = Optional.ofNullable(getNamespaceTransMap(namespace)).orElse(new HashMap<>());
        transMap.put(key, value);
        setNamespaceTransMap(namespace, transMap);
    }

    public static Map<String, Object> get(Object obj) {
        String namespace = getNamespace(obj);
        return holder.get().get(namespace);
    }

    public static void remove() {
        holder.remove();
    }

    private static void setNamespaceTransMap(String namespace, Map<String, Object> transMap) {
        Map<String, Map<String, Object>> map = holder.get();
        map.put(namespace, transMap);
    }

    private static Map<String, Object> getNamespaceTransMap(String namespace) {
        return holder.get().get(namespace);
    }


    private static String getNamespace(Object obj) {
        return obj.getClass().getName() + obj.hashCode();
    }
}
