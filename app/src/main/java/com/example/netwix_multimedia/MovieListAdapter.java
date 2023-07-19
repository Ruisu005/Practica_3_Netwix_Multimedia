package com.example.netwix_multimedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieListAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] movies;
    private int[] movieCovers;

    public MovieListAdapter(Context context, String[] movies, int[] movieCovers) {
        super(context, R.layout.list_item_movie, movies);
        this.context = context;
        this.movies = movies;
        this.movieCovers = movieCovers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_movie, parent, false);
        }

        TextView textViewMovieName = view.findViewById(R.id.textViewMovieName);
        ImageView imageViewMovie = view.findViewById(R.id.imageViewMovie);

        textViewMovieName.setText(movies[position]);
        imageViewMovie.setImageResource(movieCovers[position]);

        return view;
    }
}
