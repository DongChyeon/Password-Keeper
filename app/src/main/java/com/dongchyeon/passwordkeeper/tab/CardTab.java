package com.dongchyeon.passwordkeeper.tab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.CardViewActivity;
import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.CardAdapter;
import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.CardDao;

public class CardTab extends Fragment {
    private AppDatabase appDatabase;
    private CardDao cardDao;

    private RecyclerView cardRecyclerView;
    private CardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.card_tab, container, false);

        appDatabase = AppDatabase.getInstance(getActivity());
        cardDao = appDatabase.cardDao();

        InitUI(rootView);

        return rootView;
    }

    private void InitUI(ViewGroup rootView) {
        cardRecyclerView = rootView.findViewById(R.id.card_recycler_view);

        cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CardAdapter(appDatabase);
        cardRecyclerView.setAdapter(adapter);
        cardDao.getAll().observe(this, data -> adapter.setItems(data));

        adapter.setOnItemClickListener((holder, view, position) -> {
            Intent intent = new Intent(getActivity(), CardViewActivity.class);

            intent.putExtra("eid", adapter.getItem(position).getEid());
            intent.putExtra("title", adapter.getItem(position).getTitle());
            intent.putExtra("id", adapter.getItem(position).getId());
            intent.putExtra("password", adapter.getItem(position).getPassword());
            intent.putExtra("message", adapter.getItem(position).getMessage());
            intent.putExtra("pin", adapter.getItem(position).getPin());
            intent.putExtra("company", adapter.getItem(position).getCompany());

            startActivity(intent);
        });
    }
}
