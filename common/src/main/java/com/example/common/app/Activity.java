package com.example.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.common.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //界面未初始化前初始化窗口
        initWidows();
        if (initArgs(getIntent().getExtras()))
        {
            int layid=getContentLayoutId();
            setContentView(layid);
            initwidget();
            initData();
        }else {
            finish();
        }

    }

    /**
     * 初始化窗口
     */
    protected void initWidows()
    {

    }

    /**
     * 初始化相关参数，
     * @param bundle 初始化的参数
     * @return 初始化成功返回turn，否则返回false
     */
    protected boolean initArgs(Bundle bundle)
    {

        return true;
    }
    /**
     * 子类必须重写，获取当前布局id
     * @return
     */
    protected abstract int getContentLayoutId();

    /**
     * 初始化控件
     */
    protected void initwidget()
    {
        ButterKnife.bind(this);
    }
    /**
     * 初始化数据
     */
    protected void initData()
    {

    }

    /**
     * 界面导航返回键点击处理
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {

        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //得到当前activity下的所有fragment
        List<Fragment> fragments=getSupportFragmentManager().getFragments();
        if (fragments!=null&&fragments.size()>0)
        {
            for (Fragment fragment:fragments)//遍历所有fragment是否处理返回键点击事件
            {
                if (fragment instanceof com.example.common.app.Fragment)
                {
                    if (((com.example.common.app.Fragment)fragment).onBackPress())//有处理则直接返回
                    {
                        return;
                    }

                }
            }
        }
        super.onBackPressed();
        finish();
    }
}
