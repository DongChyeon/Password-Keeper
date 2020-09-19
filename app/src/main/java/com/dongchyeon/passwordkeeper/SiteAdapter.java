package com.dongchyeon.passwordkeeper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.entity.Site;

import java.util.ArrayList;
import java.util.List;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {

    private List<Site> items = new ArrayList<>();
    private Context mContext;
    private AppDatabase db;

    public SiteAdapter(AppDatabase db) {
        this.db = db;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Site> getItems() { return items; }

    @NonNull
    @Override
    public SiteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        mContext = viewGroup.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SiteAdapter.ViewHolder viewHolder, int position) {
        viewHolder.onBind(items.get(position), position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title_text);
        }

        public void onBind(Site item, int position){
            title.setText(item.getTitle());
        }
    }

    public void setItems(List<Site> data) {
        items = data;
        notifyDataSetChanged();
    }
}
