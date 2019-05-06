package top.dannystone.deepcopier.compile.processor;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.StringUtils;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

import static com.sun.org.apache.bcel.internal.Constants.CONSTRUCTOR_NAME;
import static top.dannystone.deepcopier.compile.processor.ProcessorUtil.THIS;
import static top.dannystone.deepcopier.compile.processor.ProcessorUtil.isValidField;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/4/16 9:06 AM
 */
@SupportedAnnotationTypes("top.dannystone.deepcopier.annotation.DeepCopier")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DeepCopierProcessor extends BaseProcessor {

    private List<JCTree.JCVariableDecl> fieldJCVariables;

    public static final String DEEP_COPY_METHOD_NAME = "deepCopy";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //首先获取被DeepCopier注解标记的元素
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(top.dannystone.deepcopier.compile.annotation.DeepCopier.class);
        set.stream().forEach(element -> {
            JCTree jcTree = trees.getTree(element);

            jcTree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClass) {
                    messager.printMessage(Diagnostic.Kind.NOTE, "process class [" + jcClass.name.toString() + "], start");
                    //合法性检查
                    if (!check()) {
                        messager.printMessage(Diagnostic.Kind.ERROR, "@Deepcopier processor [" + "" + "] check failed!");
                    }

                    //生成deepCopy()方法,主要是获取类中的字段
                    before(jcClass);

                    //todo remove
//                    printJcClassDecl(jcClass);

                    //添加全参构造方法
                    jcClass.defs = jcClass.defs.append(
                            createDeepCopy(jcClass)
                    );
                    after();
                    messager.printMessage(Diagnostic.Kind.NOTE, "process class [" + jcClass.name.toString() + "], end");
                }
            });

        });


        return true;
    }

    private boolean check() {
        return true;
    }

    private void before(JCTree.JCClassDecl classDecl) {
        fieldJCVariables = getJCVariables(classDecl);
    }

    /**
     * 进行一些清理工作
     */
    private void after() {
        this.fieldJCVariables = null;
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

    public void printJcClassDecl(JCTree.JCClassDecl jcClassDecl) {
        messager.printMessage(Diagnostic.Kind.NOTE, jcClassDecl.sym.type.toString())
        ;
        List<JCTree.JCVariableDecl> jcVariables = getJCVariables(jcClassDecl);
        jcVariables.stream().forEach(e -> {
            messager.printMessage(Diagnostic.Kind.NOTE,
                    e.vartype.toString());
        });


    }

    /**
     * 创建全参数构造方法
     *
     * @return 全参构造方法语法树节点
     */
    private JCTree.JCMethodDecl createDeepCopy(JCTree.JCClassDecl jcClassDecl) {

        ListBuffer<JCTree.JCStatement> jcStatements = new ListBuffer<>();

        //XXX xXX=new XXX();
        String className = jcClassDecl.name.toString();

        messager.printMessage(Diagnostic.Kind.NOTE, StringUtils.toLowerCase(className.substring(0, 1)) + className.substring(1));

        Name instanceName = names.fromString(StringUtils.toLowerCase(className.substring(0, 1)) + className.substring(1));
        jcStatements.append(
                treeMaker.VarDef(
                        treeMaker.Modifiers(0),
                        instanceName,
                        treeMaker.Ident(names.fromString(className)),
                        treeMaker.NewClass(
                                null, //尚不清楚含义
                                List.nil(), //泛型参数列表
                                treeMaker.Ident(names.fromString(className)), //创建的类名
                                List.nil(), //参数列表
                                null //类定义，估计是用于创建匿名内部类
                        ))
        );

        //xxx.field=this.field
        for (JCTree.JCVariableDecl jcVariable : fieldJCVariables) {
            jcStatements.append(
                    treeMaker.Exec(
                            treeMaker.Assign(
                                    treeMaker.Select(treeMaker.Ident(instanceName),names.fromString(jcVariable.name.toString())),
                                    treeMaker.Select(
                                            treeMaker.Ident(names.fromString(THIS)),
                                            names.fromString(jcVariable.name.toString())
                                    )
                            )
                    )
            );
        }

        //return xxx;
        jcStatements.append(
                treeMaker.Return(
                        treeMaker.Ident(instanceName)
                ));


        JCTree.JCBlock jcBlock = treeMaker.Block(
                0 //访问标志
                , jcStatements.toList() //所有的语句
        );

        return treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC), //访问标志
                names.fromString(DEEP_COPY_METHOD_NAME), //名字
                treeMaker.Ident(names.fromString(className)), //返回类型
                List.nil(), //泛型形参列表
                List.nil(), //参数列表
                List.nil(), //异常列表
                jcBlock, //方法体
                null //默认方法（可能是interface中的那个default）
        );

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

    private void log(String message) {
        messager.printMessage(Diagnostic.Kind.NOTE, message);
    }
}
