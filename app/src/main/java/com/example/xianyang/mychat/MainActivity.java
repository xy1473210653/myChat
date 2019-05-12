package com.example.xianyang.mychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.common.app.Activity;

import butterknife.BindView;

public class MainActivity extends Activity {
    @BindView(R.id.test_txt) TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {

        return R.layout.main_activity_layout;
    }

    @Override
    protected void initwidget() {
        super.initwidget();
        textView.setText("你好啊啊啊啊啊");
    }
}
