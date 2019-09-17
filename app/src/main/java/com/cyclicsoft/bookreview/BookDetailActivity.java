package com.cyclicsoft.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.cyclicsoft.bookreview.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetailActivity extends AppCompatActivity {

    private String id;
    TextView title, author, sum, ratings;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getStringExtra(Constants.COLUMN_ID);
        setContentView(R.layout.activity_book_detail);
        title = findViewById(R.id.book_title_da);
        author = findViewById(R.id.book_author_da);
        sum = findViewById(R.id.book_sum_da);


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
    }
}
