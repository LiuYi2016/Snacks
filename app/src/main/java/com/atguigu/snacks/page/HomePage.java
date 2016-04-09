package com.atguigu.snacks.page;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.snacks.base.BasePage;

/**
 * Created by LiuYi on 2016/4/8.
 * 主页面
 */
public class HomePage extends BasePage {
    public HomePage(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("首页");
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
