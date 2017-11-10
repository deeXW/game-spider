package com.zdf.mail;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class Config {

        private static Map<String, String> config   = new HashMap<String, String>();

        public static final String         KEY_USER = "user";

        static {
            try {
                InputStream in = Config.class.getClassLoader().getResourceAsStream(
                        "conf/mail.properties");
                Properties properties = new Properties();
                properties.load(in);
                for (Object key : properties.keySet()) {
                    config.put((String) key, (String) properties.get(key));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static String get(String key) {
            return get(key, null);
        }

        public static String get(String key, String defaultValue) {
            String value = config.get(key);
            return StringUtils.isNotBlank(value) ? value : defaultValue;
        }

        public static int getInt(String key) {
            return Integer.parseInt(config.get(key));
        }

        public static int getInt(String key, int defaultValue) {
            String value = get(key);
            return StringUtils.isNotBlank(value) ? Integer.parseInt(value) : defaultValue;
        }

        public static Boolean getBoolean(String key) {
            return getBoolean(key, null);
        }

        public static Boolean getBoolean(String key, Boolean defaultValue) {
            String value = get(key);
            return StringUtils.isNotBlank(value) ? Boolean.parseBoolean(value) : defaultValue;
        }
}
