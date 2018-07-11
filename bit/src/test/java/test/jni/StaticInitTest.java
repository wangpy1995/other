package test.jni;

import jni.StaticInit;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

public class StaticInitTest {

    @Test
    public void test() throws IOException {
        StaticInit s0 = new StaticInit();
        s0.initHandle();
        System.out.println(s0.doSomething());

        StaticInit s1 = new StaticInit();
        s1.initHandle();
        System.out.println(s1.doSomething());
        s1.releaseHandle();
        s1.releaseHandle();
        System.gc();

        FileOutputStream out = new FileOutputStream("static_init");
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(s0);
        objOut.flush();
        objOut.close();
        new Scanner(System.in).nextLine();
    }

    @Test
    public void testDeserialize() throws IOException, ClassNotFoundException {
        InputStream in = new FileInputStream("static_init");
        ObjectInputStream objIn = new ObjectInputStream(in);
        StaticInit s0 = (StaticInit) objIn.readObject();
        s0.initHandle();
        System.out.println("serialized: " + s0.doSomething());
        s0.releaseHandle();
        new Scanner(System.in).nextLine();
    }


}
