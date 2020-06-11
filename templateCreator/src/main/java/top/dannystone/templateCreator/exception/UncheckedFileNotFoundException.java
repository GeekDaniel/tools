package top.dannystone.templateCreator.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 1:47 PM
 */
public class UncheckedFileNotFoundException extends RuntimeException{

    public UncheckedFileNotFoundException(String message) {
        super(message);
    }

    public UncheckedFileNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
