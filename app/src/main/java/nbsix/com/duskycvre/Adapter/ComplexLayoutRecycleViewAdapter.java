package nbsix.com.duskycvre.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import nbsix.com.duskycvre.App.DuskyApp;
import nbsix.com.duskycvre.Design.tagView.TagView;
import nbsix.com.duskycvre.Entity.HomePage.Tagbean;
import nbsix.com.duskycvre.Entity.HomePage.EssayBean;
import nbsix.com.duskycvre.Network.api.ApiConstants;
import nbsix.com.duskycvre.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Name: ComplexLayoutRecycleViewAdapter
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //复杂布局adapter
 * Date: 2017-07-07 18:03
 */
public class ComplexLayoutRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    List<EssayBean> essayBeen;
    public static final int TYPE_MONOPOLY = 0xff01;//一项全占
    public static final int TYPE_TWO = 0xff02;//两项全占
    //public static final int TYPE_THREE = 0xff03;//三项全占
    public static final int TYPE_BUTTON = 0xff11;//按钮布局

    public ComplexLayoutRecycleViewAdapter(Context context,List<EssayBean> essayBeen) {
        this.context = context;
        this.essayBeen = essayBeen;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_MONOPOLY:
                return new HolderTypeMONOPOLY(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_page_viewpager_common_item_type_monopoly, parent, false));
            case TYPE_TWO:
                return new HolderTypeTWO(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_page_viewpager_common_item_type_monopoly, parent, false));
            case TYPE_BUTTON:
                return new HolderTypeButton(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_page_viewpager_common_item_type_monopoly, parent, false));
            default:
                Log.d("error","viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

       if (holder instanceof HolderTypeMONOPOLY){
            bindTypeMONOPOLY((HolderTypeMONOPOLY) holder, position);
        }else if(holder instanceof HolderTypeTWO){
           bindTypeTWO((HolderTypeTWO) holder, position);
       }else if(holder instanceof HolderTypeButton){
           bindTypeButton((HolderTypeButton)holder,position);
       }
        //单独对应类型的设置事件
        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(position);
                }
            });
            holder. itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(essayBeen!=null){
            return essayBeen.size();
        }else{
            return 0;
        }

    }

    @Override
    public int getItemViewType(int position) {
        String style="0";
        int size=essayBeen.size();
        if(position<size){
            style=essayBeen.get(position).getLayoutstyle();
        }

        switch (style){
            case "1":
                return TYPE_MONOPOLY;
            case "2":
                return TYPE_TWO;
            case "button":
                return TYPE_TWO;
            default:
                return TYPE_MONOPOLY;
        }
    }

    public int getSpanSize(int pos) {
        int viewType = getItemViewType(pos);
        switch (viewType) {
            case TYPE_MONOPOLY:
                return 12;
            case TYPE_TWO:
                return 6;
        }
        return 12;
    }

    private class HolderTypeMONOPOLY extends RecyclerView.ViewHolder {
        TextView title,publisher,content;
        ImageView imageView,user_avatar;
        TagView tagView;
        private HolderTypeMONOPOLY(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            publisher=(TextView)itemView.findViewById(R.id.user_name);
            content=(TextView)itemView.findViewById(R.id.content);
            imageView=(ImageView)itemView.findViewById(R.id.img);
            user_avatar=(ImageView)itemView.findViewById(R.id.user_avatar);
            tagView=(TagView)itemView.findViewById(R.id.container);
        }
    }
    private void bindTypeMONOPOLY(HolderTypeMONOPOLY holder, final int position){
        holder.title.setText(essayBeen.get(position).getTitle());
        holder.publisher.setText(essayBeen.get(position).getPublisher());
        holder.content.setText(essayBeen.get(position).getContent());
        Glide.with(context).load("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg").transition(withCrossFade()).apply(DuskyApp.optionsRoundedCorners).into(holder.imageView);
        Glide.with(context).load("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg").transition(withCrossFade()).apply(DuskyApp.optionsRoundedCircle).into(holder.user_avatar);
        List<Tagbean> tagbeens=new ArrayList<>();
        for(int i=0;i<3;i++){
            Tagbean tagbean=new Tagbean();
            tagbean.setTag("DSY"+i);
            tagbeens.add(tagbean);
        }
        holder.tagView.setTAG(tagbeens);

    }

    private class HolderTypeTWO extends RecyclerView.ViewHolder {
        private HolderTypeTWO(View itemView) {
            super(itemView);

        }
    }
    private void bindTypeTWO(HolderTypeTWO holder, final int position){

    }

    private class HolderTypeButton extends RecyclerView.ViewHolder {
        private Button button;

        private HolderTypeButton(View itemView) {
            super(itemView);
           // button=(Button)itemView.findViewById(R.id.more);

        }
    }
    private void bindTypeButton(HolderTypeButton holder, int position){

    }

    OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick( int position);
        void onLongClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.onItemClickListener=onItemClickListener;
    }




}
