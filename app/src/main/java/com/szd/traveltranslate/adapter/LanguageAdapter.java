package com.szd.traveltranslate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szd.traveltranslate.R;
import com.szd.traveltranslate.entity.LanguageInfoEntity;
import com.szd.traveltranslate.entity.LanguageInfoFactory;

/**
 * Created by shengzidong on 2017/7/4.
 */

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {
    Context context;
    String[] languages;
    LanguageViewHolder viewHolder;
    OnSelectedListener onSelectedListener;

    LanguageInfoFactory factory;


    public LanguageAdapter(Context context, String[] languages) {
        this.context = context;
        this.languages = languages;
        factory=new LanguageInfoFactory(context);
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_language_select,parent,false);
        viewHolder = new LanguageViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final LanguageViewHolder holder, final int position) {
        holder.cbCheck.setChecked(false);
        holder.tvLanguage.setText(languages[position]);
        LanguageInfoEntity entity=factory.getLanguageInfoByPosition(position);
        int flag=context.getResources().getIdentifier(entity.getFlag(),"drawable",context.getPackageName());
        holder.ivFlag.setImageResource(flag);

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectedListener.setOnSelectedListener(position);
                holder.cbCheck.setChecked(true);
            }
        });
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    @Override
    public int getItemCount() {
        return languages.length-1;
    }

    class LanguageViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFlag;
        TextView tvLanguage;
        CheckBox cbCheck;
        RelativeLayout rl;

        public LanguageViewHolder(View itemView) {
            super(itemView);
            rl = (RelativeLayout) itemView.findViewById(R.id.item_language_select);
            ivFlag = (ImageView) itemView.findViewById(R.id.iv_flag);
            tvLanguage = (TextView) itemView.findViewById(R.id.tv_language);
            cbCheck = (CheckBox) itemView.findViewById(R.id.cbLanguage);
        }
    }


    public interface OnSelectedListener {
        void setOnSelectedListener(int position);
    }
}
