package com.szd.traveltranslate.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.szd.traveltranslate.R;
import com.szd.traveltranslate.adapter.LanguageAdapter;

public class LanguageSelectActivity extends BaseAvtivity {

    RecyclerView rvLanguage;
    LanguageAdapter adapter;
    String[] languages ;

    Intent intent;//MainActivity传来的intent
    Intent languageSelected;//传回去的intent
    int select_flag;


    @Override
    protected void initViews(Bundle savedInstanceState) {
        rvLanguage = (RecyclerView) findViewById(R.id.rvLanguageSelect);
    }

    @Override
    public void init() {
        intent = getIntent();
        languages=intent.getStringArrayExtra("languages");
        languageSelected = new Intent();
        adapter = new LanguageAdapter(this, languages);
        adapter.setOnSelectedListener(new LanguageAdapter.OnSelectedListener() {
            @Override
            public void setOnSelectedListener(int position) {
                languageSelected.putExtra(MainActivity.LANGUAGE_SELECTED, languages[position]);
                setResult(0, languageSelected);
                finish();
            }
        });
        rvLanguage.setLayoutManager(new LinearLayoutManager(this));
        rvLanguage.setAdapter(adapter);
    }



    @Override
    public int getResLayoutId() {
        return R.layout.activity_language_select;
    }
}
