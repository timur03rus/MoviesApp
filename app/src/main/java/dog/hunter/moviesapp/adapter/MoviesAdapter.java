package dog.hunter.moviesapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import dog.hunter.moviesapp.R;
import dog.hunter.moviesapp.fragments.DetailFragment;
import dog.hunter.moviesapp.model.Movie;
import dog.hunter.moviesapp.model.MovieList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    MovieList movies;
    Context context;
    Bundle bundle = new Bundle();


    public MoviesAdapter(Context context, MovieList movies) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.bind(movies.getFilms().get(position));
    }

    @Override
    public int getItemCount() {
        return movies.getFilms().size();
    }


    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movieImage;
        private TextView movieTitle;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movie_image);
            movieTitle = itemView.findViewById(R.id.movie_title);
            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie) {
            Glide.with(context).load(movie.getImageUrl()).error(R.drawable.ic_launcher_background).into(movieImage);
            movieTitle.setText(movie.getLocalizedName());
        }

        @Override
        public void onClick(View v) {

            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            DetailFragment fragment = new DetailFragment();
            bundle.putSerializable("MOVIE", movies.getFilms().get(getAdapterPosition()));
            fragment.setArguments(bundle);
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
