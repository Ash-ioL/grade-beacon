
import backend.managers.UserManager;
import backend.models.Student;

public class Main {
    public static void main(String[] args) {
        UserManager authenticator = UserManager.get();
        Student student1 = new Student("Ashwath", "ashwath1234", "password1234");
        authenticator.addStudent(student1);
        System.out.print(authenticator.studentLogin("ashwath1234", "password1234"));
    }
}
