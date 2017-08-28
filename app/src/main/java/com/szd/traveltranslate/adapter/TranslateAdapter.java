package com.szd.traveltranslate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.szd.traveltranslate.R;
import com.szd.traveltranslate.entity.LanguageInfoEntity;
import com.szd.traveltranslate.entity.TranslateTextEntity;

import java.util.List;

/**
 * Created by shengzidong on 2017/7/3.
 */

public class TranslateAdapter extends RecyclerView.Adapter<TranslateAdapter.MyViewHolder> {
    Context context;
    List<TranslateTextEntity> translateTexts;
    MyViewHolder myViewHolder;

    public TranslateAdapter(Context context, List<TranslateTextEntity> translateTexts) {
        this.context = context;
        this.translateTexts = translateTexts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_translated, parent, false);
        myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        LanguageInfoEntity from = translateTexts.get(position).getLanguageInfoFrom();
        LanguageInfoEntity to = translateTexts.get(position).getLanguageInfoTo();
        String textFrom = translateTexts.get(position).getTextFrom();
        String textTo = translateTexts.get(position).getTextTo();
        int flagLeft = context.getResources().getIdentifier(from.getFlag(), "drawable", context.getPackageName());
        int flagRight = context.getResources().getIdentifier(to.getFlag(), "drawable", context.getPackageName());

        holder.ivFlagLeft.setImageResource(flagLeft);
        holder.ivFlagRight.setImageResource(flagRight);
        holder.tvLeft.setText(textFrom);
        holder.tvRight.setText(textTo);
    }


    @Override
    public int getItemCount() {
        return translateTexts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFlagLeft;
        ImageView ivFlagRight;
        TextView tvLeft;
        TextView tvRight;

        public MyViewHolder(View itemView) {
            super(itemView);

            ivFlagLeft = (ImageView) itemView.findViewById(R.id.ivFlagLeft);
            ivFlagRight = (ImageView) itemView.findViewById(R.id.ivFlagRight);
            tvLeft = (TextView) itemView.findViewById(R.id.tvLeft);
            tvRight = (TextView) itemView.findViewById(R.id.tvRight);
        }
    }
}
