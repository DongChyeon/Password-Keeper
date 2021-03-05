package com.dongchyeon.passwordkeeper.item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.database.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements OnItemClickListener {
    private List<Item> items = new ArrayList<>();
    OnItemClickListener listener;

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder viewHolder, int position) {
        viewHolder.onBind(items.get(position), position);
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView icon;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.titleText);
            icon = view.findViewById(R.id.icon);

            view.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(ViewHolder.this, view1, position);
                }
            });
        }

        public void onBind(Item item, int position) {
            title.setText(((Item)item).getTitle());
            icon.setImageResource(R.drawable.ic_baseline_web_24);
        }
    }

    public void setItems(List<Item> data) {
        items = data;
        notifyDataSetChanged();
    }

    public Item getItem(int position) {
        return items.get(position);
    }
}
