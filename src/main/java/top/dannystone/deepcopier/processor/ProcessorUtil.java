package top.dannystone.deepcopier.processor;


import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;

import javax.lang.model.element.Modifier;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/4/17 8:01 AM
 */


class ProcessorUtil {
    static final String THIS = "this";

    private static final String SET = "set";

    private static final String GET = "get";

    /**
     * 创建建造者的静态方法名
     */
    static final String BUILDER_STATIC_METHOD_NAME = "builder";

    /**
     * 建造方法名
     */
    static final String BUILD_METHOD_NAME = "build";

    /**
     * 构造方法名字，比较特殊
     */
    static final String CONSTRUCTOR_NAME = "<init>";

    /**
     * 克隆一个字段的语法树节点，该节点作为方法的参数
     * 具有位置信息的语法树节点是不能复用的！
     *
     * @param treeMaker           语法树节点构造器
     * @param prototypeJCVariable 字段的语法树节点
     * @return 方法参数的语法树节点
     */
    static JCTree.JCVariableDecl cloneJCVariableAsParam(TreeMaker treeMaker, JCTree.JCVariableDecl prototypeJCVariable) {
        return treeMaker.VarDef(
                treeMaker.Modifiers(Flags.PARAMETER), //访问标志。极其坑爹！！！
                prototypeJCVariable.name, //名字
                prototypeJCVariable.vartype, //类型
                null //初始化语句
        );
    }

    /**
     * 克隆一个字段的语法树节点集合，作为方法的参数列表
     *
     * @param treeMaker            语法树节点构造器
     * @param prototypeJCVariables 字段的语法树节点集合
     * @return 方法参数的语法树节点集合
     */
    static List<JCTree.JCVariableDecl> cloneJCVariablesAsParams(TreeMaker treeMaker, List<JCTree.JCVariableDecl> prototypeJCVariables) {
        ListBuffer<JCTree.JCVariableDecl> jcVariables = new ListBuffer<>();
        for (JCTree.JCVariableDecl jcVariable : prototypeJCVariables) {
            jcVariables.append(cloneJCVariableAsParam(treeMaker, jcVariable));
        }
        return jcVariables.toList();
    }

    /**
     * 判断是否是合法的字段
     *
     * @param jcTree 语法树节点
     * @return 是否是合法字段
     */
    private static boolean isValidField(JCTree jcTree) {
        if (jcTree.getKind().equals(JCTree.Kind.VARIABLE)) {
            JCTree.JCVariableDecl jcVariable = (JCTree.JCVariableDecl) jcTree;

            Set<Modifier> flagSets = jcVariable.mods.getFlags();
            return (!flagSets.contains(Modifier.STATIC)
                    && !flagSets.contains(Modifier.FINAL));
        }

        return false;
    }

    /**
     * 获取字段的语法树节点的集合
     *
     * @param jcClass 类的语法树节点
     * @return 字段的语法树节点的集合
     */
    static List<JCTree.JCVariableDecl> getJCVariables(JCTree.JCClassDecl jcClass) {
        ListBuffer<JCTree.JCVariableDecl> jcVariables = new ListBuffer<>();

        //遍历jcClass的所有内部节点，可能是字段，方法等等
        for (JCTree jcTree : jcClass.defs) {
            //找出所有set方法节点，并添加
            if (isValidField(jcTree)) {
                //注意这个com.sun.tools.javac.util.List的用法，不支持链式操作，更改后必须赋值
                jcVariables.append((JCTree.JCVariableDecl) jcTree);
            }
        }

        return jcVariables.toList();
    }

