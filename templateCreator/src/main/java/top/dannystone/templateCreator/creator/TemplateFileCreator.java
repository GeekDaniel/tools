package top.dannystone.templateCreator.creator;

import top.dannystone.templateCreator.FileUtils;
import top.dannystone.templateCreator.exception.FileCreateException;
import top.dannystone.templateCreator.pathInterpreter.Interpreter;
import top.dannystone.templateCreator.templateParser.Parser;
import top.dannystone.templateCreator.templateParser.Token;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<Token> tokens = Parser.tokenAnalyze(templatePath, indentationOfFile);
        List<Token> tokens1 = Parser.grammaAnalyze(tokens);
        List<String> paths = Interpreter.getPaths(tokens1);

        //params render
        List<String> finalPaths = renderParams(paths, args);

        doCreate(destination, finalPaths);
    }

    private static List<String> renderParams(List<String> paths, Map<String, String> args) {
        List<String> finalPaths = paths.stream().map(e -> {
            String finalPath = e;
            finalPath = loopRenderVariable(args, finalPath);

            return finalPath;
        }).collect(Collectors.toList());

        return finalPaths;
    }

    private static String loopRenderVariable(Map<String, String> args, String path) {
        for (Map.Entry<String, String> entry : args.entrySet()) {
            String variable = "\\$\\{" + entry.getKey() + "}";
            String value = entry.getValue();
            path = path.replaceAll(variable, value);
        }
        return path;
    }

    private static void doCreate(String destination, List<String> paths) {
        for (String path : paths) {

            destination = adjustDestination(destination);
            path = destination + "/" + path;

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
                    } catch (IOException e) {
                        throw new FileCreateException("文件创建失败!", e);
                    }
                }
            }
        }

    }

    private static String adjustDestination(String destination) {
        if (destination.endsWith("/")) {
            destination = destination.substring(0, destination.length() - 1);
        }
        return destination;
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
