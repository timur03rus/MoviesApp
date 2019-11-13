package dog.hunter.moviesapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieList {
    @SerializedName("films")
    @Expose
    private List<Movie> films = null;

    public List<Movie> getFilms() {
        return films;
    }

    public void setFilms(List<Movie> films) {
        this.films = films;
    }
}
