package winnow.ak.study;

import winnow.ak.study.demo.User;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: Winyu
 * @Date: 2021/5/26
 */
public class Reflect {
    public static void main(String[] args) throws Exception {
//        Reflect.copy(new User());

        String a = "abdaga";
    }

    public static Object copy(Object object) throws Exception {
        //获得对象的类型
        Class classType = object.getClass();
        System.out.println("Class:" + classType.getName());

        //通过默认构造方法创建一个新的对象
        Object objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
        //获得对象的所有属性
        Field fields[] = classType.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            //获得和属性对应的getXXX()方法的名字
            String getMethodName = "add";

            //获得和属性对应的getXXX()方法
            Method getMethod = classType.getMethod(getMethodName, new Class[]{});

            //调用原对象的getXXX()方法
            Object value = getMethod.invoke(object, new Object[]{});
            System.out.println(fieldName + ":" + value);
        }
        return objectCopy;
    }


}
