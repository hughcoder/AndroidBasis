package com.hugh.basis.exoplayer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.exoplayer.util.Util;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.hugh.basis.R;

/**
 * Created by chenyw on 2019-09-09.
 */
public class ExoPlayerActivity extends AppCompatActivity {

    private final String testUrl="https://cdn.qupeiyin.cn/2019-08-05/ltNuscFZujPNePI0ZOSVIdBZ7Bxr.mp4";
    private PlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exoplayer);
        mPlayerView =findViewById(R.id.player_view);
        initExoPlayer();
        mPlayerView.setPlayer(mExoPlayer);
        mPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("aaa","点击了player");
            }
        });
    }

    private void initExoPlayer() {
        RenderersFactory renderersFactory=new DefaultRenderersFactory(this);
        DefaultTrackSelector trackSelector=new DefaultTrackSelector();
        LoadControl loadControl=new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this);

        SimpleExoPlayerView playerView=new SimpleExoPlayerView(this);
        playerView.setPlayer(mExoPlayer);

        Uri mp4Uri=Uri.parse(testUrl);
        DefaultDataSourceFactory dataSourceFactory=new DefaultDataSourceFactory(
                this, Util.getUserAgent(this,"exoPlayerTest"));
        ExtractorsFactory extractorsFactory=new DefaultExtractorsFactory();
        MediaSource mediaSource=new ExtractorMediaSource(
                mp4Uri,dataSourceFactory,extractorsFactory,null,null);
        mExoPlayer.prepare(mediaSource);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mExoPlayer!=null){
            mExoPlayer.release();
        }
    }
}
