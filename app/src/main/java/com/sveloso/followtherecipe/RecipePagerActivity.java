package com.sveloso.followtherecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by Veloso on 6/1/2016.
 */
public class RecipePagerActivity extends AppCompatActivity {
    private static final String EXTRA_RECIPE_ID =
            "com.sveloso.followtherecipe.recipe_id";

    private ViewPager mViewPager;
    private List<Recipe> mRecipes;

    public static Intent newIntent(Context packageContext, UUID recipeId) {
        Intent intent = new Intent(packageContext, RecipePagerActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID, recipeId);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_pager);

        UUID recipeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_RECIPE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_recipe_pager_view_pager);

        mRecipes = RecipeManager.get(this).getRecipes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Recipe recipe = mRecipes.get(position);
                return RecipeFragment.newInstance(recipe.getId());
            }

            @Override
            public int getCount() {
                return mRecipes.size();
            }
        });

        // Cannot simply use getRecipe because ViewPager needs the position of
        // the recipe rather than the recipe itself
        for (int i = 0; i < mRecipes.size(); i++) {
            if (mRecipes.get(i).getId().equals(recipeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
