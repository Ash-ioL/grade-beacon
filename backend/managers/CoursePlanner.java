package backend.managers;

import backend.models.Course;
import backend.models.Schedule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CoursePlanner {
    
    static CoursePlanner instance;

    public List<Schedule> schedules = new ArrayList<>();

    private CoursePlanner() {
        try {
            // schedules = Files.list(Paths.get("backend/data/schedules"))
            //     .map(schedulePath -> {
            //         Course[] courses = Files.readAllLines(schedulePath).stream()
            //             .map(courseStr -> (Course) CourseManager.get().getCourse(Integer.valueOf(courseStr)))
            //             .toArray((e)->new Course[e]);
            //         return new Schedule(courses);
            //     })
            //     .collect(Collectors.toList());
            List<Path> paths;
            try (var files = Files.list(Paths.get("backend/data/schedules"))) {
                paths = files.toList();
            }
            for (Path path : paths) {
                Course[] courses = Files.readAllLines(path).stream()
                    .map(courseStr -> (Course) CourseManager.get().getCourse(Integer.parseInt(courseStr)))
                    .toArray(Course[]::new);
                schedules.add(new Schedule(courses));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static CoursePlanner get() {
        if (instance == null) {
            instance = new CoursePlanner();
        }
        return instance;
    }

    public void addSchedule(Schedule schedule) {
        schedules.add(schedule);
    }

    public void removeSchedule(int idx) {
        schedules.remove(idx);
    }

    public void updateSchedules(int idx, Schedule _new) {
        schedules.set(idx, _new);
    }

    public void printSchedules() {
        for (int i = 0; i < schedules.size(); i++) {
            System.out.printf("%d. ", i);
            schedules.get(i).printSchedule();
        }
    }
}