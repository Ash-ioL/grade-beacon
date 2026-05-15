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
        int id = (int)(Math.random()*1000);
        int idx = 0;
        int mod = 10;
        int n = ids.size();
        int lowest = -1;
        boolean found = false;
        while (lowest == -1) {
            if (idx == n) {
                idx = 0;
                mod *= 10;
            }
            idx += 1;
            id += 1;
        }
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
}
