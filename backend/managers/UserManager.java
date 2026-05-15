package backend.managers;

import backend.models.Student;
import backend.models.Teacher;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserManager {
    
    private static UserManager instance;
    private List<Student> students = new ArrayList<>();
    private Teacher teacher = null;

    private UserManager() {

    }

    public static UserManager get() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void addStudent(Student student) { // Adds student and sets the id
        students.add(student);
        pickID(student);
    }
    
    private void pickID(Student student) {
        List<Integer> ids = new ArrayList<>();
        for (Student i : students) {
            ids.add((Integer.valueOf(i.getId())));
        }
        ids = ids.stream()
            .sorted((a, b) -> a - b)
            .toList();
        int id = 0;
        for (int i = 0; i < ids.size() && id >= ids.get(i); i++) {
            if (id == ids.get(i)) 
                id += 1;
        }
        student.setId(id);
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Optional<Student> studentLogin(String username, String password) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getUsername().equals(username) && students.get(i).getPassword().equals(password)) {
                return Optional.of(students.get(i));
            }
        }
        return Optional.empty();
    }

    public void printStudents() {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.printf("%s\n", students.get(i).toString());
        }
    }

    public void printTeacher() {
        System.out.print(teacher.toString());
    }
}
