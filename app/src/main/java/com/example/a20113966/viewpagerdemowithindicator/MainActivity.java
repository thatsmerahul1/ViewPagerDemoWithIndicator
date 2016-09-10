package com.example.a20113966.viewpagerdemowithindicator;

import android.content.Context;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    LinearLayout llPagerTab;
    // we are going to use a handler to be able to run in our TimerTask
    private final Handler handler = new Handler();
    private ArrayList<String> imageArray = new ArrayList<String>();

    private int j = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i =0; i<4 ; i++)
            imageArray.add("http://cdn.wonderfulengineering.com/wp-content/uploads/2015/04/india-wallpaper-7.jpg");


        mCustomPagerAdapter = new CustomPagerAdapter(getApplicationContext());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        llPagerTab= (LinearLayout) findViewById(R.id.llPagerTab);
        mViewPager.setAdapter(mCustomPagerAdapter);
        refreshPageController();
        startViewPager();

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int postion)
            {


                for (int i = 0; i <= (mCustomPagerAdapter.getCount()-1); i++)
                {
                    ((ImageView)llPagerTab.getChildAt(i)).setImageResource(R.drawable.nonselecteditem_dot);
                }
                ((ImageView)llPagerTab.getChildAt(postion)).setImageResource(R.drawable.selecteditem_dot);

            }

            @Override
            public void onPageScrolled(int postion, float arg1, int arg2)
            {
            }

            @Override
            public void onPageScrollStateChanged(int postion)
            {


            }
        });


    }

    private void refreshPageController()
    {
        int pagerPosition = 0;
        llPagerTab.removeAllViews();
        for (int i = 0; i <= (mCustomPagerAdapter.getCount()-1); i++)
        {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final ImageView imgvPagerController = new ImageView(MainActivity.this);
            imgvPagerController.setPadding(7,8,7,8);

            imgvPagerController.setImageResource(R.drawable.nonselecteditem_dot);


            llPagerTab.addView(imgvPagerController);
        }

        pagerPosition = mViewPager.getCurrentItem();

        if(((ImageView)llPagerTab.getChildAt(pagerPosition)) != null)
            ((ImageView)llPagerTab.getChildAt(pagerPosition)).setImageResource(R.drawable.selecteditem_dot);

    }


    private class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return imageArray.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item,container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);


            if(imageArray!=null && position<imageArray.size()){
            /*Picasso.with(ViewPagerDemo.this).load(imageArray.get(position))
                    .into(imageView);*/
                imageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher));
            }

            container.addView(itemView);

            return itemView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    private void startViewPager() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                while (j <= mCustomPagerAdapter.getCount()) {
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(j, true);
                            if (j >= mCustomPagerAdapter.getCount())
                                j = 0;
                            else
                                j++;

                        }
                    });


                }
            }
        };
        new Thread(runnable).start();
    }
}
