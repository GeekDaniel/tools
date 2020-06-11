package top.dannystone.templateCreator.pathInterpreter;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import top.dannystone.templateCreator.templateParser.Parser;
import top.dannystone.templateCreator.templateParser.Token;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 11:49 AM
 */
public class InterpreterTest {

    @Test
    public void getPaths() {
        List<Token> scan3 = Parser.tokenAnalyze(this.getClass().getClassLoader().getResource("./hierarchytest.txt").getPath(), "\t");
        List<Token> tokens = Parser.grammaAnalyze(scan3);
        List<String> paths = Interpreter.getPaths(tokens,null,"");
        System.out.println(paths);

    }
}
