package com.tistory.fasdgoc.mynotego.domain;

import java.io.Serializable;

/**
 * Created by fasdg on 2016-10-28.
 */

public class User implements Serializable {
    private String name;
    private String email;
    private String photo;

    public User() {

    }

    public User(String name, String email, String photo) {
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
