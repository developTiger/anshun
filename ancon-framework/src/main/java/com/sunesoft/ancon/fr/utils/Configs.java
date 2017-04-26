package com.sunesoft.ancon.fr.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 快速配置工具。
 */
public class Configs {

    private static final String CONFIG_FILE = "env.properties";

    protected static Properties p;

    private final static Object locker = new Object();

    static {
        initConfig();
    }

    protected static void initConfig() {
        if (p == null) {
            synchronized (locker) {
                if (p == null) {
                    InputStream stream = Configs.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
                    if (stream != null) {
                        try {
                            p.load(stream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                stream.close();
                            } catch (IOException e) {
                                stream = null;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取应用的配置信息。
     * @param key 配置节点的键。
     * @return 配置节点的值。
     */
    public static String getProperty(String key) {
        return p.getProperty(key);
    }

    /**
     * 获取应用的配置信息，若配置节点的值为空，则返回默认值。
     * @param key 配置节点的键。
     * @param defaultValue 给定配置节点值为空时返回的默认值。
     * @return 配置节点的值。
     */
    public static String getProperty(String key, String defaultValue) {
        String property = getProperty(key);
        if (property == null || property.isEmpty())
            property = defaultValue;
        return property;
    }

    /**
     * 重新加载配置文件。
     */
    public static void reload() {
        if (p != null) {
            p.clear();
        }
        initConfig();
    }
}
