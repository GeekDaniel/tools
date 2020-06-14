package top.dannystone.templateCreator.fileWriter.domain;

import lombok.Data;

import java.io.File;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/12 11:42 AM
 */
@Data
public class FileWriteContext {
    private File file;
    /**
     * @see top.dannystone.templateCreator.fileWriter.enums.FileTypeEnum
     */
    private String type;

    private Map<String, String> args;

}
