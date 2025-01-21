package com.example.restaurant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class FavoritesFragment extends Fragment implements FavoritesAdapter.OnFavoriteClickListener {
    private RecyclerView recyclerView;
    private DatabaseHelper databaseHelper;
    private FavoritesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        databaseHelper = new DatabaseHelper(getActivity());

        // Load favorites from the database
        loadFavorites();

        return view;
    }

    private void loadFavorites() {
        List<Favorite> favorites = databaseHelper.getAllFavorites();
        adapter = new FavoritesAdapter(favorites, this); // Pass 'this' as the listener
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDeleteClick(Favorite favorite) {
        // Delete the favorite from the database
        databaseHelper.deleteFavorite(favorite.getId());

        // Refresh the list
        loadFavorites();

        // Notify the user
        Toast.makeText(getContext(), favorite.getRestaurant() + " removed from favorites!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorites(); // Refresh the list when the fragment resumes
    }
}