package com.szd.traveltranslate.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.szd.traveltranslate.R;


/**
 * Created by shengzidong on 2017/8/2.
 */

public class ProcessDialog extends Dialog {
    TextView tv;

    public ProcessDialog(@NonNull Context context) {
        super(context);
        this.setContentView(R.layout.widget_processdialog);
        tv = (TextView) this.findViewById(R.id.tvMessage);
    }

    public void setText(String text) {
        tv.setText(text);
    }

    public void setIsCancelOutSide(boolean isCancelOutSide) {
        this.setCanceledOnTouchOutside(isCancelOutSide);
    }


}
