package nbsix.com.duskycvre.Design.starView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.bilibili.magicasakura.widgets.Tintable;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: BackgroundView
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 参考canvas中绘图，地址：https://github.com/hustcc/canvas-nest.js
 * Date: 2017-05-06 12:06
 */

public class BackgroundView extends View implements Tintable,View.OnTouchListener,GestureDetector.OnGestureListener {

    private List<OnTouchListener> mlist = new ArrayList<>();//存储多次点击事件的响应

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    private int lineCount = 80;//屏幕出现的点数量
    private int CircleSize = 1;//点大小

    private Context mContext;
    private List<LineConfig> random_lines = new ArrayList<>();//屏幕出现的点集合

    private GestureDetector mGestureDetector = null;

    private LineConfig currentDown = new LineConfig(); //触摸点

    private int color_point = Color.argb(200, 0,191,255);//点的颜色
    private int color_line = Color.argb(60,95,158,160);//线的颜色

    private int ScreenWidth,ScreenHight;

    public BackgroundView(Context context) {
        super(context);

        mContext = context;
        init();
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        DisplayMetrics dm2 = getResources().getDisplayMetrics();
        ScreenWidth=dm2.widthPixels;
        ScreenHight=dm2.heightPixels;
        currentDown.max = 30000;//触摸点与其他点连线的最大距离

        /*添加手势监听*/
        mGestureDetector = new GestureDetector(mContext, this);
        this.setOnTouchListener(this);
        this.setLongClickable(true);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);

        int width = size.x;
        int height = size.y;

        /*初始化点集合*/
        for(int i=0; i < lineCount ; ++i){
            LineConfig l = new LineConfig();
            l.x = (float) (Math.random() * width);
            l.y = (float) (Math.random() * height);
            l.xa = (float) (Math.random() * 2 - 1);
            l.ya = (float) (Math.random() * 2 - 1);
            l.max = 15000;

            random_lines.add(l);
        }

        /*添加触摸点到集合*/
        random_lines.add(currentDown);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int canvas_width = size.x;
        int canvas_height = size.y;

        Paint paint_blue = new Paint();
        paint_blue.setStyle(Paint.Style.STROKE);
        paint_blue.setStrokeWidth(2);


        float d, x_dist, y_dist, dist;

        for(int i = 0; i < random_lines.size() ; i ++ ){
            LineConfig r = random_lines.get(i);
            r.x += r.xa;
            r.y += r.ya; //移动
            r.xa *= r.x > canvas_width || r.x < 0 ? -1 : 1;
            r.ya *= r.y > canvas_height || r.y < 0 ? -1 : 1; //碰到边界，反向反弹

            paint_blue.setColor(color_point);

            canvas.drawCircle(r.x, r.y, CircleSize, paint_blue);

            for(int j = i + 1; j < random_lines.size() ; j ++ ){
                LineConfig e = random_lines.get(j);
                x_dist = r.x - e.x; //x轴距离
                y_dist = r.y - e.y; //y轴距离
                dist = x_dist * x_dist + y_dist * y_dist; //总距离
                if(dist < e.max){
                    if(e.touch && dist >= e.max / 2){
                        r.x -= 0.02 * x_dist;
                        r.y -= 0.02 * y_dist; //靠近的时候加速
                    }

                    paint_blue.setColor(color_line);
                    canvas.drawLine(r.x,r.y,e.x,e.y,paint_blue);
                }
            }
        }

        new DrawThread().start();//启动定时线程绘制

    }

    /**
     * 重写触摸监听事件，
     * 将添加的触摸事件添加到集合中
     * 防止外部对此视图再次添加事件导致触摸点无效
     */
    @Override
    public void setOnTouchListener(OnTouchListener l) {
        mlist.add(l);
        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                for (int i = 0; i < mlist.size(); ++i) {
                    OnTouchListener ml = mlist.get(i);
                    if (ml.onTouch(v, event)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);//分发手势通知
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        currentDown.touch = true;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        currentDown.x= ScreenWidth/2;
        currentDown.y= ScreenHight/20*6;
        currentDown.touch = false;
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        currentDown.x = e2.getX();//记录触摸点坐标
        currentDown.y = e2.getY();//记录触摸点坐标
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void tint() {

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

    /**
     * 屏幕点对象
     * */
    private class LineConfig{
        public float x;//x左标
        public float y;//y左标
        public float xa;//x增量
        public float ya;//y增量
        public float max;//两点间最大距离，超过此距离不再绘制线段
        public boolean touch = false;//标示是否为触摸点

    }

}
