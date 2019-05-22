package com.example.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class Fragment extends android.support.v4.app.Fragment {
    protected View mRoot;
    protected Unbinder mRootUnbinder;
    /**
     * fragment添加到activity时最先调用的函数
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        initArgs(getArguments());
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRoot==null)
        {
            int layId=getContentLayoutId();
            View root=inflater.inflate(layId,container,false);//初始化当前根布局，但是不在创建时添加到container
            initwidget(root);
            mRoot=root;
        }
        else {
            if (mRoot.getParent()!=null)//判断父布局是否为空
            {

                ((ViewGroup)mRoot.getParent()).removeView(mRoot);//把当前root从父布局中移除
            }
        }

        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }
    /**
     * 初始化相关参数，
     * @param bundle 初始化的参数
     * @return 初始化成功返回turn，否则返回false
     */
    protected void initArgs(Bundle bundle)
    {

    }
    /**
     * 子类必须重写，获取当前布局id
     * @return
     */
    protected abstract int getContentLayoutId();
    /**
     * 初始化控件
     */
    protected void initwidget(View root)
    {
        mRootUnbinder=ButterKnife.bind(this,root);
    }
    /**
     * 初始化数据
     */
    protected void initData()
    {

    }

    /**
     * 返回按键触发时调用
     * @return ture表示拦截该事件并处理，false表示未拦截
     */
    public boolean onBackPress()
    {
        return false;
    }
}
