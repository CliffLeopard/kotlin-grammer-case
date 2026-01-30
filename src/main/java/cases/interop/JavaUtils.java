package cases.interop;

/**
 * Java 工具类，供 Kotlin 调用。
 * 形成 Java -> 被 Kotlin 调用的 CALLS 关系。
 */
public final class JavaUtils {

    private JavaUtils() {}

    public static String getVersion() {
        return "1.0-java";
    }

    public static int add(int a, int b) {
        return a + b;
    }

    /** 静态方法，供 Kotlin 调用 */
    public static String format(String s) {
        return "Java:" + (s != null ? s : "");
    }
}
