package young.project.android.com.bakeaide.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import young.project.android.com.bakeaide.R;
import young.project.android.com.bakeaide.domain.model.Recipe;
import young.project.android.com.bakeaide.view_model.SharedViewModel;

public class RecipeListFragment extends Fragment {
    private SharedViewModel mSharedViewModel;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private ActionBar mActionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        connectViewModel();
        connectRecyclerView(view);
        connectActionBar();
        return view;
    }

    private void connectActionBar(){
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle(R.string.app_name);

    }

    private void connectViewModel(){
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mSharedViewModel.getRecipeListLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                if(recipeList == null ){
                    Toast.makeText(getActivity().getApplicationContext(),
                            getResources().getString(R.string.no_internet_access),
                            Toast.LENGTH_LONG).show();
                } else{
                    updateRecyclerView(recipeList);
                }
            }
        });
    }

    private void connectRecyclerView(View view){
        List<Recipe> placeHolder = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.rv_recipe_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewAdapter = new RecipeListFragmentRecyclerViewAdapter(getActivity(), placeHolder);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void updateRecyclerView(List<Recipe> recipeList){
        mRecyclerViewAdapter = new RecipeListFragmentRecyclerViewAdapter(getActivity(), recipeList);
        mRecyclerView.swapAdapter(mRecyclerViewAdapter, true);
    }


}
