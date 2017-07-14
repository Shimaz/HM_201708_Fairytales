package kr.tangomike.hm_201708_fairytales;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(mReceiver, mFilter);

        btnBook01 = findViewById(R.id.btn_book_1);
        btnBook02 = findViewById(R.id.btn_book_2);

        btnBook01.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                intent.putExtra("bookNumber", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_short, R.anim.fade_out_short);

            }

        });

        btnBook02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, IndexActivity.class);
                intent.putExtra("bookNumber", 2);
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
