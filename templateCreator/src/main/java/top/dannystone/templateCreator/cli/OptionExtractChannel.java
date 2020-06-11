package top.dannystone.templateCreator.cli;

import top.dannystone.templateCreator.cli.domain.TemplateCreateContext;
import top.dannystone.templateCreator.exception.ArgsNotValidException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 4:33 PM
 */
public interface OptionExtractChannel {
    String getOption();

    void extractToContext(String optionValue, TemplateCreateContext templateCreateContext) throws ArgsNotValidException;

}
