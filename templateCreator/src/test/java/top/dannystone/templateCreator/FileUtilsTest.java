package top.dannystone.templateCreator;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 5:02 PM
 */
public class FileUtilsTest {

    @Test
    public void isFile() {
        String fileName = "helloworld.java";
        Assert.assertTrue(FileUtils.isFile(fileName));

        String fileName4 = "helloworldjava";
        Assert.assertFalse(FileUtils.isFile(fileName4));

        String fileName2 = "helloworld.jsp";
        Assert.assertFalse(FileUtils.isFile(fileName2));

        String fileName3 = "helloworld";
        Assert.assertFalse(FileUtils.isFile(fileName3));
    }
}
