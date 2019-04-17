package top.dannystone.deepcopier.processor;

import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import top.dannystone.deepcopier.annotation.DeepCopier;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

import static com.sun.org.apache.bcel.internal.Constants.CONSTRUCTOR_NAME;

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
public class DeepCopierProcessor extends BaseProcessor{
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //首先获取被DeepCopier注解标记的元素
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(DeepCopier.class);
        set.forEach(element -> {
            //获取当前元素的JCTree对象
            JCTree jcTree = trees.getTree(element);

            //JCTree利用的是访问者模式，将数据与数据的处理进行解耦，TreeTranslator就是访问者，这里我们重写访问类时的逻辑
            jcTree.accept(new TreeTranslator() {
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClass) {
                    //添加无参构造方法
                    if (!hasNoArgsConstructor(jcClass)) {
                        //todo correct lack of
                        messager.printMessage(Diagnostic.Kind.ERROR, "auto generate deepCopy for : "+ jcClass.name.toString()+" failed,because lack of noArgsConstructor for : "+jcClass.name.toString()  );
                    }

                }
            });
        });

        return true;
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


}
