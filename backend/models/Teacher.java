package backend.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import backend.managers.UserManager;

public class Teacher extends User{
    private static Teacher teacher;

    private Teacher(String name, String username, String password) {
        super(name, username, password);
    }

    public static Teacher set(String name, String username, String password) {
        if (teacher == null) {
            teacher = new Teacher(name, username, password);
            UserManager.get().setTeacher(teacher);
        }
        return teacher;
    }

    public static Teacher set(Teacher teach) {
        teacher = teach;
        return teacher;
    }

    public static Teacher get() {
        if (teacher == null) {
            return new Teacher("No Teacher Signed In", "NA", "NA");
        }
        return teacher;
    }

    public static List<Teacher> getRegistered() {
        try {
            return Files.readAllLines(Paths.get("/backend/data/users/teachers.csv")).stream()
                .map(info -> {
                    String[] vals = info.split(",");
                    Teacher teacher = new Teacher(vals[0], vals[1], vals[2]);
                    return teacher;
                })
                .collect(
                    Collectors.toList()
                );
        } catch(IOException e) {
            System.out.print(e.getStackTrace());
            System.exit(1);
        }
        return new ArrayList<Teacher>();
    }

    public static Teacher registerTeacher(String name, String username, String password) {
        Teacher teacher = new Teacher(name, username, password);
        try {
            Files.writeString(Paths.get("/backend/data/users/teachers.csv"), String.format("\n%s,%s,%s", teacher.getName(), teacher.getUsername(), teacher.getPassword()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return teacher;
    }

    @Override
    public String toString() {
        return String.format("Teacher---------\nName: %s\nUsername: %s\nPassword: %s\n", this.getName(), this.getUsername(), this.getPassword());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Teacher) {
            Teacher teacher = (Teacher) o;
            return teacher.getName() == this.getName() &&
            teacher.getUsername() == this.getUsername() &&
            teacher.getPassword() == this.getPassword();
        }
        return false;
    }
}
