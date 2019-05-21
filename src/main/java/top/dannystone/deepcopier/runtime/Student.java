package top.dannystone.deepcopier.runtime;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/4/17 8:30 AM
 */
public class Student extends Person{

    private boolean ace;
    private ClassRoom classRoom;

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }

    public Student() {

    }

    public boolean isAce() {
        return ace;
    }

    public void setAce(boolean ace) {
        this.ace = ace;
    }

    @Override
    public String toString(){
        return this.getName()+","+this.getAge()+","+classRoom.getClassName();
    }

    public static void main(String[] args){
        Student student = new Student();

        System.out.println(student.getClass().getSuperclass().getName());

    }
}

