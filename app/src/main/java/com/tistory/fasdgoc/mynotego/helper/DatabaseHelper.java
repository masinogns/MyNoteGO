package com.tistory.fasdgoc.mynotego.helper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tistory.fasdgoc.mynotego.domain.Note;
import com.tistory.fasdgoc.mynotego.domain.User;
import com.tistory.fasdgoc.mynotego.event.ListingNote;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

/**
 * Created by fasdg on 2016-10-29.
 */

public class DatabaseHelper {
    private static final String TAG = "DatabaseHelper";
    private static FirebaseDatabase database;
    private static DatabaseReference users;
    private static DatabaseReference notes;

    private static HashMap<String, Note> noteMap;
    private static ValueEventListener notesListListener;
    private static ValueEventListener notesMetersListListener;

    static {
        database = FirebaseDatabase.getInstance();
        users = database.getReference("users");
        notes = database.getReference("notes");

        noteMap = new HashMap<>();
        notesListListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Note note = snapshot.getValue(Note.class);
                    noteMap.put(snapshot.getKey(), note);
                }
                EventBus.getDefault().post(new ListingNote(noteMap));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        notesMetersListListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public static void register() {
        noteMap.clear();
        notes.addValueEventListener(notesListListener);
    }

    public static void unregister() {
        notes.removeEventListener(notesListListener);
    }

    public static void register(float meters) {
        noteMap.clear();
        notes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //TODO
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static void unregister(float meters) {

    }

    public static void updateUser(String uid, User user) {
        users.child(uid)
                .setValue(user);
    }

    public static void addNote(Note note) {
        String key = notes.push().getKey();

        notes.child(key).setValue(note);
    }

    public static void removeNote(String key) {
        notes.child(key).removeValue();
    }
}
