package com.sise.idea.handler;

import com.sise.idea.cache.MethodCache;
import com.sise.idea.dto.MethodModelDto;
import com.sise.idea.model.MethodModel;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import static com.sise.idea.util.ParamUtil.createJsonParamterStr;

/**
 * 读取jar里面的详细类信息
 *
 * @author idea
 * @data 2019/4/4
 */
@Slf4j
public class JarHandler {


    /**
     * 加载jar里面的类信息
     *
     * @param path
     */
    public void loadClassInJar(String path,String className) {
        try {
            URL url = new URL(path);
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url}, Thread.currentThread().getContextClassLoader());
            //这里面已经对该类是否存在做了异常捕获
            Class<?> clazz = urlClassLoader.loadClass(className);
            Method[] methods = clazz.getMethods();
            if (methods.length > 0) {
                int id=0;
                for (Method method : methods) {
                    MethodCache.putMethodModelInCache(new MethodModel(id,className, method));
                    id++;
                }
            }

        } catch (MalformedURLException e) {
            log.error("[JarHandler]URL加载异常，异常为{}", e);
        } catch (ClassNotFoundException e) {
            log.error("[JarHandler]加载类信息异常，异常为{}", e);
        }
    }


    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        JarHandler jarHandler = new JarHandler();
        jarHandler.loadClassInJar("file:D:\\app\\lib-test\\api-1.0.jar","className");
        List<MethodModelDto> methodModelDtos = MethodCache.getMethodCache("com.mmc.dubbo.api.user.UserService");
        for (MethodModelDto methodModelDto : methodModelDtos) {
            System.out.println(createJsonParamterStr(methodModelDto.getMethod()));
        }
    }
}
