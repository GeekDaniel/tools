package top.dannystone.templateCreator.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/12 12:03 PM
 */
public class WriteTemplateNotFound extends RuntimeException {
    public WriteTemplateNotFound(String message) {
        super(message);
    }

    public WriteTemplateNotFound(String message, Throwable e) {
        super(message, e);
    }
}
