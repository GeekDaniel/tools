package top.dannystone.deepcopier.runtime;

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

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    private ClassRoom classRoom;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString(){
        return name+","+age+","+classRoom.getClassName();
    }
}

