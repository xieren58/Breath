package com.zkp.breath.tim;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.tencent.qcloud.tim.uikit.component.TitleBarLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;
import com.zkp.breath.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TimActivity extends AppCompatActivity {

    @BindView(R.id.conversation_layout)
    ConversationLayout conversationLayout;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim);
        mBind = ButterKnife.bind(this);

        initTimUi();
    }

    private void initTimUi() {
        conversationLayout.initDefault();
        TitleBarLayout titleBarLayout = conversationLayout.getTitleBar();
        ImageView imageView = titleBarLayout.getRightIcon();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("xxxx");
            }
        });

//        ConversationListLayout conversationListLayout = conversationLayout.getConversationList();
//        conversationListLayout.setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onDestroy() {
        mBind.unbind();
        super.onDestroy();
    }
}
