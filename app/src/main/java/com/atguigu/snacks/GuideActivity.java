package com.atguigu.snacks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.atguigu.snacks.utils.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {

    private static final String TAG = GuideActivity.class.getSimpleName();
    //显示图片的ViewPager
    private ViewPager vp_guide;
    //显示指示原点的容器
    private LinearLayout ll_guide_points;
    //红色圆点
    private ImageView iv_guide_red_point;
    private RelativeLayout rl_guide_bottom;
    //引导页要展示的图片
    private int[] imageIds = {R.drawable.bg_guide_1, R.drawable.bg_guide_2,
            R.drawable.bg_guide_3, R.drawable.bg_guide_4, R.drawable.bg_guide_5};
    //引导页展示的图片集合
    private List<ImageView> imageViewList;
    //两个白色圆点间的距离
    private int pointDistance;
    private EdgeEffectCompat leftEdge;
    private EdgeEffectCompat rightEdge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setAdapterAndListener();
    }

    /**
     * 设置适配器和监听
     */
    private void setAdapterAndListener() {
        vp_guide.setAdapter(new GuidePageAdapter());
        vp_guide.addOnPageChangeListener(new GuideOnPageChangeListener());
    }

    /**
     * 初始化数据
     */
    private void initData() {
        imageViewList = new ArrayList<>();
        //初始化List
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            imageViewList.add(imageView);
        }
        //添加白色点
        addPoint();
        //得到白色点之间的距离
        iv_guide_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //只让该方法执行一次
                iv_guide_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                pointDistance = ll_guide_points.getChildAt(1).getLeft() - ll_guide_points.getChildAt(0).getLeft();
            }
        });


    }

    /**
     * 添加白色小圆点
     */
    private void addPoint() {
        //初始化小圆点
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.shape_white_point);
            //不是第一个的时候设置左边距
            if (i != 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(this, 10), DensityUtil.dip2px(this, 10));
                params.leftMargin = DensityUtil.dip2px(this, 10);
                imageView.setLayoutParams(params);
            }
            ll_guide_points.addView(imageView);
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        setContentView(R.layout.activity_guide);
        vp_guide = (ViewPager) findViewById(R.id.vp_guide);
        ll_guide_points = (LinearLayout) findViewById(R.id.ll_guide_points);
        iv_guide_red_point = (ImageView) findViewById(R.id.iv_guide_red_point);
        rl_guide_bottom = (RelativeLayout) findViewById(R.id.rl_guide_bottom);
        try {
            Field leftEdgeField = vp_guide.getClass().getDeclaredField("mLeftEdge");
            Field rightEdgeField = vp_guide.getClass().getDeclaredField("mRightEdge");
            if (leftEdgeField != null && rightEdgeField != null) {
                leftEdgeField.setAccessible(true);
                rightEdgeField.setAccessible(true);
                leftEdge = (EdgeEffectCompat) leftEdgeField.get(vp_guide);
                rightEdge = (EdgeEffectCompat) rightEdgeField.get(vp_guide);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float startX = 0;
        float endX = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "ACTION_DOWN");
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                endX = event.getX();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 引导页ViewPager的监听
     */
    class GuidePageAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViewList.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    class GuideOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //移动的距离
            float leftMargin = position * pointDistance + positionOffset * pointDistance;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DensityUtil.dip2px(GuideActivity.this, 10), DensityUtil.dip2px(GuideActivity.this, 10));
            params.leftMargin = (int) leftMargin;
            iv_guide_red_point.setLayoutParams(params);


        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(rightEdge!=null&&!rightEdge.isFinished()){//到了最后一张并且还继续拖动，出现蓝色限制边条了
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                GuideActivity.this.finish();
            }
        }
    }
}
