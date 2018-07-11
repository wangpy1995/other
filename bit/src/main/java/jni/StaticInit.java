package jni;

import java.io.Serializable;

public class StaticInit implements Serializable {

    static {
        System.load("/home/wpy/clion/SIMD_AVX/cmake-build-release/libStaticInit.so");
    }

    public native synchronized long initHandle();

    public native int doSomething();

    public native synchronized void releaseHandle();

    @Override
    protected void finalize() throws Throwable {
        releaseHandle();
        super.finalize();
    }

}
