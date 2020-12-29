package cn.sdormitory.common.utils.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @创建人：zhouyang
 * @创建时间：2020/12/18 15:06
 * @version：V1.0
 * description: 字段注解效验
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface ExcelTitle {

    String title();

    String dataMap() default "";

}
