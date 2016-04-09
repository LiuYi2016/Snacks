package com.atguigu.snacks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.atguigu.snacks.base.BasePage;
import com.atguigu.snacks.page.HomePage;
import com.atguigu.snacks.page.MinePage;
import com.atguigu.snacks.page.SalePage;
import com.atguigu.snacks.page.SubjectPage;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页面
 */
public class MainActivity extends FragmentActivity {
    //主页面视图容器
    private FrameLayout fl_content_main;
    //底部控制栏
    private RadioGroup rg_bottom;
    //页面集合
    private List<BasePage> pageLists;
    //标记选择的下标
    private int position;
    private DrawerLayout dl_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initPage();
    }

    /**
     * 初始化页面
     */
    private void initPage() {
        pageLists = new ArrayList<>();
        pageLists.add(new HomePage(this));
        pageLists.add(new SalePage(this));
        pageLists.add(new SubjectPage(this));
        pageLists.add(new MinePage(this));

        //设置RadioGroup监听
        rg_bottom.setOnCheckedChangeListener(new MainOnCheckedChangeListener());
        //默认选择的一个
        rg_bottom.check(R.id.rb_main_home);
        //关闭滑动打开左侧菜单
        dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        fl_content_main = (FrameLayout) findViewById(R.id.fl_content_main);
        rg_bottom = (RadioGroup) findViewById(R.id.rg_bottom);
        dl_main = (DrawerLayout) findViewById(R.id.dl_main);
    }


    /**
     * 主页面底部RadioGroup点击事件
     */
    class MainOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                default:
                    position = 0;
                    break;
                case R.id.rb_main_sale:
                    position = 1;
                    break;
                case R.id.rb_main_subject:
                    position = 2;
                    break;
                case R.id.rb_main_mine:
                    position = 3;
                    break;
            }
            setFragment();
        }
    }

    /**
     * 设置Fragment
     */
    private void setFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_content_main, new Fragment() {
            @Nullable
            @Override
            public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                BasePage page = getBasePage();
                return page.rootView;
            }
        });

        ft.commit();
    }

    /**
     * 得到页面
     *
     * @return
     */
    private BasePage getBasePage() {

        BasePage page = pageLists.get(position);
        if (page != null) {
            page.initData();//初始化数据
        }
        return page;
    }

    /**
     * 左侧菜单开关
     *
     * @param flag true 开
     *             false 关
     */
    public void leftMenuToggle(boolean flag) {
        if (flag) {
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            dl_main.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }
}
