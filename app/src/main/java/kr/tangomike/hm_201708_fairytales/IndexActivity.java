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
import android.widget.LinearLayout;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        registerReceiver(mReceiver, mFilter);

        bookNumber = getIntent().getIntExtra("bookNumber", 1);

        llIndex = (LinearLayout)findViewById(R.id.ll_index);
        scrlIndex = (HorizontalScrollView)findViewById(R.id.scrl_index);

        dc = (DataCollection)getApplicationContext();

        InitUI();


    }


    private void InitUI(){

        LayoutParams lp = new LayoutParams(200, 200);


        if(bookNumber == 1){
            bookIndex = new int[] {0, 4, 5, 12, 29, 30, 31, 34, 55, 70};

        }else if(bookNumber == 2){
            bookIndex = new int[] {0, 10, 12, 14, 17, 20, 55, 78, 100};

        }else{
            bookIndex = new int[] {0, 10, 12, 14, 17, 20, 55, 78, 100};
        }



        for(int i = 0; i < bookIndex.length; i++){


            Button btn = new Button(this);
            btn.setLayoutParams(lp);
            btn.setTag(i);

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

            btn.setText("" + bookIndex[i]);

            llIndex.addView(btn);


        }


        scrlIndex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dc.resetTimer();
                return false;
            }
        });



    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
