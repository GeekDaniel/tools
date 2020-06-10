package top.dannystone.templateCreator.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 6:26 PM
 */
public class TokenAnalizeException extends RuntimeException {
    public TokenAnalizeException(String message) {
        super(message);
    }

    public TokenAnalizeException(String message, Throwable e) {
        super(message, e);
    }

}