    /**
     * 判断是否为set方法
     *
     * @param jcTree 语法树节点
     * @return 判断是否是Set方法
     */
    private static boolean isSetJCMethod(JCTree jcTree) {
        if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
            JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
            return jcMethod.name.toString().startsWith(SET)
                    && jcMethod.params.size() == 1
                    && !jcMethod.mods.getFlags().contains(Modifier.STATIC);
        }
        return false;
    }

    /**
     * 提取出所有set方法的语法树节点
     *
     * @param jcClass 类的语法树节点
     * @return set方法的语法树节点的集合
     */
    static List<JCTree.JCMethodDecl> getSetJCMethods(JCTree.JCClassDecl jcClass) {
        ListBuffer<JCTree.JCMethodDecl> setJCMethods = new ListBuffer<>();

        //遍历jcClass的所有内部节点，可能是字段，方法等等
        for (JCTree jcTree : jcClass.defs) {
            //找出所有set方法节点，并添加
            if (isSetJCMethod(jcTree)) {
                //注意这个com.sun.tools.javac.util.List的用法，不支持链式操作，更改后必须赋值
                setJCMethods.append((JCTree.JCMethodDecl) jcTree);
            }
        }

        return setJCMethods.toList();
    }

    /**
     * 判断是否存在无参构造方法
     *
     * @param jcClass 类的语法树节点
     * @return 是否存在
     */
    static boolean hasNoArgsConstructor(JCTree.JCClassDecl jcClass) {
        for (JCTree jcTree : jcClass.defs) {
            if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
                if (CONSTRUCTOR_NAME.equals(jcMethod.name.toString())) {
                    if (jcMethod.params.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 是否存在全参的构造方法
     *
     * @param jcVariables 字段的语法树节点集合
     * @param jcClass     类的语法树节点
     * @return 是否存在
     */
    static boolean hasAllArgsConstructor(List<JCTree.JCVariableDecl> jcVariables, JCTree.JCClassDecl jcClass) {
        for (JCTree jcTree : jcClass.defs) {
            if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
                if (CONSTRUCTOR_NAME.equals(jcMethod.name.toString())) {
                    if (jcVariables.size() == jcMethod.params.size()) {
                        boolean isEqual = true;
                        for (int i = 0; i < jcVariables.size(); i++) {
                            if (!jcVariables.get(i).vartype.type.equals(jcMethod.params.get(i).vartype.type)) {
                                isEqual = false;
                                break;
                            }
                        }
                        if (isEqual) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断是否存在指定字段的set方法，返回类型不作为判断依据，因为Java中方法重载与返回类型无关
     *
     * @param jcVariable 字段的语法树节点
     * @param jcClass    类的语法树节点
     * @return 是否存在
     */
    static boolean hasSetMethod(JCTree.JCVariableDecl jcVariable, JCTree.JCClassDecl jcClass) {
        String setMethodName = fromPropertyNameToSetMethodName(jcVariable.name.toString());
        for (JCTree jcTree : jcClass.defs) {
            if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
                if (setMethodName.equals(jcMethod.name.toString())
                        && jcMethod.params.size() == 1
                        && jcMethod.params.get(0).vartype.type.equals(jcVariable.vartype.type)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否存在指定字段的get方法，返回类型不作为判断依据，因为Java中方法重载与返回类型无关
     *
     * @param jcVariable 字段的语法树节点
     * @param jcClass    类的语法树节点
     * @return 是否存在
     */
    static boolean hasGetMethod(JCTree.JCVariableDecl jcVariable, JCTree.JCClassDecl jcClass) {
        String getMethodName = fromPropertyNameToGetMethodName(jcVariable.name.toString());
        for (JCTree jcTree : jcClass.defs) {
            if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
                if (getMethodName.equals(jcMethod.name.toString())
                        && jcMethod.params.size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 字段名转换为set方法名
     *
     * @param propertyName 字段名
     * @return set方法名
     */
    static String fromPropertyNameToSetMethodName(String propertyName) {
        return SET + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    /**
     * 字段名转换为get方法名
     *
     * @param propertyName 字段名
     * @return get方法名
     */
    static String fromPropertyNameToGetMethodName(String propertyName) {
        return GET + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }
}
