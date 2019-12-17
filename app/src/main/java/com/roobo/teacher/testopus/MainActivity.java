package com.roobo.teacher.testopus;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private ExoPlayer mPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://media.roobo.net/res/20191212/1ed6d607e7105de7d2933f545e28ec40.opus";
                try {
                    if (mPlayer == null) {
                        LibopusAudioRenderer audioRenderer = new LibopusAudioRenderer();
                        mPlayer = new ExoPlayer.Builder(MainActivity.this, audioRenderer).build();
                    }
                    MediaSource mediaSource =
                            new ProgressiveMediaSource.Factory(
                                    new DefaultDataSourceFactory(MainActivity.this, "ExoPlayerExtOpusTest"),
                                    MatroskaExtractor.FACTORY)
                                    .createMediaSource(Uri.parse(url));
                    mPlayer.addListener(
                            new Player.EventListener() {
                                @Override
                                public void onPlayerError(ExoPlaybackException error) {
                                    error.printStackTrace();
                                }
                                @Override
                                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                                    if (playbackState == Player.STATE_ENDED) {
                                    }
                                }
                            }
                    );
                    mPlayer.prepare(mediaSource);
                    mPlayer.setPlayWhenReady(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
