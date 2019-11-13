package dog.hunter.moviesapp.client;

import java.util.List;

import dog.hunter.moviesapp.model.Movie;
import dog.hunter.moviesapp.model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesApi {
    @GET("films.json")
    Call<MovieList> getMovies();
}
