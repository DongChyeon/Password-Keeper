package com.dongchyeon.passwordkeeper.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.SiteAdapter;
import com.dongchyeon.passwordkeeper.SiteViewActivity;
import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;

public class SiteTab extends Fragment {
    private AppDatabase appDatabase;
    private SiteDao siteDao;

    private RecyclerView siteRecyclerView;
    private SiteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.site_tab, container, false);

        appDatabase = AppDatabase.getInstance(getActivity());
        siteDao = appDatabase.siteDao();

        InitUI(rootView);

        return rootView;
    }

    private void InitUI(ViewGroup rootView) {
        siteRecyclerView = rootView.findViewById(R.id.site_recycler_view);

        siteRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SiteAdapter(appDatabase);
        siteRecyclerView.setAdapter(adapter);
        siteDao.getAll().observe(this, data -> adapter.setItems(data));

        adapter.setOnItemClickListener((holder, view, position) -> {
            Intent intent = new Intent(getActivity(), SiteViewActivity.class);

            intent.putExtra("eid", adapter.getItem(position).getEid());
            intent.putExtra("title", adapter.getItem(position).getTitle());
            intent.putExtra("id", adapter.getItem(position).getId());
            intent.putExtra("password", adapter.getItem(position).getPassword());
            intent.putExtra("url", adapter.getItem(position).getUrl());

            startActivity(intent);
        });
    }
}
