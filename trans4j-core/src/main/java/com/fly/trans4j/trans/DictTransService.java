package com.fly.trans4j.trans;

import cn.hutool.core.util.ReflectUtil;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.core.TransFactory;
import com.fly.trans4j.util.Assert;
import com.fly.trans4j.util.TransHolder;
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

    private final Map<String, Map<String, String>> localCacheMap = new ConcurrentHashMap<>();


    public void refreshCache(String dictType, Map<String, String> dicMap) {
        localCacheMap.put(dictType, dicMap);
    }

    public void clearCache() {
        localCacheMap.clear();
    }

    @Override
    public void trans(TransVO vo, List<Field> transFieldList) throws Exception {
        for (Field field : transFieldList) {
            field.setAccessible(true);
            Trans trans = field.getAnnotation(Trans.class);
            if (null == trans) {
                continue;
            }
            Assert.notNull(trans.key(), "dict翻译时Trans注解的key不能为空");
            String dictType = trans.key();
            String[] refs = trans.refs();
            String suffix = trans.suffix();
            Object fieldValue = field.get(vo);

            Map<String, String> map = localCacheMap.get(dictType);
            String transValue = map.get(fieldValue + "");

            if (refs.length > 0) {
                for (String ref : refs) {
                    ReflectUtil.setFieldValue(vo, ref, transValue);
                }
            } else {
                TransHolder.set(vo, field.getName() + suffix, transValue);
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        TransFactory.register(getType(), this);
    }
}
