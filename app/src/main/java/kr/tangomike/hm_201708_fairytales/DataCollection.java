package kr.tangomike.hm_201708_fairytales;

import android.app.Application;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

/**
 * Created by shimaz on 2017-07-10.
 */

public class DataCollection extends Application {
    private int ticktime;
    private static final int RESET_TIME = 60;
    private boolean isTicking = false;
    private Handler mHandler;

    private MediaPlayer click01;

    public void onCreate(){
        super.onCreate();
        ticktime = 0;
        isTicking = false;

        // TODO : broadcast timer message

        mHandler = new Handler(){
            public void handleMessage(Message msg){

                if(isTicking) ticktime++;

                if(ticktime < RESET_TIME){
                    mHandler.sendEmptyMessageDelayed(0, 1000);
                    android.util.Log.i("shimaz", "" + ticktime);
                }else if(ticktime >= RESET_TIME){
                    ticktime = 0;
                    isTicking = false;
                    mHandler.removeMessages(0);

                    Intent intent  = new Intent("shimaz.restart");
                    sendBroadcast(intent);
                }

            }

        };

        click01 = MediaPlayer.create(this, R.raw.click01);

    }

    public void resetTimer(){
        ticktime = 0;
    }

    public void stopTick(){
        isTicking = false;
        mHandler.removeMessages(0);
    }

    public void startTick(){
        ticktime = 0;
        isTicking = true;
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    public void click(){
        click01.start();
    }

}
