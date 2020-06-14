package top.dannystone.templateCreator.utils;

import top.dannystone.templateCreator.exception.WriteTemplateNotFound;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description: 写文件模板路径为 ${tcHome}/writeTemplates/${fileSuffix}/${matchRule}.template
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 7:01 PM
 */
public class EnvUtils {
    public static String getHome() {
        return System.getenv("tcHome");
    }

    public static List<File> getWriteTemplates(String fileSuffix) {
        String templateDir = getHome() + "/writeTemplates/" + fileSuffix;
        File file = new File(templateDir);
        if (file.exists()) {
            return Arrays.asList(file.listFiles());
        } else {
            throw new WriteTemplateNotFound(String.format("模板目录不存在", templateDir));
        }
    }

    public static File getDefaultTemplateByFileSuffix(String fileSuffix) {

        String templateDir = getHome() + "/writeTemplates/" + fileSuffix;
        String defaultTemplate = templateDir + "/" + "default.template";

        File defaultTemplateFile = new File(defaultTemplate);
        if (defaultTemplateFile.exists()) {
            return defaultTemplateFile;
        }

        throw new WriteTemplateNotFound(String.format("默认文件模板不存在", templateDir));
    }

    public static String getTemplateHomeByFileSuffix(String fileSuffix) {

        String templateDir = getHome() + "/writeTemplates/" + fileSuffix;
        return templateDir;
    }

}
