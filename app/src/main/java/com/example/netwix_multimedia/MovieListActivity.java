package com.example.netwix_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MovieListActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // Obtener los datos enviados desde MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String profile = extras.getString("profile");

            // Reproducir la canción correspondiente según la categoría del perfil
            playProfileSong(profile);

            // Obtener una referencia al botón de pausa/reanudar
            Button btnPauseResume = findViewById(R.id.btnPauseResume);

            // Acción al hacer clic en el botón de pausa/reanudar
            btnPauseResume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    togglePauseResume();
                }
            });

            // Mostrar el título de la actividad con el perfil seleccionado
            setTitle(profile);

            // Obtener el array de películas según el perfil
            int moviesResourceId = getMoviesResourceId(profile);
            String[] movies = getResources().getStringArray(moviesResourceId);

            // Obtener los recursos de las carátulas para cada película
            int[] movieCovers = getCaratulas(profile);

            // Mostrar las películas en el ListView
            ListView listViewMovies = findViewById(R.id.listViewMovies);
            MovieListAdapter adapter = new MovieListAdapter(this, movies, movieCovers);
            listViewMovies.setAdapter(adapter);

            listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedMovie = (String) parent.getItemAtPosition(position);
                    AbrirVideos(selectedMovie);
                }
            });
        }
    }

    private int[] getCaratulas(String profile) {
        int[] movieCovers;
        switch (profile) {
            case "Infantil":
                movieCovers = new int[]{
                        R.drawable.frozen_cover,
                        R.drawable.barbie_cover,
                        R.drawable.paw_patrol_cover
                };
                break;
            case "Adolescente":
                movieCovers = new int[]{
                        R.drawable.dragon_ball_cover,
                        R.drawable.godzilla_cover,
                        R.drawable.john_wick_cover
                };
                break;
            case "Adulto":
                movieCovers = new int[]{
                        R.drawable.el_despertar_cover,
                        R.drawable.oppenheimer_cover,
                        R.drawable.terrifier_cover
                };
                break;
            default:
                movieCovers = new int[]{};
                break;
        }
        return movieCovers;
    }
    private void AbrirVideos(String selectedMovie) {
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("selectedMovie", selectedMovie);
        startActivity(intent);
    }

    private int getMoviesResourceId(String profile) {
        int resourceId;
        switch (profile) {
            case "Infantil":
                resourceId = R.array.movies_infantil;
                break;
            case "Adolescente":
                resourceId = R.array.movies_adolescente;
                break;
            case "Adulto":
                resourceId = R.array.movies_adulto;
                break;
            default:
                resourceId = -1;
                break;
        }
        return resourceId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reanudar la reproducción de la canción al volver a la actividad
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausar la reproducción de la canción cuando la actividad está en segundo plano
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener la reproducción de la canción y liberar recursos cuando la actividad se destruye
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playProfileSong(String profile) {
        int songResource;
        switch (profile) {
            case "Infantil":
                songResource = R.raw.frozen_song;
                break;
            case "Adolescente":
                songResource = R.raw.dragon_ball_song;
                break;
            case "Adulto":
                songResource = R.raw.alicia_song;
                break;
            default:
                songResource = -1;
                break;
        }

        if (songResource != -1) {
            mediaPlayer = MediaPlayer.create(this, songResource);
            mediaPlayer.setLooping(true); // Reproducir en bucle
            mediaPlayer.start();
        }
    }

    private void togglePauseResume() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            updatePauseResumeButton();
        } else if (mediaPlayer != null) {
            mediaPlayer.start();
            isPlaying = true;
            updatePauseResumeButton();
        }
    }

    private void updatePauseResumeButton() {
        Button btnPauseResume = findViewById(R.id.btnPauseResume);
        if (isPlaying) {
            btnPauseResume.setText("Pausar");
        } else {
            btnPauseResume.setText("Reanudar");
        }
    }

}