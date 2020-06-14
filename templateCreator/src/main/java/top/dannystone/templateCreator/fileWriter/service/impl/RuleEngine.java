package top.dannystone.templateCreator.fileWriter.service.impl;

import com.google.common.collect.Lists;
import lombok.Data;
import top.dannystone.templateCreator.exception.NoTemplateForFileException;
import top.dannystone.templateCreator.fileWriter.domain.FileWriteContext;
import top.dannystone.templateCreator.fileWriter.utils.JavaFileWriteUtils;
import top.dannystone.templateCreator.utils.EnvUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * parsing rules, adding objects to an engine, firing rules and getting resultant objects from the engine
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/12 3:17 PM
 */
public class RuleEngine {

    private static final String SUFFIX_SPLIT = "\\.";

    private static final String JAVA_FILE_SUFFIX = "java";

    private static final String ENDS_WITH_KEY = "endsWith";

    private static final int LOWEST_PRIVILEGE = -2;

    private static final int NOT_SPECIFIED_PRIVILEGE = -1;

    private static final Rule DEFAULT_RULE = new Rule();

    static {
        DEFAULT_RULE.setPrivilege(LOWEST_PRIVILEGE);
        //todo 目前仅支持java  JavaFileWriteUtils 先写死
        DEFAULT_RULE.setAction(fwc -> {
            File defaultTemplateByFileSuffix = EnvUtils.getDefaultTemplateByFileSuffix("java");
            JavaFileWriteUtils.write(fwc.getFile(), defaultTemplateByFileSuffix.getPath(), fwc.getArgs());
            return fwc.getFile();
        });

        DEFAULT_RULE.setCondition(c -> true);

    }

    @Data
    private static class Rule implements Comparable<Rule> {

        private Predicate<FileWriteContext> condition;

        private Function<FileWriteContext, File> action;

        private int privilege;


        @Override
        public int compareTo(Rule o) {
            return o.privilege - this.privilege;
        }
    }

    public File execute(List<File> templates, FileWriteContext fileWriteContext) {

        List<Rule> rules = parsingRules(templates);

        for (Rule rule : rules) {
            if (rule.getCondition().test(fileWriteContext)) {

                // firing rules : 写文件
                // return result: 返回渲染后的文件
                return rule.getAction().apply(fileWriteContext);
            }
        }

        throw new NoTemplateForFileException("文件没有找到写入模板");

    }

    /**
     * 模板名格式为${matchRule}.template ${matchRule}的值全部为小写
     * matchRule 为key_privilege 其中key 为endsWith ;
     * privilege适用于一个文件被多个规则击中后的优先选择哪个？ privilege 值越大 优先级越高
     * parsing rules : 模板文件就是规则，需要根据文件名，解析出 if 'fileName startsWith ${prefix}' then 'write file content using this template' 的语义规则
     */
    List<Rule> parsingRules(List<File> templates) {
        List<Rule> rules = Lists.newArrayList();
        for (File templateFile : templates) {
            String name = templateFile.getName();
            String matchRule = name.substring(0, name.indexOf(SUFFIX_SPLIT));
            String[] split = matchRule.split("_");
            boolean privilegeSpecified = split.length == 2;
            String key = split[0];
            int privilege = privilegeSpecified ? Integer.parseInt(split[1]) : NOT_SPECIFIED_PRIVILEGE;

            Rule rule = new Rule();
            if (key.equals("default")) {
                continue;
            }

            //目前仅处理"endsWithxxx_12"的模板格式
            if (matchRule.startsWith(ENDS_WITH_KEY)) {
                rule.setCondition(fwc -> {
                    String fileName = fwc.getFile().getName();
                    return fileName.toLowerCase().endsWith(key);
                });

                rule.setAction(fwc -> {
                    File file = fwc.getFile();
                    JavaFileWriteUtils.write(file, EnvUtils.getTemplateHomeByFileSuffix(JAVA_FILE_SUFFIX) + "/" + key + ".template", fwc.getArgs());
                    return file;
                });

                rule.setPrivilege(privilege);

                rules.add(rule);
            }
        }

        Collections.sort(rules);

        //default 模板优先级最低，排在最后
        rules.add(DEFAULT_RULE);

        return rules;
    }


}
