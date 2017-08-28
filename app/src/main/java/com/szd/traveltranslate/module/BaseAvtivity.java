package com.szd.traveltranslate.module;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Toast;

import com.szd.traveltranslate.R;
import com.szd.traveltranslate.widget.ProcessDialog;

/**
 * Created by shengzidong on 2017/6/27.
 */

public abstract class BaseAvtivity extends AppCompatActivity {
    boolean isSetStatusBar = false;
    ProcessDialog processDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        setContentView(getResLayoutId());
        initViews(savedInstanceState);
        initToolbar();
        init();


        if (isSetStatusBar) {
            steepStatusBar();
        }
    }

    protected void init() {

    }


    //初始化toolbar
    protected  void initToolbar(){
        setSteepStatusBar(true);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        processDialog=new ProcessDialog(this);
    };

    //初始化控件
    protected abstract void initViews(Bundle savedInstanceState);

    //获取xml的id
    public abstract int getResLayoutId();



    public void showMyDialog(int text,boolean isCancelOutside) {
        processDialog.show();
        processDialog.setText(getResources().getString(text));
        processDialog.setIsCancelOutSide(isCancelOutside);
    }

    public void dismissDialog() {
        processDialog.dismiss();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length >= 1) {
                showToast("已获得录音权限");
            } else {
                showToast("权限获取失败，可能无法听到您说的话");
            }
        }
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    //设置是否沉浸状态栏
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }


    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.status_bar_color));
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
