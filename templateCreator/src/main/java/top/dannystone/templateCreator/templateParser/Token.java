package top.dannystone.templateCreator.templateParser;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 3:03 PM
 */
@Data
@AllArgsConstructor
public class Token {
    private String value;
    private int hierarchy;
}
