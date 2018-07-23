package young.project.android.com.bakeaide.ui;

import android.support.v4.app.Fragment;

interface FragmentChangeListener {
    void replaceFragment(Fragment fragment, int recipeId, String tag);
    void replaceFragment(Fragment fragment, int recipeId, int stepId, String tag);
}
