package dog.hunter.moviesapp.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dog.hunter.moviesapp.R;
import dog.hunter.moviesapp.adapter.MoviesAdapter;
import dog.hunter.moviesapp.client.MoviesApi;
import dog.hunter.moviesapp.client.RetrofitClient;
import dog.hunter.moviesapp.model.Movie;
import dog.hunter.moviesapp.model.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MoviesListFragment extends Fragment {
    private Toolbar toolbar;
    private int spinnerPosition = 0;

    RecyclerView recyclerView;
    MoviesAdapter adapter;
    MovieList movies;
    MovieList sortedMovie;
    Spinner spinner;
    String[] genres = {
            "Выберите жанр:",
            "драма", "мелодрама", "комедия", "приключения",
            "боевик", "ужасы", "биография", "триллер", "криминал",
            "детектив", "фантастика", "фэнтези", "мюзикл"
    };

    List<Movie> movieList;

    public MoviesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        fetchData();

        if (savedInstanceState != null) {
            spinnerPosition = savedInstanceState.getInt("SPINNER_POSITION");
        }

        spinner = view.findViewById(R.id.spinner);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        toolbar = getActivity().findViewById(R.id.toolbar);
        setupToolbar();

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("SPINNER_POSITION", spinnerPosition);
    }

    private void setupToolbar() {
        toolbar.setTitle("MoviesApp");
    }

    private void fetchData() {
        Retrofit retrofit = new RetrofitClient().getInstance();
        retrofit.create(MoviesApi.class).getMovies().enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                movies = response.body();

                setupRecyclerAdapter();
                setupSpinnerAdapter();
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Toast.makeText(getContext(), "Что-то пошло не так, проверьте интернет соединение.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupRecyclerAdapter() {
        Collections.sort(movies.getFilms(), (movie1, movie2) ->
                movie1.getLocalizedName().compareTo(movie2.getLocalizedName())
        );
        adapter = new MoviesAdapter(getContext(), movies);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setupSpinnerAdapter() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, genres);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setPrompt("Выберите жанр");
        spinner.setSelection(spinnerPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    spinnerPosition = position;

                    setupRecyclerAdapter();
                } else {
                    spinnerPosition = position;

                    sortMovies();
                    adapter = new MoviesAdapter(getContext(), sortedMovie);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sortMovies() {
        sortedMovie = new MovieList();
        movieList = new ArrayList<>();
        for (Movie movie : movies.getFilms()) {
            if (movie.getGenres().contains(spinner.getSelectedItem().toString())) {
                movieList.add(movie);
            }
        }
        sortedMovie.setFilms(movieList);
    }
}
