package com.atguigu.snacks.page;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.snacks.base.BasePage;

/**
 * Created by LiuYi on 2016/4/8.
 * 专题页面
 */
public class SubjectPage extends BasePage {
    public SubjectPage(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("专题");
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
