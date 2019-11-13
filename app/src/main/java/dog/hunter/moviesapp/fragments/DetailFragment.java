package dog.hunter.moviesapp.fragments;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import dog.hunter.moviesapp.R;
import dog.hunter.moviesapp.databinding.FragmentDetailBinding;
import dog.hunter.moviesapp.model.Movie;

public class DetailFragment extends Fragment {

    public DetailFragment() {

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        View view = binding.getRoot();
        binding.setMovie((Movie) getArguments().getSerializable("MOVIE"));

        return view;
    }

}
