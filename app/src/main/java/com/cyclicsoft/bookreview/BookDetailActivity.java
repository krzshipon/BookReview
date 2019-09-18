package com.cyclicsoft.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cyclicsoft.bookreview.adapter.CommentListAdapter;
import com.cyclicsoft.bookreview.models.Book;
import com.cyclicsoft.bookreview.models.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookDetailActivity extends AppCompatActivity {

    private String id;
    TextView title, author, sum, ratings;
    private DatabaseReference mRef;
    ArrayList<Comment> comments = new ArrayList<>();
    CommentListAdapter adapter;
    private RecyclerView rv;
    EditText com, rat;
    Button addCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra(Constants.COLUMN_ID);
        setContentView(R.layout.activity_book_detail);
        title = findViewById(R.id.book_title_da);
        author = findViewById(R.id.book_author_da);
        sum = findViewById(R.id.book_sum_da);
        rv = findViewById(R.id.recycler_view_comment);
        com = findViewById(R.id.add_comment);
        rat = findViewById(R.id.add_ratings);
        addCom = findViewById(R.id.add_review);


        addCom.setOnClickListener(view -> {
            String comment = com.getText().toString();
            double ratings = Double.parseDouble(rat.getText().toString());

            mRef = FirebaseDatabase.getInstance().getReference().child(Constants.TABLE_BOOKS).child(id).child("comments");
            Map<String, Object> child = new HashMap<>();
            child.put("comment", comment);
            mRef.updateChildren(child);

        });


        mRef = FirebaseDatabase.getInstance().getReference().child(Constants.TABLE_BOOKS).child(id);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                title.setText(book.getTitle());
                author.setText(book.getAuthor());
                sum.setText(book.getSum());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dos();

        adapter = new CommentListAdapter(comments, book -> {

        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(adapter);



    }

    private void dos() {
        mRef = FirebaseDatabase.getInstance().getReference().child(Constants.TABLE_BOOKS).child(id).child("comments");
        Log.d("Check",  "dos");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Comment book = postSnapshot.getValue(Comment.class);
                    comments.add(book);
                    adapter.notifyDataSetChanged();
                    Log.d("Check",  "Has child"+ book.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
