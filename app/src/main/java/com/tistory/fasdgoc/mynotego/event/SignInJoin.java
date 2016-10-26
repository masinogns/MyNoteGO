package com.tistory.fasdgoc.mynotego.event;

/**
 * Created by fasdg on 2016-10-27.
 */

public class SignInJoin {
    public boolean status;
    public SignInJoin(boolean isSuccess) {
        this.status = isSuccess;
    }
}
