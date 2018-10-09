package nbsix.com.duskycvre.Module.HomePage;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nbsix.com.duskycvre.Adapter.BannerCoverFlowAdapter;
import nbsix.com.duskycvre.Adapter.FragmentAdapter;
import nbsix.com.duskycvre.Design.PopWindow.CommonPopupWindow;
import nbsix.com.duskycvre.Design.filterMenuLayout.FilterMenu;
import nbsix.com.duskycvre.Design.filterMenuLayout.FilterMenuLayout;
import nbsix.com.duskycvre.Design.imagecoverflow.CoverFlowView;
import nbsix.com.duskycvre.Entity.HomePage.BannerBean;
import nbsix.com.duskycvre.Entity.HomePage.BannerInfo;
import nbsix.com.duskycvre.Module.Base.BaseActivity;
import nbsix.com.duskycvre.Module.Search.SearchActivity;
import nbsix.com.duskycvre.Module.UserCenter.UserInfoActivity;
import nbsix.com.duskycvre.Network.RetrofitHelper;
import nbsix.com.duskycvre.Network.api.ApiConstants;
import nbsix.com.duskycvre.R;
import nbsix.com.duskycvre.Utils.ToastUtil;


public class HomePageActivity extends BaseActivity {
    List<BannerBean> banners=new ArrayList<>();
    List<Bitmap> bitmaps=new ArrayList<>();
    BannerCoverFlowAdapter adapter;
    private CommonPopupWindow minepopwindow;
    @BindView(R.id.coverflow)CoverFlowView<BannerCoverFlowAdapter> coverflow;
    @BindView(R.id.toolbar_tab)TabLayout tabs;
    @BindView(R.id.view_pager)ViewPager viewPager;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.toolbar_more)ImageView toolbar_more;
    @BindView(R.id.group_user) LinearLayout group_user;


    @OnClick(R.id.toolbar_more)
    public void toolbarmenu(ImageView toolbar_more) {
        ShowPopWindow(toolbar_more);
    }
    @OnClick(R.id.group_user)
    public void usercenter(){
        Intent it=new Intent(this, UserInfoActivity.class);
        startActivity(it);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public void loadData() {
        getBannerBitmap(banners);
        RetrofitHelper.getBannerAPI()
                .getHomePageRecommendedBannerInfo()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((BannerInfo bean) -> {
                    banners.addAll(bean.getAd());
                    getBannerBitmap(banners);
                }, throwable -> {
                    ToastUtil.ShortToast("数据错误");
                });

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initMenu();
        initPopWindow();
        initBanner();
        initListener();
        initTabAndViewPager();
    }

    private void initPopWindow() {
        if (minepopwindow != null && minepopwindow.isShowing())  return;
        minepopwindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.toolbar_pop_layout)
                .setWidthAndHeight(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(1.0f)
                .setViewOnclickListener((view, layoutResId) -> {
                    switch (layoutResId){
                        case R.layout.toolbar_pop_layout:
                            LinearLayout minelin = (LinearLayout) view.findViewById(R.id.pop_mine);
                            minelin.setOnClickListener(v -> {
                                Intent it=new Intent(HomePageActivity.this, SearchActivity.class);
                                startActivity(it);
                            });
                            break;

                    }
                })
                .create();
    }

    private void ShowPopWindow(View v){
        minepopwindow.showAsDropDown(v);
    }

    private void getBannerBitmap(List<BannerBean> banners){
        int size=3;
        for(int i=0;i<size;i++){
            Glide.with(this).asBitmap().load("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg").into(new SimpleTarget<Bitmap>(1000, 200) {
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    bitmaps.add(resource);
                    if(bitmaps.size()>=size){
                        //bitmaps.clear();
                        //bitmaps.addAll(bitmapsnew);
                        coverflow.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                }

            });

        }
    }
    private void initBanner(){
        Resources res=getResources();
        if(bitmaps.size()<3){
            int d=3-bitmaps.size();
            for (int i=0;i<d;i++){
                Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.bili_default_image_tv);
                bitmaps.add(bmp);
            }
        }
        adapter = new BannerCoverFlowAdapter();
        adapter.setBitmapList(bitmaps);
        coverflow.setAdapter(adapter);

    }
    //@BindView(R.id.filter_menu)FilterMenuLayout filter_menu;
    private void initMenu(){
        //attachMenu(filter_menu);

    }
    private FilterMenu attachMenu(FilterMenuLayout layout){
        return new FilterMenu.Builder(this)
                //.addItem(R.drawable.ic_action_location_2)
                //.addItem(R.drawable.ic_action_clock)
                //.addItem(R.drawable.ic_action_info)
                //.addItem(R.drawable.ic_action_io)
                //.addItem(R.drawable.ic_action_location_2)
                //.attach(layout)
                //.withListener(listener)
                .build();
    }

    private void initTabAndViewPager(){
        List<String> titles;
        String[] PLANETS = {"HOT","VISION","GAMES","SQUARE"};
        titles= Arrays.asList(PLANETS);
        int size=titles.size();
        List<Fragment> mFragments=new ArrayList<>();
        for(int i=0;i<size;i++){
            HomePageFragment homePageFragment=HomePageFragment.newInstance(titles.get(i));
            mFragments.add(homePageFragment);
        }
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(titles.size());
        tabs.setupWithViewPager(viewPager);
    }

    private void initListener(){
        coverflow.setCoverFlowListener(new CoverFlowView.CoverFlowListener<BannerCoverFlowAdapter>() {
            @Override
            public void imageOnTop(
                    CoverFlowView<BannerCoverFlowAdapter> view,
                    int position, float left, float top, float right,
                    float bottom) {
                Log.e("CV", position + "on top!");
            }

            @Override
            public void topImageClicked(CoverFlowView<BannerCoverFlowAdapter> view, int position) {
                Log.e("CV", position + "clicked!");
                //ToastUtil.ShortToast(banners.get(position).getMsg());
            }

            @Override
            public void invalidationCompleted() {

            }
        });

        coverflow.setTopImageLongClickListener(position -> Log.e("CV", "top image long clicked == >"+ position));
    }
    @Override
    public void initToolBar() {

    }
}
