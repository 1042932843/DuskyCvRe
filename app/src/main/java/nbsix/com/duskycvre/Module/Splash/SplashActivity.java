package nbsix.com.duskycvre.Module.Splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nbsix.com.duskycvre.Entity.Splash.UpdateInfo;
import nbsix.com.duskycvre.Entity.Splash.WheelMenuInfo;
import nbsix.com.duskycvre.Module.Base.BaseActivity;
import nbsix.com.duskycvre.Module.HomePage.HomePageActivity;
import nbsix.com.duskycvre.Design.wheelView.WheelView;
import nbsix.com.duskycvre.Module.LoginAndRegister.LoginActivity;
import nbsix.com.duskycvre.Module.Music.PlayQueueActivity;
import nbsix.com.duskycvre.Module.TestPlay.TestPlayActivity;
import nbsix.com.duskycvre.Network.RetrofitHelper;
import nbsix.com.duskycvre.Permission.PermissonManager;
import nbsix.com.duskycvre.R;
import nbsix.com.duskycvre.Service.MusicService;
import nbsix.com.duskycvre.Utils.DialogUtil;
import nbsix.com.duskycvre.Utils.LogUtil;
import nbsix.com.duskycvre.Utils.ToastUtil;

public class SplashActivity extends BaseActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();
    private MusicService musicService;
    private Intent serviceIntent;
    private ServiceConnection serviceConnection;
    private UpdateInfo updateInfo;

    @BindView(R.id.Magic)ImageView magic;
    @BindView(R.id.welcome)ImageView welcome;
    @BindView(R.id.splash_wv)WheelView splash_wv;
    @BindView(R.id.tips)TextView tips;



    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initPermission();
        inspectUpdate();
        initMagic();
        initWheelMenu();
        bindService();
        startService(serviceIntent);
    }

    private void bindService() {
        serviceIntent = new Intent(this, MusicService.class);
        if(serviceConnection == null) {
            serviceConnection = new ServiceConnection() {

                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    musicService = ((MusicService.MusicBinder)service).getService();
                    musicService.textDisplay.setOnClickListener(v -> {
                        Intent it=new Intent(getApplication(), PlayQueueActivity.class);
                        startActivity(it);
                    });
                }
                @Override
                public void onServiceDisconnected(ComponentName name) {
                }
            };
            bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
        }
    }

    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions.requestEach(
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.SYSTEM_ALERT_WINDOW

                       )
                .subscribe(permission -> {
                    if (permission.granted) {
                        // 用户已经同意该权限
                        Log.d(TAG, permission.name + " is granted.");
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Log.d(TAG, permission.name + " is denied. More info should be provided.");
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Log.d(TAG, permission.name + " is denied.");
                    }
                });
    }

    private void initWheelMenu() {
        List<WheelMenuInfo> menuInfos=new ArrayList<>();
        WheelMenuInfo menuInfo1=new WheelMenuInfo();
        menuInfo1.setTitle("肥宅事务所");
        menuInfo1.setTips("每周一到周日休息,周八上班");

        WheelMenuInfo menuInfo2=new WheelMenuInfo();
        menuInfo2.setTitle("新手上路");
        menuInfo2.setTips("你可以选择开车，也可以选择老司机带带我");

        WheelMenuInfo menuInfo3=new WheelMenuInfo();
        menuInfo3.setTitle("弹射起步");
        menuInfo3.setTips("快就一个字");

        WheelMenuInfo menuInfo4=new WheelMenuInfo();
        menuInfo4.setTitle("音乐模式");
        menuInfo4.setTips("开启新世界的大门♂");

        WheelMenuInfo menuInfo5=new WheelMenuInfo();
        menuInfo5.setTitle("烂电视");
        menuInfo5.setTips("爆米花是配不上可乐的，只有炸鸡才行");

        WheelMenuInfo menuInfo6=new WheelMenuInfo();
        menuInfo6.setTitle("宅验室");
        menuInfo6.setTips("相信科学,慎用膜法");

        menuInfos.add(menuInfo1);
        menuInfos.add(menuInfo2);
        menuInfos.add(menuInfo3);
        menuInfos.add(menuInfo4);
        menuInfos.add(menuInfo5);
        menuInfos.add(menuInfo6);

        splash_wv.setOffset(2);
        splash_wv.setItems(menuInfos);
        splash_wv.setSeletion(2);
        tips.setText(menuInfos.get(2).getTips());
        splash_wv.setHorizontalFadingEdgeEnabled(false);
        splash_wv.setYesOnclickListener((str) -> {
               LogUtil.i(str);
                gotoPage(str);
        });

        splash_wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(String item,String tip) {
                LogUtil.i(item);
                tips.setText(tip);
            }
        });
    }

    @Override
    public void initToolBar() {

    }

    private void initMagic(){
        Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        //Uri magicUri = Uri.parse("file:///android_asset/magic520.png");
        //Glide.with(this).load(magicUri).into(magic);
        //magic.setImageURI(magicUri);
        magic.setAlpha(0.8f);
        magic.startAnimation(operatingAnim);
    }



    private void gotoPage(String type){
        Intent intent;
        switch (type){
            case"肥宅事务所":
                break;
            case"新手上路":
                intent =new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case"弹射起步":
                intent =new Intent(SplashActivity.this, HomePageActivity.class);
                startActivity(intent);
                break;
            case"音乐模式":
                if(PermissonManager.getInstance().applyOrShowFloatWindow(this)){
                    if(musicService!=null){
                        musicService.show();
                    }
                }else{
                    ToastUtil.ShortToast("开请开启浮窗权限");
                }
                break;
            case"烂电视":
                break;
            case"宅验室":
                intent =new Intent(SplashActivity.this, TestPlayActivity.class);
                startActivity(intent);
                break;
            default:
                intent =new Intent(SplashActivity.this, HomePageActivity.class);
                startActivity(intent);
                //SplashActivity.this.finish();
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void inspectUpdate(){
        RetrofitHelper.getUpdateAPI()
                .getUpdateInfo()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bean -> {
                    updateInfo = bean;
                    if(updateInfo !=null){
                        showDialog();
                    }
                }, throwable -> {
                    ToastUtil.ShortToast("数据错误");
                });

    }

    private void showDialog(){
        String context= updateInfo.getChangelog();
        String url= updateInfo.getInstall_url();
        DialogUtil dialogUtil=new DialogUtil();
        dialogUtil.showDialogTheme(this,"版本更新", context, () -> {
            DownloadApk(url);
            dialogUtil.dismissDialog();
        }, dialogUtil::dismissDialog);
    }

    private void DownloadApk(String url){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ updateInfo.getName()+".apk";
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.d("pending");
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        LogUtil.d("connected");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.d("soFarBytes"+soFarBytes+"totalBytes"+totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        File file = new File(path);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        // 由于没有在Activity环境下启动Activity,设置下面的标签
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if(Build.VERSION.SDK_INT>=24) { //判读版本是否在7.0以上
                            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                            String a="nbsix.com.duskycvre.provider";
                            File file2=new File(path);
                            Uri apkUri = FileProvider.getUriForFile(SplashActivity.this,a , file2);
                            //添加这一句表示对目标应用临时授权该Uri所代表的文件
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                        }else{
                            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        }
                         startActivity(intent);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //放在super.onDestroy();前，因为baseActivity会解绑ButterKnife导致view为空。
        splash_wv.releaseYoYo();
        super.onDestroy();
    }


    private long exitTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
