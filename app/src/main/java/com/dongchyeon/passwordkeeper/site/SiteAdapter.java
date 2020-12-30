package com.dongchyeon.passwordkeeper.site;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.database.entity.Site;

import java.util.ArrayList;
import java.util.List;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> implements OnSiteItemClickListener {

    private List<Site> items = new ArrayList<>();

    OnSiteItemClickListener listener;

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<Site> getSites() { return items; }

    public void setOnItemClickListener(OnSiteItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SiteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SiteAdapter.ViewHolder viewHolder, int position) {
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

        public void onBind(Site item, int position) {
            title.setText(((Site)item).getTitle());
            icon.setImageResource(R.drawable.ic_baseline_web_24);
        }
    }

    public void setItems(List<Site> data) {
        items = data;
        notifyDataSetChanged();
    }

    public Site getItem(int position) {
        return items.get(position);
    }
}
