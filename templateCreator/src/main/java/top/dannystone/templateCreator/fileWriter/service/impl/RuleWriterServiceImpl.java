package top.dannystone.templateCreator.fileWriter.service.impl;

import top.dannystone.templateCreator.utils.EnvUtils;
import top.dannystone.templateCreator.fileWriter.domain.FileWriteContext;
import top.dannystone.templateCreator.fileWriter.service.RuleWriterService;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/12 1:43 PM
 */
public class RuleWriterServiceImpl implements RuleWriterService {
    /**
     * 根据文件写入上下文选出匹配的Writer
     *
     * @param fileWriteContext
     * @return
     */
    @Override
    public List<File> getRules(FileWriteContext fileWriteContext) {
        File file = fileWriteContext.getFile();
        List<File> writeTemplates = EnvUtils.getWriteTemplates(file.getName().split("\\.")[1]);
        return writeTemplates;
    }
}
