package nbsix.com.duskycvre.Module.Search;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
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

public class SearchActivity extends BaseActivity{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.shotView)
    shotView shotView;

    @Override
    public int getLayoutId() {
        return R.layout.test_play;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        toolbar.setTitle("你在找什么呢");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
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
