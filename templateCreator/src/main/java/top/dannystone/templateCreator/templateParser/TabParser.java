package top.dannystone.templateCreator.templateParser;

import top.dannystone.templateCreator.exception.TokenAnalizeException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 2:56 PM
 */
public class TabParser {

    private static final char TAB = 9;
    private static final String INDENTATION = TAB + "";

    public static List<Token> scan(String path) {
        List<Token> tokens = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String s = bufferedReader.readLine();
            while (s != null) {

                Token token = getToken(s);
                tokens.add(token);

                s = bufferedReader.readLine();
            }
            return tokens.stream().filter(e -> e != null).collect(Collectors.toList());

        } catch (Exception e) {
            //一个命令行jar，最好是直接抛出异常
            throw new TokenAnalizeException("词法解析出错！", e);
        }
    }

    private static Token getToken(String s) {
        //如果该字符串全是空格等，返回null
        if (s.trim().length() == 0) {
            return null;
        }

        int hierarchy = 0;
        while (s.startsWith(INDENTATION)) {
            if (s.indexOf(INDENTATION) + INDENTATION.length() == s.length()) {
                // 如果改行全是 缩进符号，返回null
                return null;
            }
            s = s.substring(s.indexOf(INDENTATION) + INDENTATION.length(), s.length());
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
