package com.cyclicsoft.bookreview.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cyclicsoft.bookreview.R;
import com.cyclicsoft.bookreview.models.Book;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.BookViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
    private List<Book> adminList;
    private OnItemClickListener listener;

    public BookListAdapter(List<Book> adminList, OnItemClickListener listener) {
        this.adminList = adminList;
        this.listener = listener;
    }


    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book_list, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.Title.setText(adminList.get(position).getTitle());
        holder.Author.setText(String.valueOf(adminList.get(position).getAuthor()));
        holder.Ratings.setText(String.valueOf(adminList.get(position).getRatings()));
        holder.bind(adminList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public TextView Author;
        public TextView Ratings;

        public BookViewHolder(View view) {
            super(view);
            Title = view.findViewById(R.id.book_title);
            Author = view.findViewById(R.id.book_author);
            Ratings = view.findViewById(R.id.book_rating);
        }

        public void bind(final Book book, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });
        }

    }

}
