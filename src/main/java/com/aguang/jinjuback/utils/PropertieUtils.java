package com.aguang.jinjuback.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class PropertieUtils {

//    @Autowired
//    private static StringRedisTemplate stringRedisTemplate;

    public static void copyProperties(Object source, Object target) throws Exception {

        Class sourceClass = source.getClass();
        Class targetClass = target.getClass();

        Map<String, Field> sourceMap = obtainNameMap(source.getClass());
        Map<String, Field> targetMap = obtainNameMap(target.getClass());

        Iterator<String> iterator = sourceMap.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();

            Field targetField = targetMap.get(key);

            if (targetField == null) {
                continue;
            }

            Field sourceField = sourceMap.get(key);

            // 源、目的的类型必须一致
            if(!sourceField.getGenericType().getTypeName().equals(targetField.getGenericType().getTypeName())) {
                continue;
            }

            String sourceMethod = "get" + upperCase(sourceField.getName());
            Method method = sourceClass.getMethod(sourceMethod);

            Object sourceValue = null;
            if (method != null) {
                sourceValue = method.invoke(source, null);
            }

            String targetMethodStr = "set" + upperCase(targetField.getName());
            Method targetMethod = targetClass.getMethod(targetMethodStr, sourceField.getType());

            System.out.println(sourceField.getType());

            if(sourceField.getGenericType().getTypeName().contains("String")) {
                targetMethod.invoke(target, sourceValue.toString());
            }

            if(sourceField.getGenericType().getTypeName().contains("Integer")) {
                targetMethod.invoke(target, new Integer(sourceValue.toString()));
            }

            if(sourceField.getGenericType().getTypeName().contains("Date")) {
                targetMethod.invoke(target, new Date(sourceValue.toString()));
            }
        }
    }

    private static String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private static Map<String, Field> obtainNameMap(Class class1) {
        Map<String, Field> map = new HashMap<String, Field>();

        Field[] fields = class1.getDeclaredFields();

        for (Field field : fields) {
            FieldMeta meta = field.getAnnotation(FieldMeta.class);
            if (meta != null) {
                map.put(meta.name(), field);
            }
        }
        return map;
    }


    /**
     *  转值域
     */
    public static void conversionCode(Object object, JedisPool jedisPool) {

        Jedis jedis = null;
        try {
            Class class1 = object.getClass();

            Field[] fields = class1.getDeclaredFields();

            for (Field field : fields) {
                CodomainField anno = field.getAnnotation(CodomainField.class);
                if (anno != null) {

                    jedis = jedisPool.getResource();

                    field.setAccessible(true);
                    Object fieldValue = field.get(object);

                    if(fieldValue == null) {
                        return;
                    }

                    String hget = jedis.hget(anno.name(), fieldValue.toString());

                    field.set(object, hget);
//                String str = (String)stringRedisTemplate.boundHashOps(anno.name()).get("1");
//                System.out.println(str);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (jedis != null) {
                //关闭连接
                jedis.close();
            }
        }
    }
}
