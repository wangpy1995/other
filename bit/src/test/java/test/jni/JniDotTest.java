package test.jni;

import jni.JniDot;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Random;

public class JniDotTest {
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

    @Test
    public void testDot() {
        JniDot test = new JniDot();
        int s = 0;
        int r = 0;

        long t0 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
            s = test.dot(src, des, 512);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++)
            r = test.dotProduct(a, b, 512);
        long t2 = System.currentTimeMillis();

        System.out.println("dot: " + s + "\t" + "cost: " + (t1 - t0));
        System.out.println("dot1: " + r + "\t" + "cost: " + (t2 - t1));
    }

}
