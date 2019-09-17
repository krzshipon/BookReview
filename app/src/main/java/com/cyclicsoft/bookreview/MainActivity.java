package com.cyclicsoft.bookreview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.cyclicsoft.bookreview.adapter.BookListAdapter;
import com.cyclicsoft.bookreview.models.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageButton btGotoAddbook;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private RecyclerView recyclerView;
    private DatabaseReference mDbRef;
    private ArrayList<Book> mBooks = new ArrayList<>();
    private BookListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btGotoAddbook = findViewById(R.id.bt_goto_add_book);
        btGotoAddbook.setOnClickListener(view -> {
            Intent addBookIntent = new Intent(MainActivity.this, CreateBookActivity.class);
            startActivity(addBookIntent);
        });


        recyclerView = findViewById(R.id.recycler_view);

        init();
        dos();
        Log.d("Check",  "size" + mBooks.size());


        mAdapter = new BookListAdapter(mBooks, book -> {
            Intent bookDetails = new Intent(MainActivity.this, BookDetailActivity.class);
            bookDetails.putExtra(Constants.COLUMN_ID, book.getId());
            startActivity(bookDetails);
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    private void dos() {
        Log.d("Check",  "dos");
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mBooks.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Book book = postSnapshot.getValue(Book.class);
                    mBooks.add(book);
                    mAdapter.notifyDataSetChanged();
                    Log.d("Check",  "Has child"+ book.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        mDbRef = FirebaseDatabase.getInstance().getReference().child(Constants.TABLE_BOOKS);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Do u want to Sign out?")
                .setMessage("Student will no longer see the Route?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, (arg0, arg1) -> {
                   FirebaseAuth.getInstance().signOut();
                   super.onBackPressed();
                }).create().show();


    }
}
