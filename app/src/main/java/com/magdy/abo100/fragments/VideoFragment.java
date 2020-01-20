package com.magdy.abo100.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.magdy.abo100.R;
import com.magdy.abo100.helpers.StaticMembers;

public class VideoFragment extends Fragment {

    private String url;
    private VideoView video;
    private RelativeLayout progress;
    private boolean isPlaying, isFirst;
    private int stopPosition = 0;
    private ImageView clicker;

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
        video = view.findViewById(R.id.video);
        MediaController controller = new MediaController(getContext());
        styleMediaController(controller);
        controller.setMediaPlayer(video);
        video.setMediaController(controller);
        clicker = view.findViewById(R.id.clicker);
        progress = view.findViewById(R.id.progress);
        video.setVideoPath(url);
        isPlaying = false;
        isFirst = true;
        video.setOnPreparedListener(mp -> {
            progress.setVisibility(View.GONE);
            if (isPlaying) {
                video.seekTo(stopPosition);
                video.postDelayed(() -> video.start(), 200);
            }
            isFirst = false;
        });
        //resumeVideo();
        clicker.setOnClickListener(v -> {
            progress.setVisibility(View.VISIBLE);
            if (isPlaying) {
                progress.setVisibility(View.GONE);
                pauseVideo();
            } else {
                video.resume();
                clicker.setVisibility(View.GONE);
            }
            isPlaying = !isPlaying;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideo();
    }

    public void pauseVideo() {
        if (video != null) {
            stopPosition = video.getCurrentPosition();
            video.pause();
            clicker.setVisibility(View.VISIBLE);
        }
    }

    public void resumeVideo() {
        if (video != null) {
            progress.setVisibility(View.VISIBLE);
            isPlaying = true;
            video.resume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (video != null && !isFirst) {
            video.seekTo(stopPosition);
            video.start();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (video != null)
            video.resume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (video != null)
            video.stopPlayback();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (video != null)
            video.stopPlayback();
    }

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
