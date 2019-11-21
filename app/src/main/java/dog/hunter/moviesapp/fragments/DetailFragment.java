package dog.hunter.moviesapp.fragments;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import dog.hunter.moviesapp.R;
import dog.hunter.moviesapp.databinding.FragmentDetailBinding;
import dog.hunter.moviesapp.model.Movie;

public class DetailFragment extends Fragment {
    private String title;
    private Toolbar toolbar;

    public DetailFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            title = savedInstanceState.getString("TITLE");
            setupToolbar(title);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            title = getArguments().getString("LOCAL_NAME");
        }
        toolbar = getActivity().findViewById(R.id.toolbar);
        setupToolbar(title);

        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        View view = binding.getRoot();
        binding.setMovie((Movie) getArguments().getSerializable("MOVIE"));

        return view;
    }

    private void setupToolbar(String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TITLE", title);
    }
}
