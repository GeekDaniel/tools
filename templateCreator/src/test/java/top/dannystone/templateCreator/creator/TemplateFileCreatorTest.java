package top.dannystone.templateCreator.creator;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 12:19 PM
 */
public class TemplateFileCreatorTest {

    @Test
    public void create() {
        Map<String, String> params = new HashMap<>();
        params.put("biz", "Order");
        params.put("bizPackage", "order");

        TemplateFileCreator.create(
                "/Users/daniel/codes/tools/templateCreator/src/test/java/top/dannystone/templateCreator",
                this.getClass().getClassLoader().getResource("./mvcBiz.txt").getPath()
                , this.getClass().getClassLoader().getResource("./mvcBizContent.map").getPath()
                , params);


    }

}
