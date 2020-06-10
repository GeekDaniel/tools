package top.dannystone.templateCreator;

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
}
