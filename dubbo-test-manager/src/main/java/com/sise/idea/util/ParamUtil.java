package com.sise.idea.util;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.PojoUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sise.idea.common.SysException;
import com.sise.idea.em.ExceptionEnums;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;
import java.util.*;

import static com.sise.idea.em.ExceptionEnums.INTERFACE_NOT_FOUND;

/**
 * 处理参数的工具类
 *
 * @author idea
 * @data 2019/4/4
 */
@Slf4j
public class ParamUtil {

    /**
     * 截取参数类型的尾缀
     *
     * @return
     */
    public static String subStringLastIndex(String str, char point) {
        return str.substring(str.lastIndexOf(point) + 1);
    }


    /**
     * 获取url里面的附属信息
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static HashMap<String, String> getAttachmentFromUrl(URL url) throws Exception {

        String interfaceName = url.getParameter(Constants.INTERFACE_KEY, "");
        if (StringUtils.isEmpty(interfaceName)) {
            throw new SysException(INTERFACE_NOT_FOUND);
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(Constants.PATH_KEY, interfaceName);
        map.put(Constants.VERSION_KEY, url.getParameter(Constants.VERSION_KEY));
        map.put(Constants.GROUP_KEY, url.getParameter(Constants.GROUP_KEY));
        return map;
    }

    /**
     * 创建json格式的参数字符串
     *
     * @param method
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static String createJsonParamterStr(Method method) {
        List<Object> objects = new ArrayList<>();
        try {
            for (Parameter param : method.getParameters()) {
                objects.add(initObject(param.getType(), param.getParameterizedType()));
            }
        } catch (IllegalAccessException e) {
            log.error("[ParamUtil]格式转换异常{}", e);
        } catch (InstantiationException e) {
            log.error("[ParamUtil]数据初始化转换异常{}", e);
        }
        String json = JSON.toJSONString(objects, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        return json;
    }


    /**
     * 初始化一些原始的参数类型
     *
     * @param clazz
     * @param type
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static Object initObject(Class<?> clazz, Type type) throws IllegalAccessException, InstantiationException {

        log.debug("开始参数模板初始化 {}", clazz.getTypeName());

        if (clazz == Integer.class || clazz == int.class) {

            return RandomUtils.nextInt(0, 1000);

        } else if (clazz == String.class) {

            String base = UUID.randomUUID().toString();
            return base.substring(RandomUtils.nextInt(1, base.length()));

        } else if (clazz == Long.class || clazz == long.class) {

            return RandomUtils.nextLong(0, 1000);

        } else if (clazz == Short.class || clazz == short.class) {

            return RandomUtils.nextInt(0, 100);

        } else if (clazz == Date.class) {

            return new Date();

        } else if (clazz == List.class) {

            if (null != type) {
                //暂时只支持arraylist类型
                return new ArrayList<>();
            }

        } else if (clazz == Map.class) {

            return new HashMap<>();

        } else if (clazz == Set.class) {

            return new HashSet<>();

        }

        Object ret;
        try {
            ret = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.debug("缺少无参构造函数，出现异常{}", e);
            return new Object();
        }

        List<Field> fields = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, fields::add);
        for (Field field : fields) {

            field.setAccessible(true);
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                continue;
            }
            try {
                field.set(ret, initObject(field.getType(), field.getGenericType()));
            } catch (Exception e) {
                log.debug("出现未知异常，异常为{}", e);
            }
        }

        return ret;
    }


    /**
     * 初始化ArrayList类型
     *
     * @param genericType
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static List<Object> initArrayList(Type genericType) throws InstantiationException, IllegalAccessException {

        List<Object> list = new ArrayList<>();

        if (genericType == null) {
            return list;
        }
        // 如果是泛型参数的类型
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) genericType;
            // 得到泛型里的class类型对象
            Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
            //递归
            list.add(initObject(genericClazz, null));
        }
        return list;
    }


    /**
     * 将json格式的参数转换为数组对象
     *
     * @param jsonStr
     * @param invokeMethod
     * @return
     */
    public static Object[] parseJson(String jsonStr, Method invokeMethod) {

        jsonStr = jsonStr.trim();

        String json;
        if (invokeMethod.getParameters().length > 0) {
            if (StringUtils.isEmpty(jsonStr)) {
                //自定义异常
                throw new SysException(ExceptionEnums.PARAM_NOT_NULL);
            }
            if (jsonStr.startsWith("[") && jsonStr.endsWith("]")) {
                json = jsonStr;
            } else {
                json = "[" + jsonStr + "]";
            }
        } else {
            json = jsonStr;
        }

        List<Object> list = JSON.parseArray(json, Object.class);
        Object[] array = PojoUtils.realize(list.toArray(), invokeMethod.getParameterTypes(), invokeMethod.getGenericParameterTypes());

        return array;
    }

    /**
     * 获取host
     *
     * @param address
     * @return
     */
    public static String getHost(String address) {
        if (!address.contains(":")) {
            return address;
        }
        int index = address.indexOf(":");
        return address.substring(0, index);
    }




    /**
     * 获取端口号
     *
     * @param address
     * @return
     */
    public static int getPort(String address) {
        if (!address.contains(":")) {
            return 80;
        }
        int index = address.indexOf(":");
        return Integer.parseInt(address.substring(index+1));
    }


    public static void main(String[] args) {
        String address="1.0.0.1";
        String host=getHost(address);
        int port=getPort(address);
        System.out.println(host);
        System.out.println(port);
    }
}
