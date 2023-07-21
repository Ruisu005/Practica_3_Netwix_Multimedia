package com.example.netwix_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    private FullScreenVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);

        // Obtener el nombre de la película seleccionada
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String selectedMovie = extras.getString("selectedMovie");
            int videoResource = ObtenerVideos(selectedMovie);
            if (videoResource != 0) {
                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResource);

                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(videoView);

                videoView.setMediaController(mediaController);
                videoView.setVideoURI(videoUri);
                videoView.start();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
    }

    private int ObtenerVideos(String selectedMovie) {
        switch (selectedMovie) {
            case "Frozen":
                return R.raw.frozen_video;
            case "Barbie":
                return R.raw.barbie_video;
            case "Paw Patrol":
                return R.raw.paw_patrol_video;
            case "Dragon Ball":
                return R.raw.dragon_ball_video;
            case "Godzilla":
                return R.raw.godzilla_video;
            case "John Wick":
                return R.raw.john_wick_video;
            case "El Despertar":
                return R.raw.el_despertar_video;
            case "The Hammer":
                return R.raw.oppenheimer_video;
            case "Terrifier":
                return R.raw.terrifier_video;
            default:
                return 0;
        }
    }
}