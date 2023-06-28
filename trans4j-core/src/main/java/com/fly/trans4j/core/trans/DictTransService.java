package com.fly.trans4j.core.trans;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransHolder;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.core.TransFactory;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

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


    public void refreshCache(String dictGroupCode, Map<String, String> dicMap) {
        localCacheMap.put(dictGroupCode, dicMap);
    }

    @Override
    public void trans(TransVO vo, List<Field> transFieldList) throws Exception {
        for (Field field : transFieldList) {
            field.setAccessible(true);
            Trans trans = field.getAnnotation(Trans.class);
            if (trans == null) {
                continue;
            }
            String dictKey = trans.key();
            String ref = trans.ref();

            if (StrUtil.isNotBlank(ref)) {
                Map<String, String> map = localCacheMap.get(dictKey);
                Object fieldValue = field.get(vo);
                String value = map.get(fieldValue + "");
                ReflectUtil.setFieldValue(vo, ref, value);
            } else {
                Map<String, Object> transMap = new HashMap<>();
                transMap.put("sexName", "张三" + ThreadLocalRandom.current().nextInt(100));
                TransHolder.set(transMap);
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        TransFactory.register(getType(), this);
    }
}
