package com.hugh.basis.home.homeSub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hugh.basis.MainActivity;
import com.hugh.basis.R;
import com.hugh.basis.activities.ListActivity;
import com.hugh.basis.activities.TransParentActivity;
import com.hugh.basis.animate.AnimationActivity;
import com.hugh.basis.camera2face.Camera2FaceActivity;
import com.hugh.basis.constraintlayout.ConstraintLayoutActivity;
import com.hugh.basis.dialog.DialogShowActivity;
import com.hugh.basis.eventbus.EventActivity1;
import com.hugh.basis.exoplayer.ExoPlayerActivity;
import com.hugh.basis.eyeshield.EyeProtectActivity;
import com.hugh.basis.highOrderUI.UiActivity;
import com.hugh.basis.home.model.HomeTabEntity;
import com.hugh.basis.home.vh.SingleHomeTabVH;
import com.hugh.basis.hook.HookTestActivity;
import com.hugh.basis.ijkplayer.PlayActivity;
import com.hugh.basis.leakcanary.LeakActivity;
import com.hugh.basis.puzzleGame.PuzzleActivity;
import com.hugh.basis.record.RecordActivity;
import com.hugh.basis.retrofit.RetrofitActivity;
import com.hugh.basis.rvlooper.LooperActivity;
import com.hugh.basis.third.greendao.NoteActivity;
import com.hugh.basis.thread.ThreadTestAcitivity;
import com.hugh.basis.timer.TimerActivity;
import com.hugh.basis.videoView.VideoViewActivity;
import com.hugh.basis.webView.WebViewActivity;
import com.hugh.ffmpeg.CCMainActivity;
import com.zhl.commonadapter.BaseViewHolder;
import com.zhl.commonadapter.CommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by chenyw on 2020/8/11.
 */
public class HomeSubActivity extends Activity {

    private TextView mTvTitle;
    private static final String TYPE_KEY_SUB = "type_key_sub";
    private int mType;
    private RecyclerView mRvList;
    private List<HomeTabEntity> mHomeTabEntityList;
    private CommonRecyclerAdapter<HomeTabEntity> mAdapter;
    private Activity mActivity;

    public static Intent createIntent(Activity activity, int type) {
        Intent intent = new Intent(activity, HomeSubActivity.class);
        intent.putExtra(TYPE_KEY_SUB, type);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_home);
        mActivity = HomeSubActivity.this;
        if (getIntent() != null) {
            mType = getIntent().getIntExtra(TYPE_KEY_SUB, 1);
        }
        mHomeTabEntityList = new ArrayList<>();
        mRvList = findViewById(R.id.rv_list);
        mTvTitle = findViewById(R.id.tv_title);
        mTvTitle.setText("子页面");
        iniData(mType);
        mAdapter = new CommonRecyclerAdapter<HomeTabEntity>(mHomeTabEntityList) {
            @Override
            public BaseViewHolder<HomeTabEntity> createViewHolder(int type) {
                return new SingleHomeTabVH();
            }
        };
        setAdapterClick();
        mRvList.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(HomeSubActivity.this, 3);
        mRvList.setLayoutManager(gridLayoutManager);

    }

    private void iniData(int type) {
        switch (type) {
            case 1: //android ui
                addAndroidUI();
                break;
            case 2:
                addAndroidThirdParty();
                break;
            case 3:
                addAndroidVideo();
                break;
            case 4:
                addAndroidBasis();
                break;
        }

    }

    private void setAdapterClick() {
        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int type = mHomeTabEntityList.get(position).getType();
                Intent intent = new Intent();
                switch (type) {
                    case 1:
                        intent = new Intent(mActivity, PuzzleActivity.class);
                        break;
                    case 2:
                        intent = new Intent(mActivity, EyeProtectActivity.class);
                        break;
                    case 3:
                        intent = new Intent(mActivity, Camera2FaceActivity.class);
                        break;
                    case 4:
                        intent = new Intent(mActivity, AnimationActivity.class);
                        break;
                    case 5:
                        intent = new Intent(mActivity, ListActivity.class);
                        break;
                    case 6:
                        intent = new Intent(mActivity, ConstraintLayoutActivity.class);
                        break;
                    case 7:
                        intent = new Intent(mActivity, LooperActivity.class);
                        break;
                    case 8:
                        intent = new Intent(mActivity, EventActivity1.class);
                        break;
                    case 9:
                        intent = new Intent(mActivity, NoteActivity.class);
                        break;
                    case 10:
                        intent = new Intent(mActivity, RetrofitActivity.class);
                        break;
                    case 11:
                        intent = new Intent(mActivity, PlayActivity.class);
                        break;
                    case 12:
                        intent = new Intent(mActivity, VideoViewActivity.class);
                        break;
                    case 13:
                        intent = new Intent(mActivity, ExoPlayerActivity.class);
                        break;
                    case 14:
                        intent = new Intent(mActivity, RecordActivity.class);
                        break;
                    case 15:
                        intent = new Intent(mActivity, WebViewActivity.class);
                        break;
                    case 16:
                        intent = new Intent(mActivity, TimerActivity.class);
                        break;
                    case 17:
                        intent = new Intent(mActivity, DialogShowActivity.class);
                        break;
                    case 18:
                        intent = new Intent(mActivity, TransParentActivity.class);
                        break;
                    case 19:
                        intent = new Intent(mActivity, LeakActivity.class);
                        break;
                    case 20:
                        intent = new Intent(mActivity, UiActivity.class);
                        break;
                    case 21:
                        intent = new Intent(mActivity, HookTestActivity.class);
                        break;
                    case 22:
                        intent = new Intent(mActivity, ThreadTestAcitivity.class);
                        break;

                }
                startActivity(intent);
            }
        });
    }

    private void addAndroidUI() {
        addHomeTab("进入拼图", 1);
        addHomeTab("去护眼模式", 2);
        addHomeTab("gocamera2", 3);
        addHomeTab("进入动画相关", 4);
        addHomeTab("进入list相关", 5);
        addHomeTab("进去constraintLayout约束布局界面", 6);
        addHomeTab("进去recyclerview循环滚动", 7);
    }

    private void addAndroidThirdParty() {
        addHomeTab("进入消息传递Activity", 8);
        addHomeTab("去GreenDao数据库页面", 9);
        addHomeTab("进入retrofit页面", 10);
    }

    private void addAndroidVideo() {
        addHomeTab("进入播放相关页面", 11);
        addHomeTab("进入VideoView页面", 12);
        addHomeTab("进入exoplayer播放器页面", 13);
        addHomeTab("去录音界面", 14);
    }

    private void addAndroidBasis() {
        addHomeTab("进入Web相关页面", 15);
        addHomeTab("进入多任务定时页面", 16);
        addHomeTab("进去dialog弹框界面", 17);
        addHomeTab("弹出透明的activity", 18);
        addHomeTab("内存泄漏的activity", 19);
        addHomeTab("去高阶ui界面", 20);
        addHomeTab("去hook界面", 21);
        addHomeTab("去线程相关页面", 22);

    }

    private void addHomeTab(String title, int type) {
        HomeTabEntity homeTabEntity = new HomeTabEntity("", title, type);
        mHomeTabEntityList.add(homeTabEntity);
    }


}
