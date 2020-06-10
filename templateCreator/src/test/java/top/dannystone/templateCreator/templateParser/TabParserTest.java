package top.dannystone.templateCreator.templateParser;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 5:52 PM
 */
public class TabParserTest {

    @Test
    public void scan() {
        List<Token> scan = TabParser.scan(this.getClass().getClassLoader().getResource("./hierarchytest.txt").getPath());
        System.out.println(JSONObject.toJSONString(scan));
    }
}
