package top.dannystone.deepcopier.compile.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/4/15 11:17 PM
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface DeepCopier {
}
