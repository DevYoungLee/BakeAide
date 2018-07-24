package young.project.android.com.bakeaide.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import young.project.android.com.bakeaide.R;
import young.project.android.com.bakeaide.domain.model.Recipe;
import young.project.android.com.bakeaide.view_model.SharedViewModel;

public class StepDetailFragment extends Fragment{

    private final String PLAYER_POSITION_KEY = "player_position_key";
    private final String PLAYER_PLAY_WHEN_READY_KEY = "player_play_when_ready_key";

    private SharedViewModel mSharedViewModel;
    private TextView mStepDescriptionTextView;
    private Button mPrevStepButton;
    private Button mNextStepButton;
    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;
    private FragmentChangeListener mFragmentChangeListener;
    private ActionBar mActionBar;
    private int mRecipePosition;
    private int mStepPosition;
    private long mPlayerPosition;
    private boolean mPlayerIsPlayWhenReady;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mPlayerPosition = savedInstanceState.getLong(PLAYER_POSITION_KEY);
            mPlayerIsPlayWhenReady = savedInstanceState.getBoolean(PLAYER_PLAY_WHEN_READY_KEY);
        }
        if(getArguments() != null) {
            mRecipePosition = getArguments().getInt(MainActivity.EXTRA_RECIPE_ID_KEY);
            mStepPosition = getArguments().getInt(MainActivity.EXTRA_STEP_ID_KEY);
        }
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        mFragmentChangeListener = (MainActivity) getActivity();
        mStepDescriptionTextView = view.findViewById(R.id.tv_step_detail_description);
        mPrevStepButton = view.findViewById(R.id.b_prev);
        mNextStepButton = view.findViewById(R.id.b_next);
        mPlayerView = view.findViewById(R.id.player_step_detail);
        connectViewModel();
        return view;
    }

    private void connectActionBar(List<Recipe> recipeList){
        mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        if(!getResources().getBoolean(R.bool.isTablet)) {
            mActionBar.setTitle(recipeList.get(mRecipePosition).getSteps().get(mStepPosition).getShortDescription());
        } else {
            mActionBar.setTitle(recipeList.get(mRecipePosition).getName() + ": " + recipeList.get(mRecipePosition).getSteps().get(mStepPosition).getShortDescription());
        }

    }


    private void connectViewModel(){
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mSharedViewModel.getRecipeListLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                if (recipeList != null) {
                    connectActionBar(recipeList);
                    int numberOfSteps = recipeList.get(mRecipePosition).getSteps().size();
                    connectButtons(numberOfSteps);
                    mStepDescriptionTextView.setText(recipeList.get(mRecipePosition).getSteps().get(mStepPosition).getDescription());
                    String videoUrlString = recipeList.get(mRecipePosition).getSteps().get(mStepPosition).getVideoURL();
                    String thumbnailUrlString = recipeList.get(mRecipePosition).getSteps().get(mStepPosition).getThumbnailURL();
                    if (videoUrlString.isEmpty()) {
                        videoUrlString = thumbnailUrlString;
                    }
                    if (!videoUrlString.isEmpty()) {
                        Uri uri = Uri.parse(videoUrlString);
                        initializePlayer(uri);
                    } else {
                        mPlayerView.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    private void connectButtons(final int numberOfSteps){

        if(mStepPosition <= 0){
            mPrevStepButton.setVisibility(View.GONE);
        }
        if(mStepPosition >= numberOfSteps - 1){
            mNextStepButton.setVisibility(View.GONE);
        }
        mPrevStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStepPosition > 0){
                    mFragmentChangeListener.replaceFragment(
                            new StepDetailFragment(),
                            mRecipePosition, (mStepPosition - 1),
                            MainActivity.STEP_DETAIL_FRAGMENT_TAG);

                }
            }
        });

        mNextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mStepPosition <= numberOfSteps - 2){
                    mFragmentChangeListener.replaceFragment(
                            new StepDetailFragment(),
                            mRecipePosition, (mStepPosition + 1),
                            MainActivity.STEP_DETAIL_FRAGMENT_TAG);
                }
            }
        });
    }

    private void initializePlayer(Uri uri){

            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
            mPlayerView.setPlayer(mPlayer);

            DataSource.Factory dataSourceFacory = new DefaultDataSourceFactory(
                    getActivity(),
                    Util.getUserAgent(getActivity(), getResources().getString(R.string.app_name)),
                    bandwidthMeter);

            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFacory)
                    .createMediaSource(uri);
            mPlayer.prepare(mediaSource);
            if(mPlayerPosition != C.TIME_UNSET){
                mPlayer.seekTo(mPlayerPosition);

            }
            mPlayer.setPlayWhenReady(mPlayerIsPlayWhenReady);

    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onPause() {
        if(mPlayer != null) {
            mPlayer.release();
            mPlayerPosition = mPlayer.getCurrentPosition();
            mPlayerIsPlayWhenReady = mPlayer.getPlayWhenReady();
        }
        super.onPause();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION_KEY, mPlayerPosition);
        outState.putBoolean(PLAYER_PLAY_WHEN_READY_KEY, mPlayerIsPlayWhenReady);
    }
}
