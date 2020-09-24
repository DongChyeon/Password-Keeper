package com.dongchyeon.passwordkeeper;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.entity.Card;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> implements OnCardItemClickListener {

    private List<Card> items = new ArrayList<>();
    private Context context;
    private AppDatabase db;

    OnCardItemClickListener listener;

    public CardAdapter(AppDatabase db) {
        this.db = db;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Card> getCards() { return items; }

    public void setOnItemClickListener(OnCardItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        context = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder viewHolder, int position) {
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
            title = view.findViewById(R.id.title_text);
            icon = view.findViewById(R.id.icon);

            view.setOnClickListener(view1 -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(ViewHolder.this, view1, position);
                }
            });
        }

        public void onBind(Card item, int position) {
            title.setText(((Card)item).getTitle());
            icon.setImageResource(R.drawable.ic_baseline_card_24);
        }
    }

    public void setItems(List<Card> data) {
        items = data;
        notifyDataSetChanged();
    }

    public Card getItem(int position) {
        return items.get(position);
    }
}
