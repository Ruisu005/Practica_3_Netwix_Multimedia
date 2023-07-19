package com.example.netwix_multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        // Obtener los datos enviados desde MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String profile = extras.getString("profile");

            // Mostrar el título de la actividad con el perfil seleccionado
            setTitle(profile);

            // Obtener el array de películas según el perfil
            int moviesResourceId = getMoviesResourceId(profile);
            String[] movies = getResources().getStringArray(moviesResourceId);

            // Obtener los recursos de las carátulas para cada película
            int[] movieCovers = getMovieCovers(profile);

            // Mostrar las películas en el ListView
            ListView listViewMovies = findViewById(R.id.listViewMovies);
            MovieListAdapter adapter = new MovieListAdapter(this, movies, movieCovers);
            listViewMovies.setAdapter(adapter);

            listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedMovie = (String) parent.getItemAtPosition(position);
                    openVideoPlayerActivity(selectedMovie);
                }
            });
        }
    }

    private int[] getMovieCovers(String profile) {
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
    private void openVideoPlayerActivity(String selectedMovie) {
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
}