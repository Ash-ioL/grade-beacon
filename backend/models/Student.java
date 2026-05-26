package backend.models;

public class Student extends User{

    private int id;

    public Student(String name, String username, String password) {
        super(name, username, password);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nUsername: %s\nPassword: %s\nID: %s\n", this.getName(), this.getUsername(), this.getPassword(), getFormattedID());
    }

    public String getFormattedID() {
        return "0".repeat(Math.max(
            0,
            4-String.valueOf(id).length()
        )) + id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Student) {
            Student student = (Student) o;
            return student.getUsername().equals(this.getUsername()) && 
                student.getName().equals(this.getName()) && 
                student.getPassword().equals(this.getPassword()) && 
                student.getId() == this.getId();
        }
        return false;
    }
}