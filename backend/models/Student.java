package backend.models;

import backend.managers.UserManager;

public class Student extends User{
    private String id = 0;

    public Student(String name, String username, String password) {
        super(name, username, password);
        id = UserManager.get()
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}