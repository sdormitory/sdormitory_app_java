package cn.sdormitory.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * ClassName: BeanUtils
 */
@Slf4j
public class BeanUtils {

    /**
     * 普通对象转换
     *
     * @param targetClass 目标对象类型，必须含有无参构造函数，且目标对象和被转换对象如果有相同名称字段，则2个字段的类型必须一致
     * @param source      被转换的对象
     */
    public static <T> T transform(Class<T> targetClass, Object source) {
        if (source == null) {
            return null;
        }
        try {
            String jsonSource = JSON.toJSONString(source);
            return JSONObject.parseObject(jsonSource, targetClass);
        } catch (Exception e) {
            log.error("对象转换出错：目标对象类型:{}, 被转换的对象类型:{}, 被转换的对象值:{}", targetClass, source.getClass(),
                    JSON.toJSONString(source, SerializerFeature.WriteMapNullValue), e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 普通对象集合转换
     *
     * @param targetClass 目标对象类型，必须含有无参构造函数，且目标对象和被转换对象如果有相同名称字段，则2个字段的类型必须一致
     * @param listSource  被转换的对象
     */
    public static <T> List<T> transformList(Class<T> targetClass, List<?> listSource) {
        if (listSource == null) {
            return null;
        }
        try {
            String jsonSource = JSON.toJSONString(listSource);
            return JSONArray.parseArray(jsonSource, targetClass);
        } catch (Exception e) {
            log.error("对象转换出错：目标对象类型:{}, 被转换的对象类型:{}, 被转换的对象值:{}", targetClass, listSource.getClass(),
                    JSON.toJSONString(listSource, SerializerFeature.WriteMapNullValue), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

}
