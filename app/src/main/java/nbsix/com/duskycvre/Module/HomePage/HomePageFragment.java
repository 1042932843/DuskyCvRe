package nbsix.com.duskycvre.Module.HomePage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import nbsix.com.duskycvre.Adapter.ComplexLayoutRecycleViewAdapter;
import nbsix.com.duskycvre.Adapter.helper.EndlessRecyclerOnScrollListener;
import nbsix.com.duskycvre.Adapter.helper.HeaderViewRecyclerAdapter;
import nbsix.com.duskycvre.Adapter.helper.SpacesItemDecoration;
import nbsix.com.duskycvre.Entity.HomePage.EssayBean;
import nbsix.com.duskycvre.Entity.HomePage.EssayTag;
import nbsix.com.duskycvre.Module.Base.BaseFragment;
import nbsix.com.duskycvre.Module.EssayDetail.EssayDetailActivity;
import nbsix.com.duskycvre.R;
import nbsix.com.duskycvre.Utils.LogUtil;
import nbsix.com.duskycvre.Utils.ToastUtil;

/**
 * Name: HomePageFragment
 * Author: Dusky
 * QQ: 1042932843
 * Comment: 首页通用页面recyclerView.smoothScrollToPosition(0);
 * Date: 2017-05-04 12:00
 */
public class HomePageFragment extends BaseFragment {
  private String type;
  private int pageNum = 1;
  private int pageSize = 10;
  private boolean mIsRefreshing = false;
  private List<EssayBean> essayBeens=new ArrayList<>();
  private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
  private ComplexLayoutRecycleViewAdapter complexLayoutRecycleViewAdapter;
  private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
  @BindView(R.id.common_recyclerview)RecyclerView recyclerView;
  @BindView(R.id.swipe_refresh_layout)SwipeRefreshLayout mSwipeRefreshLayout;
  public static HomePageFragment newInstance(String type) {
    HomePageFragment fragment=  new HomePageFragment();
    fragment.type=type;
    return fragment;
  }


  @Override
  public int getLayoutResId() {
    return R.layout.activity_home_page_viewpager_common;
  }


  @Override
  public void finishCreateView(Bundle state) {
    isPrepared = true;
    lazyLoad();
  }

  @Override
  protected void lazyLoad() {
    if (!isPrepared || !isVisible) {
      return;
    }
    initRefreshLayout();
    initRecyclerView();
    isPrepared = false;
  }
  @Override
  public void initRefreshLayout() {
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

    mSwipeRefreshLayout.post(() -> {
      mSwipeRefreshLayout.setRefreshing(true);
      loadData();
    });
    mSwipeRefreshLayout.setOnRefreshListener(() -> {
      pageNum = 1;
      mIsRefreshing = true;
      essayBeens.clear();
      mEndlessRecyclerOnScrollListener.refresh();
      loadData();
    });
  }

  @Override
  protected void initRecyclerView() {
     //去掉recyclerView动画处理闪屏
    ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

    complexLayoutRecycleViewAdapter=new ComplexLayoutRecycleViewAdapter(getContext(),essayBeens);
    mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(complexLayoutRecycleViewAdapter);
    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 12);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
        return complexLayoutRecycleViewAdapter.getSpanSize(position);
      }
    });
    recyclerView.addItemDecoration(new SpacesItemDecoration(24));
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(mHeaderViewRecyclerAdapter);
    //createHeadView();
    createLoadMoreView();
    mEndlessRecyclerOnScrollListener =new EndlessRecyclerOnScrollListener(layoutManager) {
      @SuppressLint("CheckResult")
      @Override
      public void onLoadMore(int currentPage) {
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .compose(HomePageFragment.this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                  pageNum++;
                  loadData();
                  loadMoreView.setVisibility(View.VISIBLE);
                });
      }
    };
    recyclerView.addOnScrollListener(mEndlessRecyclerOnScrollListener);
    setRecycleNoScroll();

    complexLayoutRecycleViewAdapter.setOnItemClickListener(new ComplexLayoutRecycleViewAdapter.OnItemClickListener() {
      @Override
      public void onClick(int position) {
        ToastUtil.ShortToast(essayBeens.get(position).getTitle());
          Intent intent =new Intent(getActivity(), EssayDetailActivity.class);
          startActivity(intent);
      }
      @Override
      public void onLongClick(int position) {
        //ToastUtil.ShortToast(position+"");
      }
    });
  }

  @Override
  public void loadData(){
    for(int i=0;i<10;i++){
      EssayBean bean=new EssayBean();
      bean.setId(i+"");
      bean.setTitle("测试文章"+i);
      bean.setPublisher("dsy"+i);
      bean.setTime("2017-7-7");
      bean.setImg("/homemaking/upload/1496140112123.jpg");
      bean.setContent("TextView文字中间加横线:" +
              "tv_goods_price=(TextView) v.findViewById(R.id.tv_goods_price);" +
              "tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);" +
              "底部加横线:" +
              "tv_goods_price.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);"+i);
      bean.setLayoutstyle("1");
      List<EssayTag> tags=new ArrayList<>();
      for(int j=0;j<3;j++){
        EssayTag essayTag=new EssayTag();
        essayTag.setTag("android"+i);
        tags.add(essayTag);
      }
      bean.setTags(tags);
      essayBeens.add(bean);
    }

    finishTask();

  }

  @Override
  public void finishTask() {
    mIsRefreshing = false;
    loadMoreView.setVisibility(View.GONE);
    if (mSwipeRefreshLayout.isRefreshing()) {
      mSwipeRefreshLayout.setRefreshing(false);
    }
    if (pageNum * pageSize - pageSize - 1 > 0) {
      LogUtil.d("essayBeens:"+essayBeens.size()+"");
      complexLayoutRecycleViewAdapter.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1, pageSize);
    } else {

      LogUtil.d("essayBeens:"+essayBeens.size()+"");
      complexLayoutRecycleViewAdapter.notifyDataSetChanged();

    }
  }

  private void createHeadView() {

  }
  private View loadMoreView;

  private void createLoadMoreView() {
    loadMoreView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_load_more, recyclerView, false);
    mHeaderViewRecyclerAdapter.addFooterView(loadMoreView);
    loadMoreView.setVisibility(View.GONE);
  }

  private void setRecycleNoScroll() {
    recyclerView.setOnTouchListener((View v, MotionEvent event) -> {
      return mIsRefreshing;
    });
  }

}
