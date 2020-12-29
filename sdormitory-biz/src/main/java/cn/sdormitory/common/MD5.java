package cn.sdormitory.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @创建人：zhouyang
 * @创建时间：2020/9/12 12:08
 * @version：V1.0
 */
public class MD5 {
    public static String getMd5(String plainText,int length) throws NoSuchAlgorithmException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(plainText.getBytes());
        byte b[] = md.digest();

        int i;

        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        // 32位
        // return buf.toString();
        // 16位
        // return buf.toString().substring(0, 16);

        return buf.toString().substring(0, length);

    }

    public static int getRandomCode(){
        int max=9999;
        int min=1111;
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }
    public static void main(String[] args) {
        try {
            System.out.println(MD5.getMd5("helloadsfdsffsf",6));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(getRandomCode());
    }
}
