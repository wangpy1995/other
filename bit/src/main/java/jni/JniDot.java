package jni;

import java.nio.ByteBuffer;

//-XX:MaxDirectMemorySize=6g -XX:+UnlockDiagnosticVMOptions -XX:CompileCommand=print,*JniDot.dot
public class JniDot {
    public native int dotProduct(ByteBuffer a, ByteBuffer b);

    public native void batchDotProduct(ByteBuffer a, ByteBuffer b, ByteBuffer res, int batchSize);
}
