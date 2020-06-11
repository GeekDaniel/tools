package top.dannystone.templateCreator.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 12:17 PM
 */
public class FileCreateException extends RuntimeException {
    public FileCreateException(String message) {
        super(message);
    }

    public FileCreateException(String message, Throwable e) {
        super(message, e);
    }
}
