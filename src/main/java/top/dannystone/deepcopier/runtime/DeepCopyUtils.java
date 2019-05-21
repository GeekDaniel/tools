package top.dannystone.deepcopier.runtime;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * Description: whether field is copyable judged by the filed has getter setter method
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/4/30 9:59 AM
 */
public class DeepCopyUtils {

    public static final String SETTER_PREFIX = "set";
    public static final String GETTER_PREFIX = "get";
    public static final String BOOLEAN_GETTER_PREFIX = "is";
    public static final String OBJECT_CLASS_NAME = "java.lang.Object";

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
        FieldCopyContext fieldCopyContext = getClassContext(aClass);
        LinkedList<Triple<Field, Method, Method>> fieldGetterSetters = fieldCopyContext.getFieldGetterSetters();

        for (Triple<Field,Method,Method> fieldGetterSetter: fieldGetterSetters) {
            Field field = fieldGetterSetter.getLeft();
            String fieldName = field.getName();

                try {
                    Object value = fieldGetterSetter.getMiddle().invoke(t, new Object[]{});
                    Object valueToAssign = value;
                    //简单类型或者null就不需要copy了
                    if (!isPrimitiveOrString(field)&&valueToAssign!=null) {
                        valueToAssign = deepCopy(value);
                    }
                    fieldGetterSetter.getRight().invoke(copy, new Object[]{valueToAssign});
                } catch (InvocationTargetException e) {
                    System.out.println("refrect mothod invoke fialed. fieldName : " + fieldName + e.getMessage());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        return copy;
    }

    private static boolean isPrimitiveOrString(Field field) {
        return field.getType().isPrimitive() || field.getType().getName().equals("java.lang.String");
    }


    private static boolean isBoolean(Field field) {
        return field.getType().getName().equals("boolean") || field.getType().getName().equals("Boolean");
    }

    private static LinkedList<Class> getClassHierachy(Class clazz) {
        LinkedList<Class> classList= new LinkedList<>();
        classList.add(clazz);
        Class superclass = clazz.getSuperclass();
        if (!OBJECT_CLASS_NAME.equals(superclass.getName())) {
            classList.addAll(getClassHierachy(superclass));
        }
        return classList;

    }

    public static class FieldCopyContext {
        private LinkedList<Triple<Field,Method,Method>> fieldGetterSetters;

        public FieldCopyContext(LinkedList<Triple<Field, Method, Method>> fieldGetterSetters) {
            this.fieldGetterSetters = fieldGetterSetters;
        }

        public LinkedList<Triple<Field, Method, Method>> getFieldGetterSetters() {
            return fieldGetterSetters;
        }

    }

    public static class Triple<L,M,R>{
        private L left;
        private M middle;
        private R right;

        public Triple(L left, M middle, R right) {
            this.left = left;
            this.middle = middle;
            this.right = right;
        }

        public Triple() {
        }

        public L getLeft() {
            return left;
        }

        public void setLeft(L left) {
            this.left = left;
        }

        public M getMiddle() {
            return middle;
        }

        public void setMiddle(M middle) {
            this.middle = middle;
        }

        public R getRight() {
            return right;
        }

        public void setRight(R right) {
            this.right = right;
        }
    }

    private static FieldCopyContext getClassContext(Class clazz) {
        LinkedList<Class> classHierachy = getClassHierachy(clazz);

        LinkedList<Triple<Field, Method, Method>> fieldGetterSetters = new LinkedList<>();
        classHierachy.forEach(e -> {
            Field[] declaredFields = e.getDeclaredFields();
            for (Field field : declaredFields) {
                String name = field.getName();
                try {
                    //获取对应field的getter
                    String getterPrefix = isBoolean(field) ? BOOLEAN_GETTER_PREFIX : GETTER_PREFIX;
                    Method getter = e.getMethod(getterPrefix + name.substring(0, 1).toUpperCase() + name.substring(1), new Class[]{});

                    //获取对应field的setter
                    String methodName = SETTER_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Method setter = clazz.getMethod(methodName, new Class[]{field.getType()});

                    fieldGetterSetters.add(new Triple<>(field, getter, setter));

                } catch (NoSuchMethodException exp) {
                    //todo move to log
                    System.out.println("getter or setter not exists for class : " + clazz.getName() + ", exp message : " + exp.getMessage());
                }
            }
        });

        return new FieldCopyContext(fieldGetterSetters);

    }


}

