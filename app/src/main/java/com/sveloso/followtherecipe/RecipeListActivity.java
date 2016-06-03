package com.sveloso.followtherecipe;

import android.support.v4.app.Fragment;

/**
 * Created by Veloso on 5/31/2016.
 */
public class RecipeListActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }
}
