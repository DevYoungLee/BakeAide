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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import young.project.android.com.bakeaide.R;
import young.project.android.com.bakeaide.domain.model.Ingredient;
import young.project.android.com.bakeaide.domain.model.Recipe;
import young.project.android.com.bakeaide.domain.model.Step;
import young.project.android.com.bakeaide.view_model.SharedViewModel;

public class RecipeOverviewFragment extends Fragment {
    private SharedViewModel mSharedViewModel;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView.Adapter mRecyclerViewAdapter;
    private RecyclerView mIngredientRecyclerView;
    private LinearLayoutManager mIngredientLinearLayoutManager;
    private RecyclerView.Adapter mIngredientRecyclerViewAdapter;
    private TextView mIngredientHeaderTextView;
    private FragmentChangeListener fragmentChangeListener;
    private ActionBar mActionBar;
    private int mRecipePosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_overview, container, false);
        if (getArguments() != null) {
            mRecipePosition = getArguments().getInt(MainActivity.EXTRA_RECIPE_ID_KEY);
        }
        fragmentChangeListener = (MainActivity) getActivity();
        connectRecyclerView(view);
        connectViewModel();
        mIngredientHeaderTextView = view.findViewById(R.id.tv_ingredients_header);
        return view;
    }

    private void connectActionBar(List<Recipe> recipeList){
        mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        mActionBar.setTitle(recipeList.get(mRecipePosition).getName());
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void connectViewModel(){
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        mSharedViewModel.getRecipeListLiveData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                if(recipeList != null) {
                    connectActionBar(recipeList);
                    updateRecyclerView(recipeList);
                }
            }
        });
    }

    private void connectRecyclerView(View view){
        List<Step> placeHolder = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.rv_steps_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewAdapter = new RecipeOverviewFragmentRecyclerViewAdapter(getActivity(), mRecipePosition, placeHolder);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        List<Ingredient> ingredientPlaceholder = new ArrayList<>();
        mIngredientRecyclerView = view.findViewById(R.id.rv_ingredient_list);
        mIngredientLinearLayoutManager = new LinearLayoutManager(getActivity());
        mIngredientRecyclerViewAdapter = new IngredientListRecyclerViewAdapter(getActivity(), ingredientPlaceholder);
        mIngredientRecyclerView.setLayoutManager(mIngredientLinearLayoutManager);
        mIngredientRecyclerView.setAdapter(mIngredientRecyclerViewAdapter);

    }

    private void updateRecyclerView(List<Recipe> recipeList){
        mRecyclerViewAdapter = new RecipeOverviewFragmentRecyclerViewAdapter(getActivity(), mRecipePosition, recipeList.get(mRecipePosition).getSteps());
        mRecyclerView.swapAdapter(mRecyclerViewAdapter, true);

        mIngredientRecyclerViewAdapter = new IngredientListRecyclerViewAdapter(getActivity(), recipeList.get(mRecipePosition).getIngredients());
        mIngredientRecyclerView.swapAdapter(mIngredientRecyclerViewAdapter, true);

    }
}
