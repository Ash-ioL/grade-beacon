package backend.managers;

import backend.models.Student;
import backend.models.Teacher;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserManager {
    
    private static UserManager instance;
    private List<Student> students;
    private List<Teacher> registeredTeachers;
    private Teacher teacher = Teacher.get();

    private UserManager() {
        try {
            students = Files.readAllLines(Paths.get("backend/data/users/students.csv")).stream()
                .map(info -> {
                    String[] vals = info.split(",");
                    Student student = new Student(vals[0], vals[1], vals[2]);
                    student.setId(Integer.parseInt(vals[3]));
                    return student;
                })
                .collect(
                    Collectors.toList()
                );
            registeredTeachers = Teacher.getRegistered();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static UserManager get() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public void addStudent(Student student) { // Adds student and sets the id
        List<String> curUsers = students.stream()
            .map(s->s.getUsername())
            .toList();
        List<Integer> curIds = students.stream()
            .map(s->s.getId())
            .toList();

        pickID(student);
        
        if (curUsers.contains(student.getUsername()) || curIds.contains(student.getId())) {
            System.out.println("Student could not be added as the username/id is not unique.");
            return;
        }

        students.add(student);

        try {
            List<String> content = Files.readAllLines(
                Paths.get("backend/data/users/students.csv")
            );
            content.add(String.format("%s,%s,%s,%s", 
                student.getName(), 
                student.getUsername(), 
                student.getPassword(), 
                student.getFormattedID()
            ));
            Files.write(Paths.get("backend/data/users/students.csv"), content);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void removeStudent(Student student) { // if the student doesnt exist it does nothing
        students.remove(student);
        try {
            
            List<String> lines = students.stream()
                .map(s -> {
                    return String.format("%s,%s,%s,%s", s.getName(), s.getUsername(), s.getPassword(), s.getFormattedID());
                })
                .toList();
                
            Files.write(Paths.get("backend/data/users/students.csv"), lines);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void addTeacher(Teacher teacher) {
        List<String> curUsers = registeredTeachers.stream()
            .map(t->t.getUsername())
            .toList();
        if (curUsers.contains(teacher.getUsername())) {
            System.out.println("Teacher could not be registered as the username is not unique.");
            return;
        }
        try {
            Files.writeString(Paths.get("backend/data/users/teachers.csv"), String.format("\n%s,%s,%s", teacher.getName(), teacher.getUsername(), teacher.getPassword()));
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        registeredTeachers.add(teacher);
        Teacher.registerTeacher(teacher.getName(), teacher.getUsername(), teacher.getPassword());
    }

    public void removeTeacher(Teacher teacher) {
        registeredTeachers.remove(teacher);
        List<String> lines = registeredTeachers.stream()
            .map(t -> {
                return String.format("%s,%s,%s", teacher.getName(), teacher.getUsername(), teacher.getPassword());
            })
            .toList();
        try {
            Files.write(Paths.get("backend/data/users/teachers.csv"), lines);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
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
        Teacher.set(teacher);
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
