package backend.managers;

import backend.models.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class CourseManager {

    Map<Integer, Course> courseList = new HashMap<>();
    Map<String, Integer> categoryRequirements = new HashMap<>(); // name, amount req
    int graduationReq;
    static CourseManager instance;

    private CourseManager() {
        try {
            List<String> courseNames = Files.readAllLines(Paths.get("/backend/data/course_list/course_names.txt"));
            List<String> courseDescs = Files.readAllLines(Paths.get("/backend/data/course_list/course_descriptions.txt"));
            List<String> courseCodes = Files.readAllLines(Paths.get("/backend/data/course_list/course_codes.txt"));
            List<String> courseCategories = Files.readAllLines(Paths.get("/backend/data/course_list/course_categories.txt"));
            // courseList = IntStream.range(0, courseNames.size())
            //     .mapToObj(c -> {
            //         Course course = new Course(courseNames.get(c), courseDescs.get(c), courseCodes.get(c), courseCategories.get(c));
            //         return course;
            //     })
            //     .collect(
            //         Collectors.toCollection(()-> new ArrayList<>()) // so its mutable
            //     );
            for (int i = 0; i < courseNames.size(); i++) {
                courseList.put(Integer.parseInt(courseCodes.get(i)), new Course(courseNames.get(i), courseCodes.get(i), courseDescs.get(i), courseCategories.get(i)));
            }
            List<String> categories = Files.readAllLines(Paths.get("/backend/data/categories/category_names.txt"));
            List<Integer> requirements = Files.readAllLines(Paths.get("/backend/data/categories/category_requirements.txt")).stream()
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
        courseList.put(Integer.parseInt(newCourse.getCode()), newCourse);
        try {
            Files.writeString(Paths.get("/backend/data/course_list/course_names.txt"), "");
        } catch(IOException e) {
            System.out.print(e.getStackTrace());
        }
    }

    public Course getCourse(int idx) {
        return courseList.get(idx);
    }

    public void updateCourse(int idx, Course newCourse) {
        courseList.set(idx, newCourse);
    }

    public void printCourses() {
        for (int i = 0; i < courseList.size(); i++) {
            Course course = courseList.get(i);
            System.out.printf("%d. %s | %s (%s): %s\n", i+1, course.getCategory(), course.getName(), course.getCode(), course.getDesc());
        }
    }

    
}
