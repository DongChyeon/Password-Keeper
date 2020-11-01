package com.dongchyeon.passwordkeeper.card;

import android.view.View;

import com.dongchyeon.passwordkeeper.card.CardAdapter;

public interface OnCardItemClickListener {
    public void onItemClick(CardAdapter.ViewHolder holder, View view, int position);
}
