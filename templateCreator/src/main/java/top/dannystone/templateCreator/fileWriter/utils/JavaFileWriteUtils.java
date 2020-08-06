package top.dannystone.templateCreator.fileWriter.utils;

import top.dannystone.templateCreator.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;
import static top.dannystone.templateCreator.constant.IDEAJavaConstant.IDEA_JAVA_PACKAGE_ROOT;
import static top.dannystone.templateCreator.constant.IDEAJavaConstant.IDEA_JAVA_TEST_PACKAGE_ROOT;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/14 9:46 PM
 */
public class JavaFileWriteUtils {

    public static void write(File file, String templatePath, Map<String, String> args) {

        //读取模板文件
        File javaTemplate = new File(templatePath);
        String s = FileUtils.readAFile(javaTemplate);

        //替换变量
        Map<String, String> argsFromFile = getArgsByJavaFile(file);
        for (Map.Entry<String, String> entry : argsFromFile.entrySet()) {
            args.put(entry.getKey(), entry.getValue());
        }

        String result = s;
        for (Map.Entry<String, String> entry : args.entrySet()) {
            result = CommonUtils.loopRenderVariable(args, s);
        }

        //动作 回退等
        result = PathOperationUtils.package3DepthCd(result);
        result = PathOperationUtils.package2DepthCd(result);
        result = PathOperationUtils.package1DepthCd(result);

        //写入内容
        FileUtils.writeAFile(file, result);
    }


    private static Map<String, String> getArgsByJavaFile(File file) {
        Map<String, String> args = new HashMap<>();

        String name = file.getName();
        args.put("className", name.replaceFirst("\\.java", ""));

        String absolutePath = file.getAbsolutePath();
        String packagePath = null;
        if (absolutePath.contains(IDEA_JAVA_PACKAGE_ROOT)) {
            packagePath = absolutePath.substring(absolutePath.indexOf(IDEA_JAVA_PACKAGE_ROOT) + IDEA_JAVA_PACKAGE_ROOT.length(), absolutePath.length() - name.length() - "/".length());
        }
        if (absolutePath.contains(IDEA_JAVA_TEST_PACKAGE_ROOT)) {
            packagePath = absolutePath.substring(absolutePath.indexOf(IDEA_JAVA_TEST_PACKAGE_ROOT) + IDEA_JAVA_TEST_PACKAGE_ROOT.length(), absolutePath.length() - name.length() - "/".length());
        }
        String packageString = packagePath.replaceAll("\\/", ".");
        args.put("package", packageString);
        return args;
    }


}
