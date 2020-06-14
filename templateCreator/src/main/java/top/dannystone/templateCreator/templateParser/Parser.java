package top.dannystone.templateCreator.templateParser;

import top.dannystone.templateCreator.utils.FileUtils;
import top.dannystone.templateCreator.exception.TokenAnalyzeException;

import java.io.BufferedReader;
import java.io.FileReader;
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
 * @Time: 2020/6/10 2:56 PM
 */
public class Parser {

    /**
     * 词法分析，scan token抽取 <value,heirarchy>
     *
     * @param path
     * @return
     */
    public static List<Token> tokenAnalyze(String path, String indentation) {
        List<Token> tokens = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String s = bufferedReader.readLine();
            while (s != null) {

                Token token = getToken(s, indentation);
                tokens.add(token);

                s = bufferedReader.readLine();
            }

            List<Token> notNull = tokens.stream().filter(e -> e != null).collect(Collectors.toList());

            if (notNull.size() == 0) {
                return notNull;
            }

            return baseHierarchyAdjust(tokens, notNull);


        } catch (Exception e) {
            //一个命令行jar，最好是直接抛出异常
            throw new TokenAnalyzeException("词法解析出错！", e);
        }
    }

    private static List<Token> baseHierarchyAdjust(List<Token> tokens, List<Token> notNull) {
        //以第一行为层级基准(第一行的hierarchy始终为1)，调整hierarchy
        int baseHierarchy = 1 - tokens.get(0).getHierarchy();
        notNull.forEach(e -> e.setHierarchy(baseHierarchy + e.getHierarchy()));
        return notNull;
    }

    /**
     * 语法分析 遍历tokenlist 判断当前token环境下，heirarchy-1 级目录token不能为空。
     *
     * @param tokens
     * @return
     */
    public static List<Token> grammaAnalyze(List<Token> tokens) {
        if (tokens == null || tokens.size() == 0) {
            return tokens;
        }

        Map<Integer, Boolean> directoryTokenExistsMap = new HashMap<>(tokens.size());
        int prevHierarchy = -1;
        for (Token token : tokens) {

            int hierarchy = token.getHierarchy();

            //如果上一个节点的层次比本次节点深，那么需要回退层次
            if (prevHierarchy > hierarchy) {
                for (int i = prevHierarchy; i > hierarchy; i--) {
                    directoryTokenExistsMap.put(i, false);
                }
            }
            prevHierarchy = hierarchy;

            //检查 heirarchy-1 级目录token不能为空。
            int parentHierarchy = hierarchy - 1;
            if (parentHierarchy != 0 && directoryTokenExistsMap.get(parentHierarchy) == false) {
                throw new TokenAnalyzeException(String.format("%s 层次错误!", token.getValue()));
            }

            //如果本token是目录，记录此级存在目录
            if (FileUtils.isDirectory(token.getValue())) {
                directoryTokenExistsMap.put(token.getHierarchy(), true);
            }

        }
        return tokens;
    }


    private static Token getToken(String s, String indentation) {
        //如果该字符串全是空格等，返回null
        if (s.trim().length() == 0) {
            return null;
        }

        int hierarchy = 1;
        while (s.startsWith(indentation)) {
            if (s.indexOf(indentation) + indentation.length() == s.length()) {
                // 如果改行全是 缩进符号，返回null
                return null;
            }
            s = s.substring(s.indexOf(indentation) + indentation.length(), s.length());
            hierarchy++;
        }

        String value = s.trim();

        // 如果改行全是 缩进符号+空字符串组成，返回null
        if (value.length() == 0) {
            return null;
        }

        Token token = new Token(value, hierarchy);
        return token;
    }


}
