package backend.models;

import java.util.Arrays;
import java.util.List;

public class Schedule {

    private List<Course> courses;

    public Schedule(Course... courses) {
        this.courses = Arrays.asList(courses);
    }
    
    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void updateCourse(int idx, Course course) {
        this.courses.set(idx, course);
    }

    public void removeCourse(int idx) {
        this.courses.remove(idx);
    }

    public void printSchedule() {
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.printf("%d. %s | %s (%s): %s\n", i+1, course.getCategory(), course.getName(), course.getCode(), course.getDesc());
        }
    }
}
