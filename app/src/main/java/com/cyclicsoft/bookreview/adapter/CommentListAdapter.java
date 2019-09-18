package com.cyclicsoft.bookreview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cyclicsoft.bookreview.R;
import com.cyclicsoft.bookreview.models.Book;
import com.cyclicsoft.bookreview.models.Comment;

import java.util.List;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Comment book);
    }

    private List<Comment> adminList;
    private OnItemClickListener listener;

    public CommentListAdapter(List<Comment> adminList, OnItemClickListener listener) {
        this.adminList = adminList;
        this.listener = listener;
    }


    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment_list, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.comment.setText(adminList.get(position).getComment());
        holder.owner.setText(String.valueOf(adminList.get(position).getOwner()));
        holder.bind(adminList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return adminList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView comment;
        public TextView owner;

        public CommentViewHolder(View view) {
            super(view);
            comment = view.findViewById(R.id.recycler_view_comment);
            owner = view.findViewById(R.id.owner);
        }

        public void bind(final Comment book, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(book);
                }
            });
        }

    }

}
