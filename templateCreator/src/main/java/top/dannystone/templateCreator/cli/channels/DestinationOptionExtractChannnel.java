package top.dannystone.templateCreator.cli.channels;

import top.dannystone.templateCreator.cli.OptionExtractChannel;
import top.dannystone.templateCreator.cli.domain.TemplateCreateContext;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 4:38 PM
 */
public class DestinationOptionExtractChannnel implements OptionExtractChannel {
    @Override
    public String getOption() {
        return "-d";
    }

    @Override
    public void extractToContext(String optionValue, TemplateCreateContext templateCreateContext) {
        templateCreateContext.setDestination(optionValue);
    }
}
