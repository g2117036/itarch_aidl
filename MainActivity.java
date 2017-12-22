package com.example.g.music_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MService mService = null;
    private final String TAG = "AIDLSample";
    TextView mText;

    @Override
    //サービスの開始とテキストビューに文字のセット
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent(this, MusicService.class);
        bindService(i, serviceConnection, BIND_AUTO_CREATE);

        mText = (TextView) findViewById(R.id.mytext);
        mText.setText("MusicService");
    }

    //サービスの終了
    public void onDestroy(){
        super.onDestroy();
        unbindService(serviceConnection);
    }

    //サービスの開始と終了処理
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder ibinder) {
            mService = MService.Stub.asInterface(ibinder);
        }

        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    //ボタンを押したときの処理．押すたびに，MusicServiceのplayメソッドを呼び出す．
    public void onStartClick(View view) {
        try{
            mService.play();
            Toast.makeText(MainActivity.this, "ボタンが押されました", Toast.LENGTH_SHORT).show();
        }catch(RemoteException e){
            Log.d(TAG, "Re");
        }catch(NullPointerException e) {
            Log.d(TAG, "Nu");
        }
    }

}