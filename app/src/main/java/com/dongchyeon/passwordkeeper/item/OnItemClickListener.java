package com.dongchyeon.passwordkeeper.item;

import android.view.View;

public interface OnItemClickListener {
    public void onItemClick(ItemAdapter.ViewHolder holder, View view, int position);
}