package top.dannystone.templateCreator.cli.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import top.dannystone.templateCreator.exception.ContextNotValidException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 4:18 PM
 */
@Data
public class TemplateCreateContext {
    private String templateAbsolutePath;
    private Map<String, String> params=new HashMap<>();
    private String destination;

    public boolean valid() throws ContextNotValidException {
        if (StringUtils.isEmpty(this.templateAbsolutePath)) {
            throw new ContextNotValidException("缺少模板!");
        }

        if (StringUtils.isEmpty(this.destination)) {
            throw new ContextNotValidException("缺少生成目录！");
        }

        return true;

    }

}
