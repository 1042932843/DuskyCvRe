/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package nbsix.com.duskycvre.Design.ICanFly;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Name: ICanFly
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //我的悬浮窗，时尚最时尚。
 * Date: 2017-08-18 15:59
 */
public class ICanFly {
    private static final String TAG = "AVCallFloatView";
    View floatView;
    boolean isShow;
    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    private Context context;

    private boolean isAnchoring = false;
    private WindowManager windowManager = null;
    private WindowManager.LayoutParams mParams = null;


    public ICanFly(Context context, View PlayerView) {
        this.context=context;
        if(PlayerView!=null){
            initParams(context);
            setPlayerView(PlayerView);
        }
    }


    /**
     * 设置布局视图
     * */
    public void setPlayerView(View PlayerView) {
        if (PlayerView != null) {
            floatView = PlayerView;
            floatView.setOnTouchListener((v, event) -> {
                if (isAnchoring) {
                    return true;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xInView = event.getX();
                        yInView = event.getY();
                        xDownInScreen = event.getRawX();
                        yDownInScreen = event.getRawY();
                        xInScreen = event.getRawX();
                        yInScreen = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        xInScreen = event.getRawX();
                        yInScreen = event.getRawY();
                        // 手指移动的时候更新小悬浮窗的位置
                        updateViewPosition();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(xDownInScreen - xInScreen) <= ViewConfiguration.get(context).getScaledTouchSlop()
                                && Math.abs(yDownInScreen - yInScreen) <= ViewConfiguration.get(context).getScaledTouchSlop()) {
                            // 点击效果
                            Toast.makeText(context, "this float window is clicked", Toast.LENGTH_SHORT).show();
                        } else {

                        }
                        break;
                    default:
                        break;
                }
                return true;
            });
        }
    }
    public void initParams(Context context) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;

        mParams = new WindowManager.LayoutParams();
        mParams.packageName = context.getPackageName();
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        /*
         type 的取值：
      应用程序窗口。
      public static final int FIRST_APPLICATION_WINDOW = 1;

      所有程序窗口的“基地”窗口，其他应用程序窗口都显示在它上面。
      public static final int TYPE_BASE_APPLICATION   =1;

      普通应用功能程序窗口。token必须设置为Activity的token，以指出该窗口属谁。
      public static final int TYPE_APPLICATION       = 2;

       用于应用程序启动时所显示的窗口。应用本身不要使用这种类型。
      它用于让系统显示些信息，直到应用程序可以开启自己的窗口。
      public static final int TYPE_APPLICATION_STARTING = 3;

      应用程序窗口结束。
      public static final int LAST_APPLICATION_WINDOW = 99;

      子窗口。子窗口的Z序和坐标空间都依赖于他们的宿主窗口。
      public static final int FIRST_SUB_WINDOW       = 1000;

      面板窗口，显示于宿主窗口上层。
      public static final int TYPE_APPLICATION_PANEL  = FIRST_SUB_WINDOW;

      媒体窗口，例如视频。显示于宿主窗口下层。
      public static final int TYPE_APPLICATION_MEDIA  = FIRST_SUB_WINDOW+1;

      应用程序窗口的子面板。显示于所有面板窗口的上层。（GUI的一般规律，越“子”越靠上）
      public static final int TYPE_APPLICATION_SUB_PANEL = FIRST_SUB_WINDOW +2;

      对话框。类似于面板窗口，绘制类似于顶层窗口，而不是宿主的子窗口。
      public static final int TYPE_APPLICATION_ATTACHED_DIALOG = FIRST_SUB_WINDOW +3;

      媒体信息。显示在媒体层和程序窗口之间，需要实现透明（半透明）效果。（例如显示字幕）
      public static final int TYPE_APPLICATION_MEDIA_OVERLAY  = FIRST_SUB_WINDOW +4;

      子窗口结束。（ End of types of sub-windows ）
      public static final int LAST_SUB_WINDOW        = 1999;

      系统窗口。非应用程序创建。
      public static final int FIRST_SYSTEM_WINDOW    = 2000;

      状态栏。只能有一个状态栏；它位于屏幕顶端，其他窗口都位于它下方。
      public static final int TYPE_STATUS_BAR        =  FIRST_SYSTEM_WINDOW;

      搜索栏。只能有一个搜索栏；它位于屏幕上方。
      public static final int TYPE_SEARCH_BAR        = FIRST_SYSTEM_WINDOW+1;

      电话窗口。它用于电话交互（特别是呼入）。它置于所有应用程序之上，状态栏之下。
      public static final int TYPE_PHONE            = FIRST_SYSTEM_WINDOW+2;

      系统提示。它总是出现在应用程序窗口之上。
      public static final int TYPE_SYSTEM_ALERT      =  FIRST_SYSTEM_WINDOW +3;

      锁屏窗口。
      public static final int TYPE_KEYGUARD          = FIRST_SYSTEM_WINDOW +4;

      信息窗口。用于显示toast。
      public static final int TYPE_TOAST            = FIRST_SYSTEM_WINDOW +5;

      系统顶层窗口。显示在其他一切内容之上。此窗口不能获得输入焦点，否则影响锁屏。
      public static final int TYPE_SYSTEM_OVERLAY    =  FIRST_SYSTEM_WINDOW +6;

      电话优先，当锁屏时显示。此窗口不能获得输入焦点，否则影响锁屏。
      public static final int TYPE_PRIORITY_PHONE    =  FIRST_SYSTEM_WINDOW +7;

      系统对话框。（例如音量调节框）。
      public static final int TYPE_SYSTEM_DIALOG     =  FIRST_SYSTEM_WINDOW +8;

      锁屏时显示的对话框。
      public static final int TYPE_KEYGUARD_DIALOG   =  FIRST_SYSTEM_WINDOW +9;

      系统内部错误提示，显示于所有内容之上。
      public static final int TYPE_SYSTEM_ERROR      =  FIRST_SYSTEM_WINDOW +10;

      内部输入法窗口，显示于普通UI之上。应用程序可重新布局以免被此窗口覆盖。
      public static final int TYPE_INPUT_METHOD      =  FIRST_SYSTEM_WINDOW +11;

      内部输入法对话框，显示于当前输入法窗口之上。
      public static final int TYPE_INPUT_METHOD_DIALOG= FIRST_SYSTEM_WINDOW +12;

      墙纸窗口。
      public static final int TYPE_WALLPAPER         = FIRST_SYSTEM_WINDOW +13;

      状态栏的滑动面板。
      public static final int TYPE_STATUS_BAR_PANEL   = FIRST_SYSTEM_WINDOW +14;

      系统窗口结束。
      public static final int LAST_SYSTEM_WINDOW     = 2999;
         */
        mParams.format = PixelFormat.RGBA_8888;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = screenWidth;
        mParams.y = screenHeight;
    }

    public void show(){
        if (windowManager != null && floatView != null&&!isShow) {
            windowManager.addView(floatView, mParams);
            isShow=true;
        }else{
            dismiss();
        }

    }

    public void dismiss(){
        if (windowManager != null && floatView != null&&isShow) {
            windowManager.removeViewImmediate(floatView);
            isShow=false;
        }
    }

    private void updateViewPosition() {
        //增加移动误差
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        Log.e(TAG, "x  " + mParams.x + "   y  " + mParams.y);
        windowManager.updateViewLayout(floatView, mParams);
    }
}
