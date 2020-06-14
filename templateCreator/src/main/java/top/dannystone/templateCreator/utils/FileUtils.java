package top.dannystone.templateCreator.utils;

import top.dannystone.templateCreator.exception.FileIOException;
import top.dannystone.templateCreator.exception.UncheckedFileNotFoundException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 4:58 PM
 */
public class FileUtils {
    private static final Set<String> FILE_SUFFIX = new HashSet<>();

    static {
        FILE_SUFFIX.add(".java");
    }

    public static boolean isFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        for (String fileSuffix : FILE_SUFFIX) {
            if (fileName.endsWith(fileSuffix)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isDirectory(String fileName) {
        if (fileName == null) {
            return false;
        }

        return !isFile(fileName);

    }

    public static String readAFile(File file) {
        if (!file.exists()) {
            throw new FileIOException(String.format("文件不存在,文件名：%s", file.getAbsolutePath()));
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder contentStringBuilder = new StringBuilder();

            String s = bufferedReader.readLine();
            while (s != null) {
                contentStringBuilder.append(s).append("\n");
                s = bufferedReader.readLine();
            }

            return contentStringBuilder.toString();

        } catch (FileNotFoundException e) {
            throw new UncheckedFileNotFoundException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileIOException("文件读取出错!", e);
        }

    }

    public static void writeAFile(File file, String content) {

        if (!file.exists()) {
            return;
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(content);

        } catch (IOException e) {
            throw new FileIOException("文件写入出错!", e);
        }

    }

}
