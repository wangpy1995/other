package test.jni;

import jni.JniDot;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Random;

public class JniDotTest {
    private static int NUM_ITERATIONS = 2560000;

    static {
        System.load("/home/wpy/clion/SIMD_AVX/cmake-build-release/libjni_Test.so");
    }

    private static JniDot test = new JniDot();

    private int dot(byte[] a, byte[] b) {
        int sum = 0;
        for (int i = 0; i < 512; i++) {
            sum += a[i] * b[i];
        }
        return sum;
    }

    private void test() {
        ByteBuffer a = ByteBuffer.allocateDirect(512);
        ByteBuffer b = ByteBuffer.allocateDirect(512);
        byte[] src = new byte[512];
        byte[] des = new byte[512];
        Random rnd = new Random();

        rnd.nextBytes(src);
        a.put(src);

        int s = 0;
        int r = 0;

        rnd.nextBytes(des);
        b.put(des);
        long t0 = System.currentTimeMillis();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            des[0] = (byte) i;
            s = dot(src, des);
        }

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            b.put(0, (byte) i);
            r = test.dotProduct(a, b);
        }
        long t2 = System.currentTimeMillis();

        a.clear();
        b.clear();

        System.out.println("dot: " + s + "\t" + "cost: " + (t1 - t0));
        System.out.println("dot1: " + r + "\t" + "cost: " + (t2 - t1));
    }

    @Test
    public void testDot() {
//        for (int i = 0; i < 100; i++)
            test();
    }

}
