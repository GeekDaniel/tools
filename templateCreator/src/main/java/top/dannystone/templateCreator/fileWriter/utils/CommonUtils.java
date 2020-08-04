package top.dannystone.templateCreator.fileWriter.utils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * Creed: keep it simple and stupid !
 * Time: 2020/8/5 12:51 AM
 */
public class CommonUtils {
    public static String loopRenderVariable(Map<String, String> args, String template) {
        for (Map.Entry<String, String> entry : args.entrySet()) {
            String variable = "\\$\\{" + entry.getKey() + "}";
            String value = entry.getValue();
            template = template.replaceAll(variable, value);
        }
        return template;
    }
}
