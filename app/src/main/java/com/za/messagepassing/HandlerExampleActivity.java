package com.za.messagepassing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

//The HandlerExampleActivity simulates a long-running operation that is started when the user clicks a button.
//The long-running task is executed on a background thread;
//meanwhile, the UI displays a progress bar that is removed when the background thread reports the result back to the UI thread.

public class HandlerExampleActivity extends AppCompatActivity implements Handler.Callback, View.OnClickListener {
    public final static int SHOW_PROGRESS_BAR = 1;
    public final static int HIDE_PROGRESS_BAR = 0;
    private BackgroundThread mBackgroundThread;


    private TextView mTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);
        Handler mUiHandler = new Handler(this);

        mBackgroundThread = new BackgroundThread(mUiHandler);
        mBackgroundThread.start();

        Button mButton = (Button) findViewById(R.id.button);
        mProgressBar = findViewById(R.id.progressBar);
        mTextView = findViewById(R.id.textView);

        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mBackgroundThread.doWork();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mBackgroundThread.exit();
    }

    //The UI thread defines its own Handler that can receive commands to control the pro‐
    //gress bar and update the UI with results from the background thread:
    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_BAR:
                mProgressBar.setVisibility(View.VISIBLE);
                return true;
            case HIDE_PROGRESS_BAR:
                mTextView.setText(String.valueOf(msg.arg1));
                mProgressBar.setVisibility(View.INVISIBLE);
                return true;
        }
        return false;
    }

}
