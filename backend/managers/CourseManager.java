package backend.managers;

import backend.models.Course;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseManager {

    Map<Integer, Course> courseList = new HashMap<>();
    Map<String, Integer> categoryRequirements = new HashMap<>(); // name, amount req
    int graduationReq;

    static CourseManager instance;

    private CourseManager() {
        try {
            List<String> courseNames = Files.readAllLines(Paths.get("backend/data/course_list/course_names.txt"));
            List<String> courseDescs = Files.readAllLines(Paths.get("backend/data/course_list/course_descriptions.txt"));
            List<String> courseCodes = Files.readAllLines(Paths.get("backend/data/course_list/course_codes.txt"));
            List<String> courseCategories = Files.readAllLines(Paths.get("backend/data/course_list/course_categories.txt"));
            // courseList = IntStream.range(0, courseNames.size())
            //     .mapToObj(c -> {
            //         Course course = new Course(courseNames.get(c), courseDescs.get(c), courseCodes.get(c), courseCategories.get(c));
            //         return course;
            //     })
            //     .collect(
            //         Collectors.toCollection(()-> new ArrayList<>()) // so its mutable
            //     );
            for (int i = 0; i < courseNames.size(); i++) {
                courseList.put(Integer.parseInt(courseCodes.get(i)), new Course(courseNames.get(i), Integer.parseInt(courseCodes.get(i)), courseDescs.get(i), courseCategories.get(i)));
            }
            List<String> categories = Files.readAllLines(Paths.get("backend/data/categories/category_names.txt"));
            List<Integer> requirements = Files.readAllLines(Paths.get("backend/data/categories/category_requirements.txt")).stream()
                .map(item -> Integer.parseInt(item))
                .toList();
            for (int i = 0; i < categories.size(); i++) {
                categoryRequirements.put(categories.get(i), requirements.get(i));
            }
        } catch (IOException E) {
            System.out.println(E.getStackTrace());
            System.exit(1);
        }

    }

    public static CourseManager get() {
        if (instance == null) {
            instance = new CourseManager();
        }
        return instance;
    }

    public void addCourse(Course newCourse) {
        if (courseList.containsKey(newCourse.getCode()) && courseList.get(newCourse.getCode()) == newCourse) {
            System.out.println("Course could not be added as the course code is not unique.");
            return;
        }

        courseList.put(newCourse.getCode(), newCourse);
        try {
            Files.writeString(Paths.get("backend/data/course_list/course_names.txt"), "\n"+newCourse.getName());
            Files.writeString(Paths.get("backend/data/course_list/course_descs.txt"), "\n"+newCourse.getDesc());
            Files.writeString(Paths.get("backend/data/course_list/course_codes.txt"), "\n"+newCourse.getFormattedCode());
            Files.writeString(Paths.get("backend/data/course_list/course_categories.txt"), "\n"+newCourse.getCategory());
            
        } catch(IOException e) {
            System.out.print(e.getStackTrace());
        }
    }

    public Course getCourse(int idx) {
        return courseList.get(idx);
    }

    public void removeCourse(int idx, Course newCourse) {
        //courseList.set(idx, newCourse);
    }

    public void resetData() {
        List<Course> courses = courseList.values().stream().toList();
        try {
            Files.write(Paths.get("backend/data/course_list/course_names.txt"), courses.stream().map(c->c.getName()).toList());
            Files.write(Paths.get("backend/data/course_list/course_descs.txt"), courses.stream().map(c->c.getDesc()).toList());
            Files.write(Paths.get("backend/data/course_list/course_codes.txt"), courses.stream().map(c->c.getFormattedCode()).toList());
            Files.write(Paths.get("backend/data/course_list/course_categories.txt"), courses.stream().map(c->c.getCategory()).toList());
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void printCourses() {
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            System.out.printf("%d. %s | %s (%s): %s\n", i+1, course.getCategory(), course.getName(), course.getCode(), course.getDesc());
        }
    }
    
}
