package young.project.android.com.bakeaide.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import young.project.android.com.bakeaide.R;

public class MainActivity extends AppCompatActivity implements FragmentChangeListener{

    public static final String EXTRA_RECIPE_ID_KEY = "extra_recipe";
    public static final String EXTRA_STEP_ID_KEY = "extra_step_id";
    public static final String RECIPE_LIST_FRAGMENT_TAG = "recipe_list_fragment";
    public static final String RECIPE_OVERVIEW_FRAGMENT_TAG = "recipe_overview";
    public static final String STEP_DETAIL_FRAGMENT_TAG = "step_detail";
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTablet = getResources().getBoolean(R.bool.isTablet);
        Toolbar mToolbar = findViewById(R.id.tb_main_activity);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!isTablet) {

            if (findViewById(R.id.portrait_fragment_container) != null) {
                if (savedInstanceState != null) {
                    return;
                }

                RecipeListFragment recipeListFragment = new RecipeListFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.portrait_fragment_container, recipeListFragment, RECIPE_LIST_FRAGMENT_TAG)
                        .commit();
            }
        } else{
            if(findViewById(R.id.tablet_left_fragment_container) != null){
                if(savedInstanceState != null){
                    return;
                }

                RecipeListFragment recipeListFragment = new RecipeListFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.tablet_left_fragment_container, recipeListFragment, RECIPE_LIST_FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    @Override
    public void replaceFragment(Fragment fragment, int recipePosition, String tag){
        Bundle args = new Bundle();
        args.putInt(EXTRA_RECIPE_ID_KEY, recipePosition);
        fragment.setArguments(args);

        if(!isTablet) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.portrait_fragment_container, fragment)
                    .addToBackStack(tag)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tablet_left_fragment_container, fragment)
                    .addToBackStack(tag)
                    .commit();
        }
    }


    @Override
    public void replaceFragment(Fragment fragment, int recipePosition, int stepPosition, String tag){
        Bundle args = new Bundle();
        args.putInt(EXTRA_RECIPE_ID_KEY, recipePosition);
        args.putInt(EXTRA_STEP_ID_KEY, stepPosition);
        fragment.setArguments(args);
        if(!isTablet) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.portrait_fragment_container, fragment)
                    .addToBackStack(tag)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.tablet_right_fragment_container, fragment)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.d("TEST", "BACK STACK NUMBER: " + getSupportFragmentManager().getBackStackEntryCount());
        super.onBackPressed();
        if(!(getResources().getBoolean(R.bool.isTablet))) {
            getSupportFragmentManager().popBackStack(RECIPE_OVERVIEW_FRAGMENT_TAG, 0);
        } else {
            getSupportFragmentManager().popBackStack(RECIPE_OVERVIEW_FRAGMENT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

    }
}
