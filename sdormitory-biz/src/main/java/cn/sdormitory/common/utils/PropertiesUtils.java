package cn.sdormitory.common.utils;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/7 13:14
 * @version：V1.0
 */
public class PropertiesUtils {
    private static Properties props;

    /**
     * 加载配置文件
     *
     * @param fileName
     */
    private static void readProperties(String fileName) {
        try {
            props = new Properties();
            InputStreamReader inputStream = new InputStreamReader(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName), "UTF-8");
            props.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据key读取对应的value
     *
     * @param key
     * @return
     */
    public static String get(String fileName,String key) {
        readProperties(fileName);
        return props.getProperty(key);
    }
}
