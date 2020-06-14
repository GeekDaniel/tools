package top.dannystone.templateCreator.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/14 10:49 PM
 */
public class NoTemplateForFileException extends RuntimeException {
    public NoTemplateForFileException(String message) {
        super(message);
    }

    public NoTemplateForFileException(String message, Throwable e) {
        super(message, e);
    }
}
