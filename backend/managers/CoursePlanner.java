package backend.managers;

import backend.managers.UserManager;
import backend.models.Student;
import backend.models.Course;
import backend.models.Schedule;

import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class CoursePlanner {
    
    static CoursePlanner instance;

    public List<Schedule> schedules = new ArrayList<>();

    private CoursePlanner() {
        schedules = Files.list(Paths.get("/backend/data/schedules")).map()
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

}