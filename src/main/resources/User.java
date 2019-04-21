import top.dannystone.deepcopier.annotation.DeepCopier;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/4/17 8:30 AM
 */
@DeepCopier
public class User {
    private int age;
    private String name;

    public User() {

    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("daniel");
        user.setAge(11);
        System.out.println(user);
//        User clone = user.deepCopy();
//        System.out.println(clone);
//        System.out.println(user.equals(clone));
    }

    public String toString() {
        return "name:" + this.getName() + ",age:" + this.getAge();
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
