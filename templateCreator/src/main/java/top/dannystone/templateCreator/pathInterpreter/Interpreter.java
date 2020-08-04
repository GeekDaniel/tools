package top.dannystone.templateCreator.pathInterpreter;

import top.dannystone.templateCreator.fileWriter.utils.CommonUtils;
import top.dannystone.templateCreator.utils.FileUtils;
import top.dannystone.templateCreator.templateParser.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static List<String> getPaths(List<Token> tokens, Map<String, String> args, String destination) {

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


        //params render
        List<String> renderedPaths = renderParams(filePaths, args);

        //add destination
        final String finalDestination = adjustDestination(destination);
        List<String> fullPaths = renderedPaths.stream().map(e -> finalDestination + "/" + e).collect(Collectors.toList());

        return fullPaths;
    }

    private static List<String> renderParams(List<String> paths, Map<String, String> args) {
        List<String> finalPaths = paths.stream().map(e -> {
            String finalPath = e;
            finalPath = CommonUtils.loopRenderVariable(args, finalPath);

            return finalPath;
        }).collect(Collectors.toList());

        return finalPaths;
    }


    private static String adjustDestination(String destination) {
        if (destination.endsWith("/")) {
            destination = destination.substring(0, destination.length() - 1);
        }
        return destination;
    }

}
