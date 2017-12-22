package com.example.g.music_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class MusicService extends Service {

    MediaPlayer mp;
    String s = "";
    private final String TAG = "ServiceSample";

    public MusicService() {
    }

    //メディアプレイヤーインスタンスの用意
    public void onCreate(){
        super.onCreate();
        mp = new MediaPlayer();
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;
    }
    @Override
    //インスタンスに音楽ファイルのセット
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        try{
            mp.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/raw/" + "music"));
        }catch (IOException e){
            Log.d(TAG, "BIOerror");
        }
        return mStub;
    }

    public void onDestroy(){
        super.onDestroy();
    }

    MService.Stub mStub = new MService.Stub() {
        @Override
        //playメソッド．音楽再生中は音楽を停止し，停止中は再生させる．
        public void play(){
            if (mp.isPlaying()==true){
                s = "Music Stop";
                mp.stop();
            }else{
                try{
                    s = "Music Start";
                    mp.prepare();
                    mp.start();
                }catch (IOException e){
                    Log.d(TAG, "Ioerror");
                }
            }
            Log.d(TAG, s);
        }

    };
}