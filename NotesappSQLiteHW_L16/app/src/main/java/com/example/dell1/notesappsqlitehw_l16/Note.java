package com.example.dell1.notesappsqlitehw_l16;

public class Note {

    String title,desc,displayTime;
    Long id;
    Integer done;

    public Note(String title, String desc, String displayTime, Long id,Integer done) {
        this.title = title;
        this.desc = desc;
        this.displayTime = displayTime;
        this.id = id;
        this.done=done;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public Long getId() {
        return id;
    }

    public Integer getDone() {
        return done;
    }
}
