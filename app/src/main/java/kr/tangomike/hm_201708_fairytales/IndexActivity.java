package kr.tangomike.hm_201708_fairytales;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class IndexActivity extends Activity {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");

    private int bookNumber;

    private int[] bookIndex;

    private LinearLayout llIndex;

    private HorizontalScrollView scrlIndex;

    private DataCollection dc;

    private RelativeLayout rlIndex;

    private Button btnHome;
    private Button btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        registerReceiver(mReceiver, mFilter);

        bookNumber = getIntent().getIntExtra("bookNumber", 1);

        llIndex = (LinearLayout)findViewById(R.id.ll_index);
        scrlIndex = (HorizontalScrollView)findViewById(R.id.scrl_index);

        rlIndex = (RelativeLayout)findViewById(R.id.rl_index);
        dc = (DataCollection)getApplicationContext();

        scrlIndex.setHorizontalScrollBarEnabled(false);

        rlIndex.setBackgroundResource(getResources().getIdentifier("list_bg_" + (bookNumber - 1), "drawable", getPackageName()));

        btnHome = (Button)findViewById(R.id.btn_list_home);
        btnInfo = (Button)findViewById(R.id.btn_list_info);



        InitUI();


    }


    private void InitUI(){

//        LayoutParams lp = new LayoutParams(200, 200);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


        if(bookNumber == 1){
            bookIndex = new int[] {177, 172, 166, 156, 148, 142, 134, 127, 123, 118, 113, 109, 105, 101, 95, 93, 85, 80, 73, 68, 60, 51, 45, 39, 37, 31, 27, 23, 17, 8};

        }else if(bookNumber == 2){
            bookIndex = new int[] {0, 10, 12, 14, 17, 20, 55, 78, 100};

        }else{
            bookIndex = new int[] {0, 10, 12, 14, 17, 20, 55, 78, 100};
        }



        for(int i = 0; i < bookIndex.length; i++){

            RelativeLayout rlBtn = new RelativeLayout(this);
            rlBtn.setLayoutParams(lp);

            ImageView ivBtn = new ImageView(this);
            ivBtn.setLayoutParams(lp);
            ivBtn.setImageResource(getResources().getIdentifier("list_scroll_" + (bookNumber - 1) + "_" + i, "drawable", getPackageName()));

            android.util.Log.i("shimaz", "list_scroll_" + (bookNumber - 1) + "_" + i);

            Button btn = new Button(this);
            btn.setLayoutParams(lp);
            btn.setTag(i);
            btn.setBackgroundResource(R.drawable.btn_list_bg);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dc.resetTimer();
                    Intent intent = new Intent(IndexActivity.this, PageActivity.class);

                    intent.putExtra("pageNumber", bookIndex[(int)view.getTag()]);
                    intent.putExtra("bookNumber", bookNumber);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

                }
            });
            btn.setPadding(20, 0, 20, 0);

//            btn.setText("" + bookIndex[i]);

            rlBtn.addView(ivBtn);
            rlBtn.addView(btn);

            llIndex.addView(rlBtn);


        }


        scrlIndex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dc.resetTimer();
                return false;
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.resetTimer();
                Intent intent = new Intent(IndexActivity.this, InfoActivity.class);
                intent.putExtra("bookNumber", bookNumber);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
            }
        });

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
