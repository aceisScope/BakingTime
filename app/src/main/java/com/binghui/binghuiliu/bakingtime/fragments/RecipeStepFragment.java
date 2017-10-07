package com.binghui.binghuiliu.bakingtime.fragments;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binghui.binghuiliu.bakingtime.R;
import com.binghui.binghuiliu.bakingtime.model.Recipe;
import com.binghui.binghuiliu.bakingtime.model.Step;
import com.binghui.binghuiliu.bakingtime.utility.GlideApp;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binghuiliu on 01/10/2017.
 */

public class RecipeStepFragment extends Fragment {

    @BindView(R.id.video_step)
    SimpleExoPlayerView stepVideo;

    @BindView(R.id.view_step_description)
    CardView stepDescriptionCard;

    @BindView(R.id.text_step)
    TextView stepDescription;

    @BindView(R.id.image_step)
    ImageView stepThumbnail;

    @BindBool(R.bool.is_pad)
    boolean is_pad;

    @BindString(R.string.recipe_step_key)
    String recipe_step_key;

    private Step step;

    private SimpleExoPlayer player;

    public void setStep(Step newStep){
        this.step = newStep;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step_fragment, container, false);
        ButterKnife.bind(this, view);

        if (getArguments() != null && is_pad) {
            // for two-panel case only
            step = getArguments().getParcelable(recipe_step_key);
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        displayStep();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !is_pad) {
            // Expand video, hide description, hide system UI
            playVideoInLandscape();
        }
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        super.onDestroy();
    }

    private void displayStep() {
        stepDescription.setText(step.description);
        if (step.videoURL != null && step.videoURL.length() >0) {
            initializePlayer(step.videoURL);
        } else {
            stepVideo.setVisibility(View.GONE);
        }
        if (step.thumbnailURL != null && step.thumbnailURL.length() >0){
            GlideApp.with(getContext())
                    .load(step.thumbnailURL)
                    .placeholder(R.drawable.recipe_image_placeholder)
                    .into(stepThumbnail);
        } else {
            stepThumbnail.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(String url) {
        if (player == null) {
            stepVideo.requestFocus();

            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            DataSource.Factory mediaDataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);

            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector(videoTrackSelectionFactory));

            stepVideo.setPlayer(player);

            player.setPlayWhenReady(true);

            DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                    mediaDataSourceFactory, extractorsFactory, null, null);

            player.prepare(mediaSource);
        }
    }

    private void playVideoInLandscape() {
        stepDescriptionCard.setVisibility(View.GONE);

        stepVideo.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        stepVideo.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions =  View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
