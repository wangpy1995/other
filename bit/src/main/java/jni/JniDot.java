package jni;

import java.nio.ByteBuffer;
import java.util.Random;

public class JniDot {

    static {
        System.load("/home/wpy/clion/SIMD_AVX/cmake-build-debug/libjni_Test.so");
    }

    private static ByteBuffer a;
    private static ByteBuffer b;
    private static byte[] src = new byte[512];
    private static byte[] des = new byte[512];

    static {

        Random r = new Random();
        r.nextBytes(src);
        r.nextBytes(des);

        a = ByteBuffer.allocateDirect(512);
        b = ByteBuffer.allocateDirect(512);
        a.put(src);
        b.put(des);
    }

    public native int[] dot_product_r7(int len, ByteBuffer a, ByteBuffer b0, ByteBuffer b1, ByteBuffer b2, ByteBuffer b3, ByteBuffer b4, ByteBuffer b5);

    public native int dot_product(ByteBuffer a, ByteBuffer b, int len);

    public int dot(byte[] a, byte[] b, int len) {
        int sum = 0;
        for (int i = 0; i < len; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    public static void testDot() {
        JniDot test = new JniDot();
        int s = 0;
        int r = 0;
        int[] res = null;

        long t0 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
            s = test.dot(src, des, 512);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
            r = test.dot_product(a, b, 512);
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < 10000000 / 6; i++)
            res = test.dot_product_r7(512, a, b, b, b, b, b, b);
        long t3 = System.currentTimeMillis();

        System.out.println("dot: " + s + "\t" + "cost: " + (t1 - t0));
        System.out.println("dot1: " + r + "\t" + "cost: " + (t2 - t1));
        System.out.println("dot_r: " + res[5] + "\t" + "cost: " + (t3 - t2));
    }

    public static void main(String[] args) {
        testDot();
    }
}
