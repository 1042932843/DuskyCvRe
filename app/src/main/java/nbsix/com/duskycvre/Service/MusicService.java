package nbsix.com.duskycvre.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import nbsix.com.duskycvre.Design.ICanFly.ICanFly;
import nbsix.com.duskycvre.R;
import nbsix.com.duskycvre.Utils.LogUtil;

/**
 * Name: MusicService
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-08-21 17:36
 */

public class MusicService extends Service {

    private ICanFly iCanFly;
    private View musicView;
    private ImageButton btnPre, btnPla, btnNex;
    private TextView textPosition, textDuration;
    public TextView textDisplay;
    private SeekBar progress;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MusicBinder();
    }
    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initFloatWindow();
    }

    private void initFloatWindow() {
        LogUtil.i("Service初始化悬浮播放面板");
        musicView = LayoutInflater.from(this).inflate(R.layout.layout_fly_music, null);
        textDuration = ButterKnife.findById(musicView,R.id.player_duration);
        textPosition = ButterKnife.findById(musicView,R.id.player_progress);
        textDisplay = ButterKnife.findById(musicView,R.id.player_displayname);
        progress = (SeekBar) musicView.findViewById(R.id.player_seek);
        iCanFly = new ICanFly(this,musicView);
    }

    public void show(){
        iCanFly.show();
    }
    public void dismiss(){
        iCanFly.dismiss();
    }
}
