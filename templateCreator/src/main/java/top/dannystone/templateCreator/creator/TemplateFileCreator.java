package top.dannystone.templateCreator.creator;

import top.dannystone.templateCreator.FileUtils;
import top.dannystone.templateCreator.EnvUtil;
import top.dannystone.templateCreator.exception.FileCreateException;
import top.dannystone.templateCreator.fileWriter.JavaFileWriter;
import top.dannystone.templateCreator.pathInterpreter.Interpreter;
import top.dannystone.templateCreator.templateParser.Parser;
import top.dannystone.templateCreator.templateParser.Token;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 11:54 AM
 */
public class TemplateFileCreator {
    public static void create(String destination, String templatePath, Map<String, String> args) {
        String indentationOfFile = getIndentationOfFile(templatePath);
        //parser
        List<Token> tokens = Parser.tokenAnalyze(templatePath, indentationOfFile);
        List<Token> tokens1 = Parser.grammaAnalyze(tokens);

        //interpreter
        List<String> paths = Interpreter.getPaths(tokens1, args, destination);

        doCreate(paths);
    }


    private static void doCreate(List<String> paths) {
        for (String path : paths) {

            if (FileUtils.isDirectory(path)) {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
            }

            if (FileUtils.isFile(path)) {
                File file = new File(path);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                        writeContent(file);
                    } catch (IOException e) {
                        throw new FileCreateException("文件创建失败!", e);
                    }

                }
            }
        }

    }

    private static void writeContent(File file) {
        if (file.getName().endsWith(".java")) {
            JavaFileWriter.write(file, EnvUtil.getWriteTemplate("java"));
        }
        //todo maybe can extend to other file format
    }


    private static String getIndentationOfFile(String templatePath) {
        if (templatePath.endsWith(".md")) {
            return "#";
        } else if (templatePath.endsWith(".org")) {
            return "*";
        } else if (templatePath.endsWith(".txt")) {
            return "\t";
        }
        throw new UnsupportedOperationException("不支持的模板类型！");
    }
}
