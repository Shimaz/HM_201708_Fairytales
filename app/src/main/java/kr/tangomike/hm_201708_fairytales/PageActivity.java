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
import android.support.v4.widget.TextViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


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

    private int pageNumber;
    private int bookNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_page);
        registerReceiver(mReceiver, mFilter);

        dc = (DataCollection)getApplicationContext();
        pageNumber = getIntent().getIntExtra("pageNumber", 0);
        bookNumber = getIntent().getIntExtra("bookNumber", 1);


        adapter = new FairytalePagerAdapter();
        pager = findViewById(R.id.vp_page);
        pager.setAdapter(adapter);


        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                android.util.Log.i("shimaz", "" + position + ":" + adapter.getCount());

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                dc.resetTimer();

            }
        };

        pager.addOnPageChangeListener(listener);

        pager.setCurrentItem(pageNumber);
        android.util.Log.i("shimaz", "" + pageNumber);

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
            ImageView iv = page.findViewById(R.id.iv_page);
//            iv.setImageResource(getResources().getIdentifier("page_img" + position, "drawable", getPackageName()));
            iv.setImageResource(getResources().getIdentifier("a0", "drawable", getPackageName()));
            TextView tv = page.findViewById(R.id.tv_debug);
            tv.setText(""+position);
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
            if(bookNumber == 1){
                return 169;
            }else{
                return 289;
            }

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }
}
