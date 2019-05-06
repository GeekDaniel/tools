package top.dannystone.deepcopier.runtime;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/4/30 9:59 AM
 */
public class DeepCopyUtils {

    public static final String SETTER_PREFIX = "set";
    public static final String GETTER_PREFIX = "get";
    public static final String BOOLEAN_GETTER_PREFIX = "is";

    public static <T> T deepCopy(T t) {
        T copy = null;
        try {
            copy = (T) t.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Class<?> aClass = t.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        Map<String, Method> getter = getGetter(aClass);
        Map<String, Method> setter = getSetter(aClass);

        for (Field field : declaredFields) {
            //有 getter setter 的字段才可以复制
            String fieldName = field.getName();
            boolean fieldCopyable = getter.keySet().contains(fieldName)
                    && setter.keySet().contains(fieldName);

            if (fieldCopyable) {
                try {
                    Object value = getter.get(fieldName).invoke(t, new Object[]{});
                    Object valueToAssign = value;
                    //简单类型或者null就不需要copy了
                    if (!isPrimitiveOrString(field)&&valueToAssign!=null) {
                        valueToAssign = deepCopy(value);
                    }
                    setter.get(fieldName).invoke(copy, new Object[]{valueToAssign});
                } catch (InvocationTargetException e) {
                    System.out.println("refrect mothod invoke fialed. fieldName : " + fieldName + e.getMessage());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("uncopyable field : " + fieldName + "ignored");
            }

        }
        return copy;
    }


    //fixme 继承后续优化
    private static Map<String, Method> getGetter(Class clazz) {
        Map<String, Method> getterMethods = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            try {
                String getterPrefix = isBoolean(field) ? BOOLEAN_GETTER_PREFIX : GETTER_PREFIX;
                Method method = clazz.getMethod(getterPrefix + name.substring(0, 1).toUpperCase() + name.substring(1), new Class[]{});
                getterMethods.put(name, method);

            } catch (NoSuchMethodException e) {
                //todo move to log
                System.out.println("error during getGetter :" + clazz.getName() + e.getMessage());
            }
        }
        return getterMethods;
    }

    //fixme 继承后续优化
    private static Map<String, Method> getSetter(Class clazz) {
        Map<String, Method> setterMethods = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            try {
                String methodName = SETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method method = clazz.getMethod(methodName, new Class[]{field.getType()});
                setterMethods.put(name, method);

            } catch (NoSuchMethodException e) {

                //todo move to log
                System.out.println("error during getSetter :" + clazz.getName() + e.getMessage());
            }
        }
        return setterMethods;
    }

    private static boolean isPrimitiveOrString(Field field) {
        return field.getType().isPrimitive() || field.getType().getName().equals("java.lang.String");
    }


    private static boolean isBoolean(Field field) {
        return field.getType().getName().equals("boolean") || field.getType().getName().equals("Boolean");
    }

    public static void main(String[] args) {
        User user = new User();
        Class<? extends User> aClass = user.getClass();
        Map<String, Method> getter = getGetter(aClass);
        Set<Map.Entry<String, Method>> entries = getter.entrySet();
        entries.forEach(e -> {
            System.out.println(e.getKey() + "_" + e.getValue());
        });
        Map<String, Method> setter = getSetter(aClass);
        Set<Map.Entry<String, Method>> entries1 = setter.entrySet();
        entries1.forEach(e -> {
            System.out.println(e.getKey() + "_" + e.getValue());
        });

        user.setAce(true);
        user.setAge(12);
        ClassRoom classroom = new ClassRoom();
        classroom.setClassName("class1");
        user.setClassRoom(classroom);
        User userCopy = DeepCopyUtils.deepCopy(user);
        System.out.println(userCopy);
        System.out.println("properties equal and ref not equal : "+(userCopy.getClassRoom()!=user.getClassRoom()&&userCopy.getClassRoom().getClassName().equals(user.getClassRoom().getClassName())));
    }
}

