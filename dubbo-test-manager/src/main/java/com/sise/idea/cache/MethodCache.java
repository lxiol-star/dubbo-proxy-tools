package com.sise.idea.cache;

import com.sise.idea.dto.MethodModelDto;
import com.sise.idea.model.MethodModel;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.sise.idea.util.ParamUtil.subStringLastIndex;

/**
 * 方法缓存
 *
 * @author idea
 * @data 2019/4/4
 */
public class MethodCache {

    /**
     * 存储方法模型的缓存map
     */
    private final static Map<String, List<MethodModel>> map = new ConcurrentHashMap<>();


    /**
     * 每个类都有一个list来管理相应的method信息
     *
     * @param methodModel
     */
    public static void putMethodModelInCache(MethodModel methodModel) {
        List<MethodModel> methodModelList = map.get(methodModel.getKey());
        if (CollectionUtils.isEmpty(methodModelList)) {
            methodModelList = new ArrayList<>();
        }
        methodModelList.add(methodModel);
        map.put(methodModel.getKey(), methodModelList);
    }

    /**
     * 清空缓存
     */
    public static void clean() {
        map.clear();
    }


    /**
     * 从缓存里面获取方法信息
     *
     * @param key
     * @return
     */
    public static List<MethodModelDto> getMethodCache(String key) {
        List<MethodModel> methodModelList = map.get(key);
        if (CollectionUtils.isEmpty(methodModelList)) {
            return Collections.emptyList();
        } else {
            List<MethodModelDto> methodModelDtos = new ArrayList<>();
            methodModelList.stream().forEach(methodModel -> {
                methodModelDtos.add(buildMethodModelDto(methodModel));
            });
            return methodModelDtos;
        }
    }

    /**
     * 构建特定格式的参数描述信息
     *
     * @param methodModel
     * @return
     */
    private static String buildMethodDes(MethodModel methodModel) {
        Method method = methodModel.getMethod();
        Parameter[] parameters = method.getParameters();
        StringBuffer des = new StringBuffer();

        des.append(subStringLastIndex(method.getName(), '.') + " (");
        for (int i = 0; i < parameters.length; i++) {
            des.append(subStringLastIndex(parameters[i].getType().getName(), '.') + " " + parameters[i].getName());
            if (i != parameters.length - 1) {
                des.append(",");
            }
        }
        des.append(")");
        return des.toString();
    }


    private static MethodModelDto buildMethodModelDto(MethodModel methodModel) {
        Parameter[] parameters = methodModel.getMethod().getParameters();
        List<Parameter> parameterList = Arrays.asList(parameters);
        MethodModelDto methodModelDto = new MethodModelDto();
        methodModelDto.setId(methodModel.getId());
        methodModelDto.setInterfaceName(methodModel.getKey());
        methodModelDto.setMethodName(methodModel.getMethod().getName());
        methodModelDto.setDes(buildMethodDes(methodModel));
        methodModelDto.setParameters(parameterList);
        methodModelDto.setMethod(methodModel.getMethod());
        return methodModelDto;
    }


}
