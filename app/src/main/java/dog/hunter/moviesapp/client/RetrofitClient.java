package dog.hunter.moviesapp.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance = null;

    public Retrofit getInstance() {
        if (instance == null)
            instance = new Retrofit.Builder()
                    .baseUrl("https://s3-eu-west-1.amazonaws.com/sequeniatesttask/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return instance;
    }
}
