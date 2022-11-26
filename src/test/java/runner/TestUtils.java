package runner;

import org.apache.commons.lang3.RandomStringUtils;

public class TestUtils {

    public static String getRandomStr(int length) {
        return RandomStringUtils.random(length,
                "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
    }

    public static String getRandomStr() {
        return getRandomStr(15);
    }
}
