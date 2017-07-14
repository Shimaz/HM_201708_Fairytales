package kr.tangomike.hm_201708_fairytales;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewGroupCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;


public class PageActivity extends Activity {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");
    private DataCollection dc;
    private ViewPager pager;
    private FairytalePagerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_page);
        registerReceiver(mReceiver, mFilter);



        adapter = new FairytalePagerAdapter();
        pager = findViewById(R.id.vp_page);
        pager.setAdapter(adapter);
        pager.setCurrentItem(0);

        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                dc.resetTimer();

            }
        };

        pager.addOnPageChangeListener(listener);

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }


    public class FairytalePagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position){
            View page = getLayoutInflater().inflate(R.layout.layout_page, container, false);
            ImageView iv = findViewById(R.id.iv_page);
            iv.setImageResource(getResources().getIdentifier("page_img" + position, "drawable", getPackageName()));
            container.addView(page);

            return page;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object){
            container.removeView((View)object);
        }

        @Override
        public float getPageWidth(int position){
            return 1.0f;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
