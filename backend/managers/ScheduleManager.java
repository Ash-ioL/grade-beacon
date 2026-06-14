package backend.managers;

import backend.managers.UserManager;
import backend.models.Student;
import backend.models.Course;
import backend.models.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class ScheduleManager {
    
    static ScheduleManager instance;

    public Map<String, Schedule> schedules = new HashMap<>();

    private ScheduleManager() {
        try {
            List<String> schedNames = Files.list(Paths.get("/backend/data/schedules"))
                .map(p -> p.getFileName().toString())
                .toList();
            
            for (String s : schedNames) {
                List<String> lines = Files.readAllLines(Paths.get("/backend/data/schedules/"+s));
                Course[] courses = lines.stream()
                    .map(line -> (Course) CourseManager.get().getCourse(Integer.parseInt(line)))
                    .toArray(Course[]::new);
                this.schedules.put(s.substring(0, s.length()-4), new Schedule(courses)); // to get rid of .txt
            }
        } catch (IOException e) {
            System.out.print(e.getStackTrace());
            System.exit(1);
        }
    }

    public static ScheduleManager get() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    public void putSchedule(String name, Schedule schedule) {
        schedules.put(name, schedule);
        updateFile(name);
    }

    public void removeSchedule(String name) {
        schedules.remove(name);
        try {
            Files.delete(Paths.get("/backend/data/schedules/"+name+".txt"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void updateFile(String name) {
        try {
            Files.write(Paths.get("/backend/data/schedules/"+name+".txt"), schedules.get(name).getCourses().stream().map(c->(String)c.getFormattedCode()).toList());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void updateAllFiles() {
        try {
            for (Path i : Files.list(Paths.get("/backend/data/schedules")).toList()){
                Files.delete(i);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        for (String i: schedules.keySet()) {
            updateFile(i);
        }
    }
}