package com.zzkong.example.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;

/**
 * Created by lico on 2018/5/10
 */
public abstract class BaseActivity extends AppCompatActivity {

    public ImmersionBar mImmersionBar;
    protected Context mActivity;
    protected boolean isInitImmersionBar = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        setBaseConfig();
        initImmersionBar();
    }

    private void setBaseConfig() {
        AppManager.getAppManager().addActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        if (isInitImmersionBar()) {
            mImmersionBar
//                    .transparentStatusBar()  //透明状态栏，不写默认透明色
//                    .transparentNavigationBar()
                    .fitsSystemWindows(true)
//                    .statusBarDarkFont(true, 0.2f)//如果支持字体变黑就不会改变透明度
//                    .navigationBarColor(R.color.color_ffffff)
                    .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        mImmersionBar.init();
    }

    protected boolean isInitImmersionBar() {
        return isInitImmersionBar;
    }
//
//    public void backClick(View view) {
//        this.finish();
//    }
//
//    @Override
//    public void setTitle(int title) {
//        ((TextView) findViewById(R.id.title_tv)).setText(title);
//    }
//
//    public void setTitle(String title) {
//        ((TextView) findViewById(R.id.title_tv)).setText(title);
//    }
//
//    public void setLeftText(int title) {
//        ((TextView) findViewById(R.id.back_tv)).setText(title);
//    }
//
//    public TextView getLeftTv() {
//        return findViewById(R.id.back_tv);
//    }
//
//    public TextView getRightTv() {
//        return findViewById(R.id.right_tv);
//    }
//
//
//    public ImageView getSetImg() {
//        return findViewById(R.id.set_img);
//    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        AppManager.getAppManager().finishActivity(this);
    }
}
