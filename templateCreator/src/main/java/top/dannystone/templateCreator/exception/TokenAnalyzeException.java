package top.dannystone.templateCreator.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/6/10 6:26 PM
 */
public class TokenAnalyzeException extends RuntimeException {
    public TokenAnalyzeException(String message) {
        super(message);
    }

    public TokenAnalyzeException(String message, Throwable e) {
        super(message, e);
    }

}
