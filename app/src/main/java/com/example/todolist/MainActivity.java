package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<Todo> todos = new ArrayList<Todo>();
    TodosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context ct = this;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference todosRef = db.collection("todo");
        Query query = todosRef.whereNotEqualTo("content", ".");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("debug", document.getId() + " => " + document.getData());
                    Todo newTodo = new Todo(document.getId(), document.getData().get("content").toString(), (Boolean) document.getData().get("done"));
                    adapter.add(newTodo);
                }
            }
        });

        adapter = new TodosAdapter(this, todos);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);


    }
}