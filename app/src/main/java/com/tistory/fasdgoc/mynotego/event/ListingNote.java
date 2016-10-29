package com.tistory.fasdgoc.mynotego.event;

import com.tistory.fasdgoc.mynotego.domain.Note;

import java.util.HashMap;

/**
 * Created by fasdg on 2016-10-29.
 */

public class ListingNote {
    public HashMap<String, Note> map;

    public ListingNote(HashMap<String, Note> map) {
        this.map = map;
    }
}
