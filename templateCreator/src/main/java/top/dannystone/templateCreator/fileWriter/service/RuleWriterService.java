package top.dannystone.templateCreator.fileWriter.service;

import top.dannystone.templateCreator.fileWriter.domain.FileWriteContext;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/12 1:39 PM
 */
public interface RuleWriterService {
    List<File> getRules(FileWriteContext fileWriteContext);
}
