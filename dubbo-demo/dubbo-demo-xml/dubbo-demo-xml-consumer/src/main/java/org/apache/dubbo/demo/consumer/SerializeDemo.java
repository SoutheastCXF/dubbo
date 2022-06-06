package org.apache.dubbo.demo.consumer;

/**
 * @author ziyou.cxf
 * @version : SerializeDemo.java, v 0.1 2022年05月30日 19:56 ziyou.cxf Exp $
 * @desc : 序列化Demo
 */
public class SerializeDemo {

    // 序列化
    public static byte[] int2Bytes(int n) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < bytes.length; i++) {
            // 从后向前进行转移
            bytes[i] = (byte) (n >> (8 * i));
        }
        return bytes;
    }

    // 反序列化
    public static int bytes2int(byte[] bytes) {
        int offset = 0;
        int result = bytes[offset] & 0xff
            | bytes[offset + 1] << 8& 0xff00
            | bytes[offset + 2] << 8 & 0xff0000
            | bytes[offset + 3] << 8 & 0xff000000;

        return result;
    }

    public static void main(String[] args) {
        byte[] bytes = int2Bytes(434);
        System.out.println(bytes2int(bytes));
    }
}
