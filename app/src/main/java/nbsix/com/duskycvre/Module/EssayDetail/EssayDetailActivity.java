package nbsix.com.duskycvre.Module.EssayDetail;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

import java.net.URL;

import butterknife.BindView;
import nbsix.com.duskycvre.Design.dialog.KeyMapDailog;
import nbsix.com.duskycvre.Design.test.shotView;
import nbsix.com.duskycvre.Module.Base.BaseActivity;
import nbsix.com.duskycvre.Network.X5Web.X5WebView;
import nbsix.com.duskycvre.R;

/**
 * Name: EssayDetailActivity
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2018-04-8 14:58
 */

public class EssayDetailActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.progressBar1)
    ProgressBar mPageLoadingProgressBar;

    @BindView(R.id.webview)
    X5WebView webview;

    KeyMapDailog dialog;
    private URL mIntentUrl;

    @Override
    public int getLayoutId() {
        return R.layout.activity_essay_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar.setTitle("文章详情");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources()
                .getDrawable(R.drawable.color_progressbar));

        //创建一个WebViewClient
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

        webview.setWebChromeClient(new WebChromeClient(){

        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    mPageLoadingProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    mPageLoadingProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mPageLoadingProgressBar.setProgress(newProgress);//设置进度值
                }

            }
        });

        WebSettings webSetting = webview.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);//如果是true，在x5加载失败启用系统内核时就会闪退，解决方法在下面finish覆写
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        long time = System.currentTimeMillis();
        if (mIntentUrl == null) {
            webview.loadUrl("http://www.baidu.com");
        } else {
            webview.loadUrl(mIntentUrl.toString());
        }
        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    public void editComment(){
        dialog = new KeyMapDailog("回复小明：", new KeyMapDailog.SendBackListener() {
            @Override
            public void sendBack(final String inputText) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.hideProgressdialog();
                        dialog.dismiss();
                    }
                }, 2000);
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    public void initToolBar() {

    }

    //进行销毁
    @Override
    protected void onDestroy() {
        if (webview != null)
            webview.destroy();
        super.onDestroy();
    }

    /**
     * 防止webview缩放时退出崩溃。
     */
    @Override
    public void finish() {
        ViewGroup view = (ViewGroup) getWindow().getDecorView();
        view.removeAllViews();
        super.finish();
    }

    //处理网页中back键返回逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webview != null && webview.canGoBack()) {
                webview.goBack();
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}

/**
 * public class MainActivity extends Activity {

 private LinearLayout container ;
 private LayoutInflater inflater ;
 private List < Comment > datas ;
 @Override
 protected void onCreate ( Bundle savedInstanceState ) {
 super.onCreate ( savedInstanceState );
 setContentView ( R.layout.activity_main );

 inflater = this.getLayoutInflater () ;
 container = ( LinearLayout ) findViewById ( R.id.container ) ;

 datas = new CommentData ( this ).getComments () ;
 for ( Comment cmt : datas ) {
 addComment ( cmt ) ;
 }

 Log.d ( "systemtime", DateFormatUtils.format ( new Date ( System.currentTimeMillis ()) ) ) ;
 }

 @Override
 public boolean onCreateOptionsMenu ( Menu menu ) {
 // Inflate the menu; this adds items to the action bar if it is present.
 getMenuInflater ().inflate ( R.menu.main, menu );
 return true;
 }

 private void addComment ( Comment cmt ) {
 ViewGroup floor = ( ViewGroup ) inflater.inflate ( R.layout.comment_list_item, null ) ;
 TextView floor_date = ( TextView ) floor.findViewById ( R.id.floor_date ) ;
 TextView floor_username = ( TextView ) floor.findViewById ( R.id.floor_username ) ;
 TextView floor_content = ( TextView ) floor.findViewById ( R.id.floor_content ) ;
 floor_date.setText ( DateFormatUtils.formatPretty ( cmt.getDate () ) ) ;
 floor_username.setText ( cmt.getUserName () ) ;
 floor_content.setText ( cmt.getContent () ) ;
 FloorView subFloors = ( FloorView ) floor.findViewById ( R.id.sub_floors ) ;
 if ( cmt.getParentId () != Comment.NULL_PARENT ) {

 SubComments cmts = new SubComments ( addSubFloors ( cmt.getParentId (), cmt.getFloorNum () - 1 ) ) ;
 subFloors.setComments ( cmts ) ;
 subFloors.setFactory ( new SubFloorFactory() ) ;
 subFloors.setBoundDrawer ( this.getResources ().getDrawable ( R.drawable.bound ) ) ;
 subFloors.init () ;
 } else {
 subFloors.setVisibility ( View.GONE ) ;
 }
 container.addView ( floor ) ;
 }

 private List < Comment > addSubFloors ( long parentId, int num ) {
 if ( num == 0 ) return null ;
 Comment[] cmts ;

            cmts = new Comment[ num ] ;
                    for ( Comment cmt : datas ) {
                    if ( cmt.getId () == parentId ) cmts[0] = cmt ;
                    if ( cmt.getParentId () == parentId && cmt.getFloorNum () <= num ) cmts[ cmt.getFloorNum () - 1 ] = cmt ;
                    }

                    ArrayList < Comment > list = new ArrayList < Comment > () ;
        for ( int i = 0 ; i < cmts.length ; i ++ ) {
        list.add ( cmts[i] ) ;
        }
        return list ;
        }


        }
 */
