package nbsix.com.duskycvre.Design.tagView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import nbsix.com.duskycvre.Entity.HomePage.Tagbean;
import nbsix.com.duskycvre.R;

public class TagView extends HorizontalScrollView {
    private String TAG = "tagView";
    private List<Tagbean> tags;


    public TagView(Context context) {
        super(context);
        setHorizontalScrollBarEnabled(false);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
        Log.e(TAG, "onLayout------------------------------" + paramBoolean);
        if (paramBoolean) {
            final LinearLayoutCompat linearLayout = (LinearLayoutCompat) getChildAt(0);
            // getChildAt(postion) ViewGroup 里面的方法 用来获取指定位置的视图，由于下边的setTopic(List<Topic> list)方法中添加的控件是
            // 一个LinearLayoutCompat布局，所以这里获取的就是他了
            if(linearLayout!=null){
                int count = linearLayout.getChildCount();//计算一个LinearLayoutCompat布局中子控件的个数
                TextView localTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_tag_item, linearLayout, false);
                localTextView.setText("...");//定义一个文本内容为"..."的TextView,作为溢出内容填充
                int width = 0;//初始化linearLayout宽度
                for (int i = 0; i < count; i++) {//遍历所用TextView控件，并累加计算当前linearLayout宽度
                    width += linearLayout.getChildAt(i).getWidth();
                    if (width+localTextView.getWidth() > getWidth()) {//如果当前遍历的第i个TextView时候，linearLayout宽度大于父布局宽度
                        linearLayout.removeViews(i - 1, count - i + 1); // 将当前TextView以后的所有TextView移除
                        linearLayout.addView(localTextView);//添加文本内容为"..."的TextView到末尾
                        break;
                    }
                }
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public void setTAG(List<Tagbean> list) {
        removeAllViews();
        this.tags = list;
        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(getContext());
        linearLayoutCompat.setOrientation(LinearLayoutCompat.HORIZONTAL);
        Iterator iterator = tags.iterator();
        while (iterator.hasNext()) {
            final Tagbean localTopic = (Tagbean) iterator.next();
            TextView localTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.flow_tag_item, linearLayoutCompat, false);
            localTextView.setText(localTopic.getTag());
            localTextView.setOnClickListener(v -> Toast.makeText(getContext(), localTopic.getTag(), Toast.LENGTH_SHORT).show());
            linearLayoutCompat.addView(localTextView);
        }
        addView(linearLayoutCompat);
    }

}
