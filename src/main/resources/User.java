import top.dannystone.deepcopier.annotation.DeepCopier;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/4/17 8:30 AM
 */
public class User {
    private int age;
    private String name;
    private boolean ace;

    public User() {

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAce() {
        return ace;
    }

    public void setAce(boolean ace) {
        this.ace = ace;
    }
}

