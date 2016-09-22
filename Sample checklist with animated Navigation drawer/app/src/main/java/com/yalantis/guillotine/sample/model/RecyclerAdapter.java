package com.yalantis.guillotine.sample.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.survey.library.appprocess.process.Survey;
import com.yalantis.guillotine.sample.R;
import com.yalantis.guillotine.sample.util.GenericValues;

/**
 * Created by HamedooGram on 9/6/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    Context context;
    LayoutInflater inflater;

    public RecyclerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.cardview, parent, false);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Survey survey = GenericValues.searchResult.get(position);
        holder.txtName.setText(survey.getName());
        holder.txtResult.setText("Has Todd’s Syndrome by " + survey.getResult() + "%");
    }

    @Override
    public int getItemCount() {
        return GenericValues.searchResult.size();
    }
}
