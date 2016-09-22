package com.yalantis.guillotine.sample.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yalantis.guillotine.sample.R;

/**
 * Created by HamedooGram on 9/6/16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    TextView txtName, txtResult;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        txtName = (TextView) itemView.findViewById(R.id.list_title);
        txtResult = (TextView) itemView.findViewById(R.id.list_result);

    }
}