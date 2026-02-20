package backend.models;

public class Student {
    private String name;
    private String username;
    private String password;
    private int id;

    public Student(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
            String.format("Name: %s\n"+
                          "Username: %s\n"+
                          "Password: %s\n",
                          name, username, password);
    }
}