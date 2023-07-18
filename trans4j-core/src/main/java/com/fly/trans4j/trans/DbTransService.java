package com.fly.trans4j.trans;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.fly.trans4j.annotation.Trans;
import com.fly.trans4j.annotation.TransType;
import com.fly.trans4j.annotation.TransVO;
import com.fly.trans4j.core.TransFactory;
import com.fly.trans4j.util.Assert;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 谢飞
 * @since 2023/7/13 17:54
 */
public class DbTransService extends AbstractTransService implements InitializingBean {
    @Override
    public TransType getType() {
        return TransType.DB;
    }

    @Override
    public void trans(TransVO vo, List<Field> transFieldList) throws Exception {
        for (Field transField : transFieldList) {
            Trans trans = transField.getAnnotation(Trans.class);
            if (null == trans) {
                continue;
            }
            Assert.notNull(trans.target(), "db翻译时Trans注解的target不能为空");
            Assert.notEmpty(trans.fields(), "db翻译时Trans注解的fields不能为空");
            transField.setAccessible(true);
            Object fieldValue = transField.get(vo);

            if (null == fieldValue) {
                continue;
            }

            List<Serializable> serializableIdList = getSerializableIdList(fieldValue);
            if (serializableIdList.isEmpty()) {
                continue;
            }

            Class<?> target = trans.target();
            String[] fields = trans.fields();
            List<Map<String, Object>> targetDataList = new ArrayList<>();
            try (SqlSession sqlSession = SqlHelper.sqlSession(target)) {
                TableInfo tableInfo = TableInfoHelper.getTableInfo(target);
                Configuration configuration = tableInfo.getConfiguration();
                BaseMapper<?> mapper = (BaseMapper<?>) configuration.getMapper(Class.forName(tableInfo.getCurrentNamespace()), sqlSession);

                List<?> dataList = mapper.selectBatchIds(serializableIdList);
                for (Object data : dataList) {
                    Map<String, Object> targetData = new HashMap<>();
                    for (Field targetField : data.getClass().getDeclaredFields()) {
                        targetField.setAccessible(true);
                        targetData.put(targetField.getName(), targetField.get(data));
                    }
                    targetDataList.add(targetData);
                }
            }

            String[] refs = trans.refs();
            if (refs.length > 0) {
                Assert.isTrue(refs.length == fields.length, "db翻译如果使用ref，那么refs和fields长度需要一样，字段映射严格按照顺序映射");
                for (int i = 0; i < fields.length; i++) {
                    String fieldName = fields[i];
                    Object fieldNameValue = getConcatenatedValue(targetDataList, fieldName);
                    ReflectUtil.setFieldValue(vo, refs[i], fieldNameValue);
                }
            } else {
                for (String fieldName : fields) {
                    proxySet(vo, fieldName, getConcatenatedValue(targetDataList, fieldName));
                }
            }
        }
    }


    /**
     * 获取id列表
     *
     * @param obj 对象
     * @return 列表
     */
    private List<Serializable> getSerializableIdList(Object obj) {
        if (obj instanceof List<?>) {
            List<?> idList = (List<?>) obj;
            if (!idList.isEmpty() && idList.get(0) instanceof Serializable) {
                return idList.stream().map(id -> (Serializable) id).collect(Collectors.toList());
            }
        }
        List<Serializable> commaSplitSerializableIdList = getCommaSplitSerializableIdList(obj);
        if (!commaSplitSerializableIdList.isEmpty()) {
            return commaSplitSerializableIdList;
        }

        if (null != obj) {
            List<Serializable> serializableList = new ArrayList<>();
            Serializable id = (Serializable) obj;
            serializableList.add(id);
            return serializableList;
        }

        return Collections.emptyList();

    }

    /**
     * 获取以逗号分割的字符串的id列表
     *
     * @param obj 对象
     * @return 列表
     */
    private List<Serializable> getCommaSplitSerializableIdList(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            String[] strArr = str.split(",");
            return Arrays.stream(strArr).map(id -> (Serializable) id).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * 获取以逗号分割的值
     *
     * @param targetDataList 目标数据列表
     * @param fieldName      字段名字
     * @return 以逗号分割的值
     */

    private String getConcatenatedValue(List<Map<String, Object>> targetDataList, String fieldName) {
        return targetDataList.stream().map(e -> e.get(fieldName)).filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(","));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TransFactory.register(getType(), this);
    }
}
