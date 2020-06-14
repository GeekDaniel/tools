package top.dannystone.templateCreator.fileWriter.enums;

import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/12 11:44 AM
 */
public enum FileTypeEnum {
    INTERFACE(1, "接口"),
    CLASS(2, "类");

    FileTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Getter
    private int value;

    @Getter
    private String desc;
}
