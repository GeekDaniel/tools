package top.dannystone.templateCreator.cli;

import top.dannystone.templateCreator.cli.channels.DestinationOptionExtractChannnel;
import top.dannystone.templateCreator.cli.channels.ParameterOptionExtractChannel;
import top.dannystone.templateCreator.cli.channels.TemplateOptionExtractChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 4:33 PM
 */
public class OptionExtractFactory {
    private static List<OptionExtractChannel> optionExtractChannels = new ArrayList<>();

    private static Map<String, OptionExtractChannel> channelMap = null;

    static {
        optionExtractChannels.add(new DestinationOptionExtractChannnel());
        optionExtractChannels.add(new ParameterOptionExtractChannel());
        optionExtractChannels.add(new TemplateOptionExtractChannel());
        channelMap = optionExtractChannels.stream().collect(Collectors.toMap(OptionExtractChannel::getOption, Function.identity()));
    }

    public static OptionExtractChannel getChannel(String option) {
        return channelMap.get(option);
    }


}
