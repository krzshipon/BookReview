package com.cyclicsoft.bookreview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cyclicsoft.bookreview.utils.CSharedPrefUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateBookActivity extends AppCompatActivity {
    EditText etTitle, etAuthor, etSumm, etUrl;
    Button btAddBook;
    private DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_book);
        intUi();
    }

    private void intUi() {
        etTitle = findViewById(R.id.tv_book_title);
        etTitle.setText(CSharedPrefUtil.getString(getApplicationContext(),Constants.COLUMN_BTITLE,""));
        etAuthor = findViewById(R.id.tv_book_author);
        etAuthor.setText(CSharedPrefUtil.getString(getApplicationContext(),Constants.COLUMN_BAUTHOR,""));
        etSumm = findViewById(R.id.tv_book_summary);
        etSumm.setText(CSharedPrefUtil.getString(getApplicationContext(),Constants.COLUMN_BSUMM,""));
        etUrl = findViewById(R.id.tv_book_url);
        etUrl.setText(CSharedPrefUtil.getString(getApplicationContext(),Constants.COLUMN_BURL,""));
        btAddBook = findViewById(R.id.bt_add_book);


        btAddBook.setOnClickListener(view -> {
            String title = etTitle.getText().toString();
            String author = etAuthor.getText().toString();
            String summ = etSumm.getText().toString();
            String url = etUrl.getText().toString();

            if(TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || TextUtils.isEmpty(summ) || TextUtils.isEmpty(url)){
                Toast.makeText(this, "Please fill up all field", Toast.LENGTH_SHORT).show();
            }
            mref = FirebaseDatabase.getInstance().getReference().child(Constants.TABLE_BOOKS);
            String id = mref.push().getKey();
            Map<String, Object> child = new HashMap<>();
            child.put(Constants.COLUMN_ID, id);
            child.put(Constants.COLUMN_BTITLE, title);
            CSharedPrefUtil.saveValue(getApplicationContext(),Constants.COLUMN_BTITLE, title);
            child.put(Constants.COLUMN_BAUTHOR, author);
            CSharedPrefUtil.saveValue(getApplicationContext(),Constants.COLUMN_BAUTHOR, author);
            child.put(Constants.COLUMN_BSUMM, summ);
            CSharedPrefUtil.saveValue(getApplicationContext(),Constants.COLUMN_BSUMM, summ);
            child.put(Constants.COLUMN_BURL, url);
            CSharedPrefUtil.saveValue(getApplicationContext(),Constants.COLUMN_BURL, url);


            mref.child(id).updateChildren(child)
            .addOnSuccessListener(aVoid -> {
                super.onBackPressed();
            })
            .addOnFailureListener(e -> {
                Toast.makeText(this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
            });


        });

    }
}
