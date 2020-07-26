package com.zeekling.util;

import com.zeekling.conf.Value;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author zeekling [lingzhaohui@zeekling.cn]
 * @version 1.0
 * @apiNote 读取配置文件
 * @since 2020-07-26
 */
public final class ConfigureUtil {

    private ConfigureUtil() {
    }

    public static Properties readProperties(String filePath) throws IOException {
        Properties properties = new Properties();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        properties.load(bufferedReader);
        return properties;
    }

    @SuppressWarnings("ReflectionForUnavailableAnnotation")
    public static <T> T getNewInstants(String filePath, Class<T> tClass)
            throws IllegalAccessException, InstantiationException, IOException {
        T t = tClass.newInstance();
        Properties properties = readProperties(filePath);

        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            Value value = field.getAnnotation(Value.class);
            if (value == null || StringUtils.isEmpty(value.name())){
                continue;
            }
            String realValue = properties.getProperty(value.name());
            if (realValue == null){
                continue;
            }
            field.setAccessible(true);
            field.set(t, realValue);
        }
        return t;
    }


}
