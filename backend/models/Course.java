package backend.models;

public class Course {

    private String name;
    private int code;
    private String description;
    private String category;

    public Course(String name, int code, String description, String category) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    public Course copy() {
        return new Course(name, code, description, category);
    }

    @Override
    public String toString() {
        return String.format("%s | %s (%s): %s\n", category, name, this.getFormattedCode(), description);
    }

    public String getFormattedCode() {
        return "0".repeat(Math.max(
            0,
            4-String.valueOf(code).length()
        )) + code;
    }
}
