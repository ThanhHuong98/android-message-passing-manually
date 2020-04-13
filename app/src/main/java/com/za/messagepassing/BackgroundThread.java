package com.za.messagepassing;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

import java.util.Random;

import static com.za.messagepassing.HandlerExampleActivity.HIDE_PROGRESS_BAR;
import static com.za.messagepassing.HandlerExampleActivity.SHOW_PROGRESS_BAR;

public class BackgroundThread extends Thread{
    private Handler mBackgroundHandler;

    private  Handler mUiHandler;

    public BackgroundThread( Handler mUiHandler){
        this.mUiHandler = mUiHandler;
    }

//    Associate a Looper with the thread.
//    The Handler processes only Runnables. Hence, it is not required to implementn Handler.handleMessage.
    public void run() {
        Looper.prepare();
        mBackgroundHandler = new Handler();
        Looper.loop();
    }
 //    Post a long task to be executed in the background.
//    Create a Message object that contains only a what argument with a command— SHOW_PROGRESS_BAR—to the UI thread so that it can show the progress bar.
//    Send the start message to the UI thread.
//    Simulate a long task of random length, that produces some data randomInt.
//    Create a Message object with the result randomInt, that is passed in the arg1 parameter. The what parameter contains a command—HIDE_PROGRESS_BAR— to remove the progress bar.
//    The message with the end result that both informs the UI thread that the task is finished and delivers a result.
    public void doWork(){
        mBackgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                Message uiMsg = mUiHandler.obtainMessage(SHOW_PROGRESS_BAR, 0 , 0, null);
                mUiHandler.sendMessage(uiMsg);

                Random r = new Random();
                int randomInt = r.nextInt(5000);
                SystemClock.sleep(randomInt);

                uiMsg = mUiHandler.obtainMessage(HIDE_PROGRESS_BAR, randomInt, 0, null );
                mUiHandler.sendMessage(uiMsg);
            }
        });
    }

//    Quit the Looper so that the thread can finish.
    public void exit(){
        mBackgroundHandler.getLooper().quit();
       // mBackgroundHandler.getLooper().quitSafely();
    }

}
