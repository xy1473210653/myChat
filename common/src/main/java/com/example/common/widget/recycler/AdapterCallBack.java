package com.example.common.widget.recycler;

import android.support.v7.widget.RecyclerView;

public interface AdapterCallBack<Data> {
    void update(Data data, RecyclerAdapter.ViewHolder<Data> holder);
}
