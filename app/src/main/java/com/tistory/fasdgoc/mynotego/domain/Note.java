package com.tistory.fasdgoc.mynotego.domain;

import java.util.Date;

/**
 * Created by fasdg on 2016-10-29.
 */

public class Note {
    private String uid;
    private String title;
    private String content;
    private Position position;
    private Orientation orientation;
    private Date date;

    public Note() {
    }

    public Note(String uid, String title, String content, Position position, Orientation orientation, Date date) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.position = position;
        this.orientation = orientation;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
