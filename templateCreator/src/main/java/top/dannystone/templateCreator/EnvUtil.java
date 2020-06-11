package top.dannystone.templateCreator;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 7:01 PM
 */
public class EnvUtil {
    public static String getHome() {
        return System.getenv("tcHome");
    }

    public static String getWriteTemplate(String type) {
        return getHome() + "/writeTemplates/" + type + ".template";
    }
}
