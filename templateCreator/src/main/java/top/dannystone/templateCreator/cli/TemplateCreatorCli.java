package top.dannystone.templateCreator.cli;

import top.dannystone.templateCreator.cli.domain.TemplateCreateContext;
import top.dannystone.templateCreator.creator.TemplateFileCreator;
import top.dannystone.templateCreator.exception.ArgsNotValidException;
import top.dannystone.templateCreator.exception.ContextNotValidException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * usage: tc [-t template] [-P key=value -P key2=value2...] [-d destination]
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 2:29 PM
 */
public class TemplateCreatorCli {
    public static void main(String[] args) {
        //参数extract
        TemplateCreateContext templateCreateContext = extractTemplateCreateContext(args);

        //上下文校验
        try {
            templateCreateContext.valid();
        } catch (ContextNotValidException e) {
            errorExit(e.getMessage());
        }

        TemplateFileCreator.create(templateCreateContext.getDestination(), templateCreateContext.getTemplateAbsolutePath(), templateCreateContext.getParams());

    }

    private static TemplateCreateContext extractTemplateCreateContext(String[] args) {
        TemplateCreateContext templateCreateContext = new TemplateCreateContext();

        int countedArgs = 0;
        for (int i = 0; i + 1 < args.length; i++) {
            String option = args[i];
            String optionValue = args[++i];
            countedArgs += 2;
            OptionExtractChannel channel = OptionExtractFactory.getChannel(option);

            if (channel == null) {
                String errorMsg = String.format("未知选项 : %s", option);
                errorExit(errorMsg);
            }

            try {
                channel.extractToContext(optionValue, templateCreateContext);
            } catch (ArgsNotValidException e) {
                errorExit(e.getMessage());
            }

        }

        if (countedArgs < args.length) {
            errorExit(String.format("%s参数无法解析", args[args.length - 1]));
        }
        return templateCreateContext;
    }

    private static void errorExit(String errorMsg) {
        System.out.println(errorMsg);
        System.exit(-1);
    }
}
