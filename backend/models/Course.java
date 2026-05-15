package backend.models;

import java.util.Locale.Category;

public class Course {

    private String name;
    private String code;
    private String description;
    private String category;

    public Course(String name, String code, String description, String category) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
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
}
