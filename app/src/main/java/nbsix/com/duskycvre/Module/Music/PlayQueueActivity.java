package nbsix.com.duskycvre.Module.Music;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import nbsix.com.duskycvre.R;

public class PlayQueueActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //设置高度 、宽度
        int dialogHeight = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, dialogHeight);
        //getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.media_controller_bg)));
        //位置设置为底部
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM ;
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setAttributes(params);
        setContentView(R.layout.activity_play_queue);
    }

}
