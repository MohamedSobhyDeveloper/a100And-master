package com.Pazarabo100kwt.pazar.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.Pazarabo100kwt.pazar.R;
import com.Pazarabo100kwt.pazar.helpers.StaticMembers;
import com.potyvideo.library.AndExoPlayerView;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideoFragment extends Fragment {

    private String url;
    private JCVideoPlayerStandard video;
    private RelativeLayout progress;
    private boolean isPlaying, isFirst;
    private int stopPosition = 0;
    private ImageView clicker;
    WebView videoWeb;

    public static VideoFragment getInstance(String url) {
        VideoFragment f = new VideoFragment();
        f.url = url;
        return f;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(StaticMembers.VIDEO, url);
        outState.putInt(StaticMembers.STOP, stopPosition);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.item_video_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString(StaticMembers.VIDEO);
            stopPosition = savedInstanceState.getInt(StaticMembers.STOP);
        }

        videoWeb =view.findViewById(R.id.videoWebView);

//        videoWeb.setWebViewClient(new Browser_Home());
//        videoWeb.setWebChromeClient(new ChromeClient(((VideosListActivity) context)));
        videoWeb.setWebViewClient(new WebViewClient());
        videoWeb.getSettings().setJavaScriptEnabled(true);
        videoWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        videoWeb.getSettings().setPluginState(WebSettings.PluginState.OFF);
        videoWeb.getSettings().setMediaPlaybackRequiresUserGesture(false);
        videoWeb.setWebChromeClient(new WebChromeClient());
        videoWeb.loadUrl(url);



//        video = view.findViewById(R.id.andExoPlayerView);
//        video.setUp(url
//                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
//
//
//        clicker = view.findViewById(R.id.clicker);
//
//        clicker.setOnClickListener(v -> {
//            clicker.setVisibility(View.GONE);
//            if (url!=null){
//                video.setVisibility(View.VISIBLE);
//                video.setUp(url
//                        , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
//
//
//            }
//
//        });

    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        pauseVideo();
//    }
//
//    public void pauseVideo() {
//        if (video != null) {
//            stopPosition = video.getCurrentPosition();
//            video.pause();
//            clicker.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void resumeVideo() {
//        if (video != null) {
//            progress.setVisibility(View.VISIBLE);
//            isPlaying = true;
//            video.resume();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (video != null && !isFirst) {
//            video.seekTo(stopPosition);
//            video.start();
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (video != null)
//            video.resume();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (video != null)
//            video.stopPlayback();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (video != null)
//            video.stopPlayback();
//    }




    private void styleMediaController(View view) {
        if (view instanceof MediaController) {
            MediaController v = (MediaController) view;
            for (int i = 0; i < v.getChildCount(); i++) {
                styleMediaController(v.getChildAt(i));
            }
        } else if (view instanceof LinearLayout) {
            LinearLayout ll = (LinearLayout) view;
            for (int i = 0; i < ll.getChildCount(); i++) {
                styleMediaController(ll.getChildAt(i));
            }
        } else if (view instanceof SeekBar) {
            ((SeekBar) view).getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            ((SeekBar) view).getThumb().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        }
    }


}
