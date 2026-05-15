package backend.models;

import backend.managers.UserManager;

public class Teacher extends User{
    private String name;
    private String username;
    private String password;
    private Teacher teacher;

    private Teacher(String name, String username, String password) {
        super(name, username, password);
    }

    public Teacher set(String name, String username, String password) {
        if (teacher == null) {
            teacher = new Teacher(name, username, password);
            UserManager.get().setTeacher(teacher);
        }
        return teacher;
    }

    public Teacher get() {
        if (teacher == null) {
            return new Teacher("No Teacher Set", "NA", "Password");
        }
        return teacher;
    }
}
