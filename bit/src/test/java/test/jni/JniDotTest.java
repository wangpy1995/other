package test.jni;

import jni.JniDot;
import jni.TestThread;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Random;

public class JniDotTest {
    private static int NUM_ITERATIONS = 2560000;
    private static int BATCH_SIZE = 200000;
    private static int TIMES = NUM_ITERATIONS / BATCH_SIZE;

    ByteBuffer batch = ByteBuffer.allocateDirect(512 * BATCH_SIZE);
    ByteBuffer res = ByteBuffer.allocateDirect(4 * BATCH_SIZE);
    ByteBuffer a = ByteBuffer.allocateDirect(512);
    ByteBuffer b = ByteBuffer.allocateDirect(512);
    byte[] batchSrc = new byte[512 * (BATCH_SIZE - 1)];
    byte[] src = new byte[512];
    byte[] des = new byte[512];


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
        int br = 0;
        int s = 0;
        int r = 0;


        long t0 = System.currentTimeMillis();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
//            des[0] = (byte) i;
            s = dot(src, des);
        }

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
//            b.put(0, (byte) i);
            r = test.dotProduct(a, b);
        }
        long t2 = System.currentTimeMillis();

        for (int i = 0; i < TIMES; i++) {
//            b.put(0, (byte) i);
            test.batchDotProduct(batch, b, res, BATCH_SIZE);

            //小端
            byte r0 = res.get(0);
            byte r1 = res.get(1);
            byte r2 = res.get(2);
            byte r3 = res.get(3);
            br = (r3 << 24) + (r2 << 16) + (r1 << 8) + r0;
        }
        long t3 = System.currentTimeMillis();

        System.out.println("dot: " + s + "\t\t" + "cost: " + (t1 - t0));
        System.out.println("dot1: " + r + "\t\t" + "cost: " + (t2 - t1));
        System.out.println("batch dot: " + br + "\t" + "cost: " + (t3 - t2));
    }

    @Test
    public void testDot() {

        Random rnd = new Random();

        rnd.nextBytes(batchSrc);

        rnd.nextBytes(src);
        a.put(src);

        batch.put(src);
//        batch.put(batchSrc);

        rnd.nextBytes(des);
        b.put(des);

        for (int i = 0; i < 10; i++) test();
        batch.clear();
        a.clear();
        b.clear();

    }

    @Test
    public void testAdd() {
        TestThread t1 = new TestThread();
        System.out.println(t1.add(1));
        TestThread t2 = new TestThread();
        System.out.println(t2.add(1));
    }

}
