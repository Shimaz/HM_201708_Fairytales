package kr.tangomike.hm_201708_fairytales;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // finish();

        }
    };

    private IntentFilter mFilter = new IntentFilter("shimaz.restart");

    private Button btnBook01;
    private Button btnBook02;
    private Button btnBook03;

    private Button btnInfo01;
    private Button btnInfo02;
    private Button btnInfo03;


    private DataCollection dc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        registerReceiver(mReceiver, mFilter);

        dc = (DataCollection)getApplicationContext();


        btnBook01 = (Button)findViewById(R.id.btn_book_1);
        btnBook02 = (Button)findViewById(R.id.btn_book_2);
        btnBook03 = (Button)findViewById(R.id.btn_book_3);

        btnBook01.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, PageActivity.class);
                intent.putExtra("bookNumber", 2);
                dc.click();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

            }

        });

        btnBook02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, PageActivity.class);
                intent.putExtra("bookNumber", 1);
                startActivity(intent);
                dc.click();
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);


            }
        });

        btnBook03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PageActivity.class);
                intent.putExtra("bookNumber", 3);
                dc.click();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
            }
        });

        btnInfo01 = (Button)findViewById(R.id.btn_info_1);
        btnInfo01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("bookNumber", 1);
                dc.click();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
            }
        });

        btnInfo02 = (Button)findViewById(R.id.btn_info_2);
        btnInfo02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("bookNumber", 2);
                dc.click();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);
            }
        });

        btnInfo03 = (Button)findViewById(R.id.btn_info_3);
        btnInfo03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("bookNumber", 3);
                dc.click();
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

            }
        });



    }

    @Override
    public void onResume(){
        super.onResume();
        dc.stopTick();
    }

    @Override
    public void onPause(){
        super.onPause();
        dc.startTick();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
