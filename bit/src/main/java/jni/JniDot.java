package jni;

import java.nio.ByteBuffer;
import java.util.Random;

//-XX:MaxDirectMemorySize=6g -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*JniDot.dot
public class JniDot {

    static {
        System.load("/home/wpy/clion/SIMD_AVX/cmake-build-debug/libjni_Test.so");
    }

    public native int dotProduct(ByteBuffer a, ByteBuffer b, int len);

    public int dot(byte[] a, byte[] b, int len) {
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    private static void testDot() {
        ByteBuffer a = ByteBuffer.allocateDirect(512);
        ByteBuffer b = ByteBuffer.allocateDirect(512);
        byte[] src = new byte[512];
        byte[] des = new byte[512];
        Random rnd = new Random();

        rnd.nextBytes(src);
        a.put(src);

        JniDot test = new JniDot();
        int s = 0;
        int r = 0;

        b.put(des);
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < 30000000; i++) {
            des[0] = (byte) i;
            s = test.dot(src, des, 512);
        }

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 30000000; i++) {
            b.put(0, (byte) i);
            r = test.dotProduct(a, b, 512);
        }
        long t2 = System.currentTimeMillis();

        System.out.println("dot: " + s + "\t" + "cost: " + (t1 - t0));
        System.out.println("dot1: " + r + "\t" + "cost: " + (t2 - t1));
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++)
            testDot();
    }
}
