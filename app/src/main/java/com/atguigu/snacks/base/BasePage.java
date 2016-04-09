package com.atguigu.snacks.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by LiuYi on 2016/4/8.
 */
public abstract class BasePage {
    //上下文
    public Activity mActivity;
    //孩子视图
    public View rootView;

    public BasePage(Activity mActivity) {
        this.mActivity = mActivity;
        rootView = initView();
    }

    /**
     * 初始化视图, 强制孩子实现
     *
     * @return
     */
    public abstract View initView();

    /**
     * 初始化数据, 孩子可实现可不实现
     */
    public void initData() {

    }
}
