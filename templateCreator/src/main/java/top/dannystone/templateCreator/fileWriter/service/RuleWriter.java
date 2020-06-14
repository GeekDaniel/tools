package top.dannystone.templateCreator.fileWriter.service;

import top.dannystone.templateCreator.fileWriter.domain.FileWriteContext;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description: 除了目录模板中携带的信息（类还是接口），你还可以根据匹配规则来选择写文件模板
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/12 11:09 AM
 */
public interface RuleWriter {

    /**
     * 该文件是否匹配到此模板
     *
     * @param fileWriteContext
     * @return
     */
    boolean match(FileWriteContext fileWriteContext);

    /**
     * 获取写文件模板
     *
     * @return
     */
    File getTemplate();


}
