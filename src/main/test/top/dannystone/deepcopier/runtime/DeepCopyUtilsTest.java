package top.dannystone.deepcopier.runtime;


import junit.framework.TestCase;
import org.junit.Test;
import top.dannystone.deepcopier.runtime.domain.ClassRoom;
import top.dannystone.deepcopier.runtime.domain.Student;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: focus on the domain !
 * @Time: 2019/5/22 12:16 AM
 */
public class DeepCopyUtilsTest extends TestCase {

    @Test
    public void testDeepCopy(){
        Student student = new Student();
        student.setAge(12);
        student.setName("daniel");
        student.setAce(true);

        ClassRoom classroom = new ClassRoom();
        classroom.setClassName("classRoom1");
        student.setClassRoom(classroom);
        Student studentCopy = DeepCopyUtils.deepCopy(student);

        //测试继承属性
        assertTrue(student.getAge()==studentCopy.getAge());
        assertTrue(student.isAce()==studentCopy.isAce());
        assertTrue(student.getName().equals(studentCopy.getName()));

        //测试深复制
        assertTrue((studentCopy.getClassRoom()!= student.getClassRoom()&& studentCopy.getClassRoom().getClassName().equals(student.getClassRoom().getClassName())));


    }
}
