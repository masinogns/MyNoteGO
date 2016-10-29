package com.tistory.fasdgoc.mynotego.domain;

/**
 * Created by fasdg on 2016-10-29.
 */

public class KeyWithNote {
    private String key;
    private Note note;

    public KeyWithNote() {
    }

    public KeyWithNote(String key, Note note) {
        this.key = key;
        this.note = note;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
