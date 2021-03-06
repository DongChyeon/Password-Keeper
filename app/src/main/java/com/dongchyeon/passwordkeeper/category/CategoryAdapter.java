package com.dongchyeon.passwordkeeper.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements OnCategoryItemClickListener {
    private List<String> items = new ArrayList<>();
    OnCategoryItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.category_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<String> getItems() {
        return items;
    }

    public void setOnItemClickListener(OnCategoryItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public ViewHolder(View itemView, final CategoryAdapter listener) {
            super(itemView);

            title = itemView.findViewById(R.id.titleText);
            image = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(ViewHolder.this, view, position);
                }
            });
        }

        public void setItem(String item) {
            title.setText(item);
            if (item.equals("새 항목 추가")) {
                image.setImageResource(R.drawable.ic_add_button);
            } else {
                image.setImageResource(R.drawable.applogo);
            }
        }
    }

    public void addItem(String item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<String> items) {
        for (String item : items) {
            addItem(item);
        }
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getItem(int position) {
        return items.get(position);
    }
}