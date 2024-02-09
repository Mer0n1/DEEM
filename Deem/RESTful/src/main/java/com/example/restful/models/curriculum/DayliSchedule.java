package com.example.restful.models.curriculum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayliSchedule {
    public Date date; //day mouth
    public List<Class> classes;

    public DayliSchedule() {
        classes = new ArrayList<>();
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
