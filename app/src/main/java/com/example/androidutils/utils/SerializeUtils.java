package com.example.androidutils.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @功能:
 * @Creat 2019/06/10 11:47
 * @User Lmy
 * @By Android Studio
 */
public class SerializeUtils {
    private static long startTime = 0l;
    private static long endTime = 0l;

    /**
     * 序列化对象
     *
     * @param object 对象必须实现 implements Serializable 接口
     * @return
     * @throws IOException
     */
    public static String serialize(Object object) {
        try {
            startTime = System.currentTimeMillis();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
            objectOutputStream.close();
            byteArrayOutputStream.close();
            endTime = System.currentTimeMillis();
//        System.out.println("序列化耗时为" + (endTime - startTime));
            return serStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return 对象必须实现 implements Serializable 接口
     * @throws Exception
     */
    public static Object deSerialization(String str) {
        try {
            startTime = System.currentTimeMillis();
            String redStr = java.net.URLDecoder.decode(str, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            objectInputStream.close();
            byteArrayInputStream.close();
            endTime = System.currentTimeMillis();
//        System.out.println("反序列化耗时为" + (endTime - startTime));
            return objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new Object();
        }
    }
}
