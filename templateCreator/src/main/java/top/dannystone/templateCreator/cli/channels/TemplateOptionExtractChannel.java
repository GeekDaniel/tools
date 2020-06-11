package top.dannystone.templateCreator.cli.channels;

import top.dannystone.templateCreator.cli.OptionExtractChannel;
import top.dannystone.templateCreator.cli.domain.TemplateCreateContext;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 4:40 PM
 */
public class TemplateOptionExtractChannel implements OptionExtractChannel {
    @Override
    public String getOption() {
        return "-t";
    }

    @Override
    public void extractToContext(String optionValue, TemplateCreateContext templateCreateContext) {
        templateCreateContext.setTemplateAbsolutePath(optionValue);
    }
}
