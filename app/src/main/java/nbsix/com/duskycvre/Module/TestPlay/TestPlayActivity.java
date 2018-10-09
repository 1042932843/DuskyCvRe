package nbsix.com.duskycvre.Module.TestPlay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

import com.nbsix.dsy.bannerview.BannerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.duskycvre.Adapter.WebBannerAdapter;
import nbsix.com.duskycvre.Design.keyEditText.KeyEditText;
import nbsix.com.duskycvre.Design.test.shotView;
import nbsix.com.duskycvre.Module.Base.BaseActivity;
import nbsix.com.duskycvre.R;

/**
 * Name: LoginActivity
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-07-20 15:33
 */

public class TestPlayActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.shotView)
    shotView shotView;


    @BindView(R.id.banner)
    BannerView bannerView;

    WebBannerAdapter webBannerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.test_play;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar.setTitle("宅验室");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        List<String> list = new ArrayList<>();
        list.add("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=2293177440,3125900197&fm=27&gp=0.jpg");
        list.add("http://img3.imgtn.bdimg.com/it/u=3967183915,4078698000&fm=27&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=3184221534,2238244948&fm=27&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=1794621527,1964098559&fm=27&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=1243617734,335916716&fm=27&gp=0.jpg");
        webBannerAdapter=new WebBannerAdapter(this,list);
        webBannerAdapter.setOnBannerItemClickListener(new BannerView.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(TestPlayActivity.this, "点击了第  " + position+"  项", Toast.LENGTH_SHORT).show();
            }
        });
        bannerView.setAdapter(webBannerAdapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    public void initToolBar() {

    }
}
