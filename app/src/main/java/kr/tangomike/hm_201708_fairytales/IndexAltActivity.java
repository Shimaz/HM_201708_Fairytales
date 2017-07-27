package kr.tangomike.hm_201708_fairytales;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class IndexAltActivity extends Activity {

    private DataCollection dc;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");

    private int bookNumber;
    private int[] bookIndex;

    private Button btnClose;

    private LinearLayout llContent;
    private RelativeLayout rlBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_alt);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        dc = (DataCollection)getApplicationContext();
        registerReceiver(mReceiver, mFilter);
        bookNumber = getIntent().getIntExtra("bookNumber", 1);


        rlBG = (RelativeLayout)findViewById(R.id.rl_list_bg);
        rlBG.setBackgroundResource(getResources().getIdentifier("list_bg_" + bookNumber, "drawable", getPackageName()));

        llContent = (LinearLayout)findViewById(R.id.ll_index);

        initUI();

        btnClose = (Button)findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dc.click();
                dc.resetTimer();
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
            }
        });




    }

    private void initUI(){

        if(bookNumber == 0){
            bookIndex = new int[]{291,289,287,281,274,267,263,262,257,253,250,244,240,234,229,225,217,214,209,204,202,193,188,185,181,178,172,169,168,159,153,149,140,135,132,128,119,113,108,104,98,89,88,84,83,78,75,73,71,68,64,62,60,56,53,50,47,44,41,38,33,30,27,24,21,17,13,11,7};

        }else if(bookNumber == 1){
            bookIndex = new int[]{188,186,183,181,180,177,172,166,156,148,142,134,127,123,118,113,109,105,101,95,93,85,80,73,68,60,51,45,39,37,31,27,23,17,8};

        }else{
            bookIndex = new int[] {0, 10, 12, 14, 17, 20, 55, 78, 100};
        }

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        for(int i = 0; i < bookIndex.length; i++){

            RelativeLayout rlBtn = new RelativeLayout(this);
            rlBtn.setLayoutParams(lp);

            ImageView ivBtn = new ImageView(this);
            ivBtn.setLayoutParams(lp);
            ivBtn.setImageResource(getResources().getIdentifier("list_" +  bookNumber + "_scroll_btn_" + i, "drawable", getPackageName()));

            android.util.Log.i("shimaz", "list_scroll_" + bookNumber + "_" + i);

            Button btn = new Button(this);
            btn.setLayoutParams(lp);
            btn.setTag(i);
            btn.setBackgroundResource(R.drawable.btn_list_bg);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dc.resetTimer();

                    dc.click();
                    Intent intent = new Intent();
                    intent.putExtra("pageNumber", bookIndex[(int)view.getTag()]);
                    setResult(RESULT_OK, intent);

                    finish();

                    overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

                }
            });
            btn.setPadding(20, 0, 20, 0);

//            btn.setText("" + bookIndex[i]);

            rlBtn.addView(ivBtn);
            rlBtn.addView(btn);

            llContent.addView(rlBtn);

        }


    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
