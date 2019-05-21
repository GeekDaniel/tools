package top.dannystone.deepcopier.runtime;


import junit.framework.TestCase;
import org.junit.Test;

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
        student.setAce(true);
        student.setAge(12);
        student.setName("daniel");
        ClassRoom classroom = new ClassRoom();
        classroom.setClassName("classRoom1");
        student.setClassRoom(classroom);
        student.setAce(true);
        Student studentCopy = DeepCopyUtils.deepCopy(student);
        assertTrue((studentCopy.getClassRoom()!= student.getClassRoom()&& studentCopy.getClassRoom().getClassName().equals(student.getClassRoom().getClassName())));


    }
}
