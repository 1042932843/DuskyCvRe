package nbsix.com.duskycvre.Design.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import nbsix.com.duskycvre.Design.starView.BackgroundView;

/**
 * Name: shotView
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-11-06 11:02
 */

public class shotView extends View {
    private int ScreenWidth,ScreenHight;

    private Context context;

    private DrawThread thread;

    private Player P1;

    public shotView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public shotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public shotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    public shotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        init();
    }

    private void init(){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        ScreenWidth=size.x;
        ScreenHight=size.y;
        initPlayer();
    }

    private void initPlayer(){
        P1=new Player();
        P1.x=ScreenWidth/2;
        P1.y=ScreenHight/2;
        P1.Size=50;
        P1.xa=0;
        P1.ya=10;
        P1.player=new Paint();
        P1.player.setAntiAlias(false);//无锯齿
        P1.player.setStyle(Paint.Style.STROKE);
        P1.player.setStrokeWidth(4);
        P1.player.setColor(Color.GREEN);
    }

    /**
     * 屏幕点对象
     * */
    private class Player{
        public float x;//x坐标
        public float y;//y坐标
        public float xa;//x增量
        public float ya;//y增量
        public int Size;//玩家大小
        public Paint player;//玩家图像点
        public boolean touch = false;//标示是否为触摸点

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        P1.y += P1.ya; //移动
        P1.ya *= P1.y > ScreenHight || P1.y < 0 ? -1 : 1; //碰到边界，反向反弹
        canvas.drawCircle(P1.x, P1.y, P1.Size,P1.player);
        startGame();
    }

    public void startGame(){
        thread= new shotView.DrawThread();//启动定时线程绘制
        thread.start();
    }
    /**
     * 定时通知绘制线程
     * */
    private class DrawThread extends Thread{

        @Override
        public void run() {
            super.run();
            try {
                sleep(1000 / 40);//每秒绘制40次
                mHandler.sendEmptyMessage(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**绘图通知handler*/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1) draw_canvas();
        }
    };

    /**
     * 重绘视图通知
     */
    private void draw_canvas() {
        this.invalidate();
    }
}
