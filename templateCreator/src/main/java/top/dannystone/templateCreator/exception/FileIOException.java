package top.dannystone.templateCreator.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/11 1:48 PM
 */
public class FileIOException extends RuntimeException{
    public FileIOException(String message) {
        super(message);
    }

    public FileIOException(String message, Throwable e) {
        super(message, e);
    }

}
