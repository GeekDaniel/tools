package top.dannystone.templateCreator.pathInterpreter;

import top.dannystone.templateCreator.FileUtils;
import top.dannystone.templateCreator.templateParser.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 11:59 PM
 */
public class Interpreter {

    private static final String DIRECTORY_SPLIT = "/";

    public static List<String> getPaths(List<Token> tokens) {


        if (tokens == null || tokens.size() == 0) {
            return new ArrayList<>();
        }

        List<String> filePaths = new ArrayList<>();
        Map<Integer, String> hierarchyDirectoryPath = new HashMap<>(tokens.size());

        int prevDirectoryHierarchy = 0;
        for (Token token : tokens) {
            int hierarchy = token.getHierarchy();

            //如果目录回退，需要修改目录映射
            String fileName = token.getValue();
            if (FileUtils.isDirectory(fileName)) {
                if (prevDirectoryHierarchy > hierarchy) {
                    for (int i = prevDirectoryHierarchy; i > hierarchy; i--) {
                        hierarchyDirectoryPath.remove(i);
                    }
                }

                String parentDirectory = hierarchyDirectoryPath.get(hierarchy - 1);
                hierarchyDirectoryPath.put(hierarchy, parentDirectory == null ? fileName : parentDirectory + DIRECTORY_SPLIT + fileName);
            }

            //生成文件路径
            String parentDirectory = hierarchyDirectoryPath.get(hierarchy - 1);
            filePaths.add(parentDirectory == null ? fileName : parentDirectory + DIRECTORY_SPLIT + fileName);
        }

        return filePaths;
    }
}
