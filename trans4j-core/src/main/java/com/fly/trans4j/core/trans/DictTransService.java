package com.fly.trans4j.core.trans;

import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.core.TransFactory;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 谢飞
 * @since 2023/6/12 11:45
 */
public class DictTransService extends AbstractTransService implements InitializingBean {
    @Override
    public TransType getType() {
        return TransType.DICT;
    }

    private Map<String, Map<String, String>> localCacheMap = new ConcurrentHashMap<>();


    public void refreshCache(String dictGroupCode, Map<String, String> dicMap) {
        localCacheMap.put(dictGroupCode, dicMap);
    }

    @Override
    public void trans(TransVO obj, List<Field> transFieldList) throws Exception {
        for (Field field : transFieldList) {
            field.setAccessible(true);
            Trans trans = field.getAnnotation(Trans.class);
            if (trans == null) {
                continue;
            }
            String key = trans.key();
            Map<String, String> map = localCacheMap.get(key);
            Object value = field.get(obj);
            String name = map.get(value + "");
            obj.getTransMap().put(field.getName() + "Name", name);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        TransFactory.register(getType(), this);
    }
}
