package nbsix.com.duskycvre.Module.LoginAndRegister;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import nbsix.com.duskycvre.Design.keyEditText.KeyEditText;
import nbsix.com.duskycvre.Module.Base.BaseActivity;
import nbsix.com.duskycvre.R;

/**
 * Name: LoginActivity
 * Author: Dusky
 * QQ: 1042932843
 * Comment: //TODO
 * Date: 2017-07-20 15:33
 */

public class LoginActivity extends BaseActivity implements KeyEditText.KeyPreImeListener {

    @BindView(R.id.username)
    KeyEditText username;
    @BindView(R.id.password)
    KeyEditText password;

    @BindView(R.id.toolbar)
    Toolbar login_toolbar;
    @BindView(R.id.login_btn)
    Button login_btn;

    @OnClick(R.id.user_register)
    public void show_register(){

    }

    @OnClick(R.id.login_btn)
    public void do_login(){

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        login_toolbar.setTitle("登录");
        setSupportActionBar(login_toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        username.setKeyPreImeListener(this);
        password.setKeyPreImeListener(this);
        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            login_btn.setEnabled(username.getText().length() != 0 && password.getText().length() != 0);
        }
    };

    @Override
    public void onKeyPreImeUp(int keyCode, KeyEvent event) {
        username.clearFocus();
        password.clearFocus();
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
