package com.geshtop.firstproject.Data;

import androidx.annotation.NonNull;

import com.geshtop.firstproject.Entities.Travel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Firebase_DBManager {


    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }

    static DatabaseReference TravelRef;
    static List<Travel> travelsList;

    static {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        TravelRef = database.getReference("TraveRequest");
        travelsList = new ArrayList<>();
    }


    public static void addTravel(final Travel travel, final Action<Long> action) {
        if (travel.isValid()) {
            // upload image
            addTravelToFirebase(travel, action);
        } else
            action.onFailure(new Exception("ID is required"));
    }

    private static void addTravelToFirebase(final Travel travel, final Action<Long> action) {
        //String key = travel.getTravelId().toString();
        //TravelRef.child(key).setValue(travel)
        TravelRef.push().setValue(travel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //action.onSuccess(travel.getTravelId());
                action.onProgress("upload Travel data", 100);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                action.onFailure(e);
                action.onProgress("error upload student data", 100);

            }
        });
    }


    /**
     public static void removeStudent(long id, final Action<Long> action) {

     final String key = ((Long) id).toString();

     StudentsRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
    final Student value = dataSnapshot.getValue(Student.class);
    if (value == null)
    action.onFailure(new Exception("student not find ..."));
    else {
    StorageReference imagesRef = FirebaseStorage.getInstance().getReferenceFromUrl(value.getImageFirebaseUrl());
    imagesRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
    StudentsRef.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
    action.onSuccess(value.getId());
    }
    }).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
    action.onFailure(e);
    }
    });
    }
    }).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
    action.onFailure(e);
    }
    });
    }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    action.onFailure(databaseError.toException());
    }
    });
     }

     public static void updateStudent(final Student toUpdate, final Action<Long> action) {
     final String key = ((Long) toUpdate.getId()).toString();

     removeStudent(toUpdate.getId(), new Action<Long>() {
    @Override
    public void onSuccess(Long obj) {
    addStudent(toUpdate, action);
    }

    @Override
    public void onFailure(Exception exception) {
    action.onFailure(exception);
    }

    @Override
    public void onProgress(String status, double percent) {
    action.onProgress(status, percent);
    }
    });
     }

     private static ChildEventListener studentRefChildEventListener;

     public static void notifyToStudentList(final NotifyDataChange<List<Student>> notifyDataChange) {
     if (notifyDataChange != null) {

     if (studentRefChildEventListener != null) {
     notifyDataChange.onFailure(new Exception("first unNotify student list"));
     return;
     }
     studentList.clear();

     studentRefChildEventListener = new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
    Student student = dataSnapshot.getValue(Student.class);
    String id = dataSnapshot.getKey();
    student.setId(Long.parseLong(id));
    studentList.add(student);


    notifyDataChange.OnDataChanged(studentList);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
    Student student = dataSnapshot.getValue(Student.class);
    Long id = Long.parseLong(dataSnapshot.getKey());
    student.setId(id);


    for (int i = 0; i < studentList.size(); i++) {
    if (studentList.get(i).getId().equals(id)) {
    studentList.set(i, student);
    break;
    }
    }
    notifyDataChange.OnDataChanged(studentList);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
    Student student = dataSnapshot.getValue(Student.class);
    Long id = Long.parseLong(dataSnapshot.getKey());
    student.setId(id);

    for (int i = 0; i < studentList.size(); i++) {
    if (studentList.get(i).getId() == id) {
    studentList.remove(i);
    break;
    }
    }
    notifyDataChange.OnDataChanged(studentList);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
    notifyDataChange.onFailure(databaseError.toException());
    }
    };
     StudentsRef.addChildEventListener(studentRefChildEventListener);
     }
     }

     public static void stopNotifyToStudentList() {
     if (studentRefChildEventListener != null) {
     StudentsRef.removeEventListener(studentRefChildEventListener);
     studentRefChildEventListener = null;
     }

     }
     **/
}
