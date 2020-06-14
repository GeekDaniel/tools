package top.dannystone.templateCreator.fileWriter.service.impl.ruleWriters;

import top.dannystone.templateCreator.fileWriter.domain.FileWriteContext;
import top.dannystone.templateCreator.fileWriter.service.RuleWriter;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/12 11:30 AM
 */
public class EndsWithRuleWriter implements RuleWriter {

    @Override
    public boolean match(FileWriteContext fileWriteContext) {
        File file = fileWriteContext.getFile();
        return false;
    }

    @Override
    public File getTemplate() {
        return null;
    }
}
