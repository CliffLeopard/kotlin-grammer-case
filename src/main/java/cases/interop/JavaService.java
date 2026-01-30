package cases.interop;

import cases.core.CoreAnnotations;
import cases.core.CoreSingleton;
import cases.types.ResultBox;
import cases.types.TypeSystemKt;

/**
 * Java 服务类，内部调用 Kotlin 的顶层函数、object、类型。
 * 形成 Java -> Kotlin 的跨语言 CALLS 关系。
 */
public final class JavaService {

    public int useKotlinHelper(int x) {
        return CoreAnnotations.helperTopLevel(x);
    }

    public String useKotlinSingleton() {
        return CoreSingleton.staticLikeCall();
    }

    public ResultBox<String> useKotlinResult(String value) {
        return TypeSystemKt.ok(value);
    }

    public int chainKotlinAndJava(int a, int b) {
        int k = CoreAnnotations.helperTopLevel(a);
        return JavaUtils.add(k, b);
    }
}
