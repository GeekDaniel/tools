package top.dannystone.templateCreator.creator;

import com.google.common.collect.Maps;
import top.dannystone.templateCreator.exception.FileCreateException;
import top.dannystone.templateCreator.fileWriter.utils.CommonUtils;
import top.dannystone.templateCreator.fileWriter.utils.JavaFileWriteUtils;
import top.dannystone.templateCreator.pathInterpreter.Interpreter;
import top.dannystone.templateCreator.templateParser.Parser;
import top.dannystone.templateCreator.templateParser.Token;
import top.dannystone.templateCreator.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 11:54 AM
 */
public class TemplateFileCreator {
    public static void create(String destination, String templatePath, String contentMap, Map<String, String> args) {
        String indentationOfFile = getIndentationOfFile(templatePath);
        //parser
        List<Token> tokens = Parser.tokenAnalyze(templatePath, indentationOfFile);
        List<Token> tokens1 = Parser.grammaAnalyze(tokens);

        //interpreter
        List<String> paths = Interpreter.getPaths(tokens1, args, destination);

        doCreate(paths, contentMap, args);
    }


    private static void doCreate(List<String> paths, String contentMap, Map<String, String> args) {
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
                        writeContent(file, contentMap, args);
                    } catch (IOException e) {
                        throw new FileCreateException("文件创建失败!", e);
                    }

                }
            }
        }

    }

    private static void writeContent(File file, String contentMapPath, Map<String, String> args) {
        Map<String, String> contentMap = Maps.newHashMap();
        String s = FileUtils.readAFile(new File(contentMapPath));
        String[] keyAndValue = s.split("\n");
        for (String s1 : keyAndValue) {
            String[] fileAndContentFileName = s1.split("=");
            if (fileAndContentFileName.length == 2) {
                contentMap.put(CommonUtils.loopRenderVariable(args, fileAndContentFileName[0]), fileAndContentFileName[1]);
            }
        }

        String name = file.getName();
        String contentFileName = Optional.ofNullable(contentMap.get(name)).orElse(contentMap.get("default"));

        if (contentFileName != null) {
            //写死content文件 和contentMap文件 统一目录
            Pattern compile = compile("(.+/)(.+?)\\.map");
            Matcher matcher = compile.matcher(contentMapPath);
            //无map文件无法写内容，忽略
            if (!matcher.matches()) {
                return;
            }
            String fileDirectory = matcher.group(1);

            String contentFile = fileDirectory + contentFileName;
            JavaFileWriteUtils.write(file, contentFile, args);

        }
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
