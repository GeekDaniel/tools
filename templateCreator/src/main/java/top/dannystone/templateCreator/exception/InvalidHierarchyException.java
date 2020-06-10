package top.dannystone.templateCreator.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 6:47 PM
 */
public class InvalidHierarchyException extends RuntimeException {
    public InvalidHierarchyException(String message) {
        super(message);
    }

    public InvalidHierarchyException(String message, Throwable e) {
        super(message, e);
    }
}
