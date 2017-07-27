package kr.tangomike.hm_201708_fairytales;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

//    private int pageNumber;
    private int bookNumber;

    private float posX;
    private float posY;

    private boolean isMenuOn;

    private Button btnPrev;
    private Button btnNext;
    private Button btnBack;
    private Button btnIndex;

    private RelativeLayout llControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_page);
        registerReceiver(mReceiver, mFilter);

        dc = (DataCollection)getApplicationContext();
//        pageNumber = getIntent().getIntExtra("pageNumber", 0);
        bookNumber = getIntent().getIntExtra("bookNumber", 0);


        adapter = new FairytalePagerAdapter();
        pager = (ViewPager)findViewById(R.id.vp_page);
        pager.setAdapter(adapter);
        pager.setCurrentItem(adapter.getCount() - 1);



        isMenuOn = true;

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

        final float differ = 20.f;

        ViewPager.OnTouchListener otl = new ViewPager.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                dc.resetTimer();

                if(event.getAction() == MotionEvent.ACTION_DOWN && event.getActionIndex() == 0){
                    posX = event.getX();
                    posY = event.getY();

                    android.util.Log.i("shimaz", "" + posX + " " + posY);
                }

                if(event.getAction() == MotionEvent.ACTION_UP && event.getActionIndex() == 0){
                    if(posX - differ < event.getX() && posX + differ > event.getX()){
                        if(posY - differ < event.getY() && posY + differ > event.getY()){
                            android.util.Log.i("shimaz", "click!");

                            if(isMenuOn){
                                hideMenu();

                            }else{
                                showMenu();
                            }

                        }

                    }

                }




                return false;
            }
        };

        pager.setOnTouchListener(otl);





        btnNext = (Button)findViewById(R.id.btn_next);
        btnPrev = (Button)findViewById(R.id.btn_prev);
        btnBack = (Button)findViewById(R.id.btn_back);
        btnIndex = (Button)findViewById(R.id.btn_index);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.resetTimer();
                if(isMenuOn) {
                    dc.click();
                    if(pager.getCurrentItem() < adapter.getCount()) pager.setCurrentItem(pager.getCurrentItem() + 1 , true);
                }

            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.resetTimer();
                if(isMenuOn){
                    dc.click();
                    if(pager.getCurrentItem() > 0) pager.setCurrentItem(pager.getCurrentItem() - 1, true);
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.resetTimer();
                if(isMenuOn){
                    dc.click();
                    finish();
                    overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
                }

            }
        });


        btnIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.resetTimer();
                if(isMenuOn){
                    dc.click();
                    Intent intent = new Intent(PageActivity.this ,IndexAltActivity.class);
                    intent.putExtra("bookNumber", bookNumber);
                    startActivityForResult(intent, 0);

                    overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
                }

            }
        });

        llControl = (RelativeLayout)findViewById(R.id.ll_control);



    }


    private void showMenu(){
        isMenuOn = true;
        llControl.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in_short));

    }

    private void hideMenu(){
        isMenuOn = false;
        llControl.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out_short));

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        dc.resetTimer();

        if(requestCode == 0) {

            if(resultCode == RESULT_OK){
                int pn = data.getIntExtra("pageNumber" , 0);
                pager.setCurrentItem(pn, true);

            }
        }


    }

    public class FairytalePagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position){

            View page = getLayoutInflater().inflate(R.layout.layout_page, container, false);
            ImageView iv = (ImageView)page.findViewById(R.id.iv_page);
            iv.setImageResource(getResources().getIdentifier("book_" + bookNumber + "_" + position, "drawable", getPackageName()));
//            TextView tv = (TextView)page.findViewById(R.id.tv_debug);
//            tv.setText("" + position);



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
            if(bookNumber == 0){
                return 292;
            }else if(bookNumber == 1){
                return 189;
            }else{
                return 300;
            }

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
