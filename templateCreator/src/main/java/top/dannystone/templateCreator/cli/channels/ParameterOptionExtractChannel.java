package top.dannystone.templateCreator.cli.channels;

import top.dannystone.templateCreator.cli.OptionExtractChannel;
import top.dannystone.templateCreator.cli.domain.TemplateCreateContext;
import top.dannystone.templateCreator.exception.ArgsNotValidException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 4:41 PM
 */
public class ParameterOptionExtractChannel implements OptionExtractChannel {
    @Override
    public String getOption() {
        return "-p";
    }

    @Override
    public void extractToContext(String optionValue, TemplateCreateContext templateCreateContext) throws ArgsNotValidException {
        String[] split = optionValue.split("=");
        if (split.length != 2) {
            throw new ArgsNotValidException("参数值应该是 key=value 的字符串！");
        }
        String key = split[0].trim();
        String value = split[1].trim();
        if (key.length() == 0 || value.length() == 0) {
            throw new ArgsNotValidException("参数值不能为空");
        }

        templateCreateContext.getParams().put(key, value);

    }
}
