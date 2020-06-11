package top.dannystone.templateCreator.fileWriter;

import top.dannystone.templateCreator.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 1:19 PM
 */
public class JavaFileWriter {
    private static final String IDEA_JAVA_TEST_PACKAGE_ROOT = "src/test/java/";
    private static final String IDEA_JAVA_PACKAGE_ROOT = "src/main/java/";

    public static void write(File file,String templatePath) {


        String name = file.getName();
        Map<String, String> args = new HashMap<>();
        args.put("\\$\\{className}", name.replaceFirst("\\.java", ""));

        String absolutePath = file.getAbsolutePath();
        String packagePath = null;
        if (absolutePath.contains(IDEA_JAVA_PACKAGE_ROOT)) {
            packagePath = absolutePath.substring(absolutePath.indexOf(IDEA_JAVA_PACKAGE_ROOT) + IDEA_JAVA_PACKAGE_ROOT.length(), absolutePath.length() - name.length() - "/".length());
        }
        if (absolutePath.contains(IDEA_JAVA_TEST_PACKAGE_ROOT)) {
            packagePath = absolutePath.substring(absolutePath.indexOf(IDEA_JAVA_TEST_PACKAGE_ROOT) + IDEA_JAVA_TEST_PACKAGE_ROOT.length(), absolutePath.length() - name.length() - "/".length());
        }
        String packageString = packagePath.replaceAll("\\/", ".");
        args.put("\\$\\{package}", packageString);

        File javaTemplate = new File(templatePath);
        String s = FileUtils.readAFile(javaTemplate);

        for (Map.Entry<String, String> entry : args.entrySet()) {
            s = s.replaceAll(entry.getKey(), entry.getValue());
        }

        FileUtils.writeAFile(file, s);
    }
}
