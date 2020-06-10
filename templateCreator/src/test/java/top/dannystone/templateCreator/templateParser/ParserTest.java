package top.dannystone.templateCreator.templateParser;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 5:52 PM
 */
public class ParserTest {

    @Test
    public void scanDifferentEquals() {
        List<Token> scan = Parser.tokenAnalyze(this.getClass().getClassLoader().getResource("./hierarchytest.md").getPath(),"#");

        List<Token> scan2 = Parser.tokenAnalyze(this.getClass().getClassLoader().getResource("./hierarchytest.org").getPath(),"*");

        System.out.println(JSONObject.toJSONString(scan));
        System.out.println(JSONObject.toJSONString(scan2));

        Assert.assertTrue(JSONObject.toJSONString(scan).equals(JSONObject.toJSONString(scan2)));
    }

    @Test
    public void grammaAnalyze() {
        List<Token> scan = Parser.tokenAnalyze(this.getClass().getClassLoader().getResource("./hierarchytest.org").getPath(),"*");
        System.out.println(JSONObject.toJSONString(scan));
        Parser.grammaAnalyze(scan);

    }

}
