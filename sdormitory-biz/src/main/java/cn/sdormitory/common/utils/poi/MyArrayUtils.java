package cn.sdormitory.common.utils.poi;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/18 15:07
 * @version：V1.0
 */
public class MyArrayUtils {
    private MyArrayUtils() {

    }

    public static int getIndex(Object[] objs, Object object) {
        int index = -1;
        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];
            if (obj.equals(object)) {
                return i;
            }
        }
        return index;
    }
}
