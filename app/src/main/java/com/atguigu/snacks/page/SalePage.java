package com.atguigu.snacks.page;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.atguigu.snacks.base.BasePage;

/**
 * Created by LiuYi on 2016/4/8.
 * 热卖页面
 */
public class SalePage extends BasePage{
    public SalePage(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(mActivity);
        textView.setText("热卖");
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
