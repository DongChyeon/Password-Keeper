package com.dongchyeon.passwordkeeper.site;

import android.view.View;

import com.dongchyeon.passwordkeeper.site.SiteAdapter;

public interface OnSiteItemClickListener {
    public void onItemClick(SiteAdapter.ViewHolder holder, View view, int position);
}