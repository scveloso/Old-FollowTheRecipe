package com.sveloso.followtherecipe;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.util.UUID;

import utility.PictureUtils;

/**
 * Created by Veloso on 5/31/2016.
 */
public class RecipeFragment extends Fragment{

    private static final int REQUEST_PHOTO = 0;

    private static final String ARG_RECIPE_ID = "recipe_id";

    private Recipe mRecipe;
    private EditText mTitleField;
    private ImageView mPhotoView;
    private ImageButton mPhotoButton;
    private File mPhotoFile;
    private EditText mIngredientsField;
    private EditText mDirectionsField;

    public static RecipeFragment newInstance(UUID recipeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE_ID, recipeId);

        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID recipeId = (UUID) getArguments().getSerializable(ARG_RECIPE_ID);
        mRecipe = RecipeManager.get(getActivity()).getRecipe(recipeId);
        mPhotoFile = RecipeManager.get(getActivity()).getPhotoFile(mRecipe);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPause() {
        super.onPause();

        RecipeManager.get(getActivity())
                .updateRecipe(mRecipe);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_recipe, container, false);

        mTitleField = (EditText) v.findViewById(R.id.recipe_title);
        mTitleField.setText(mRecipe.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRecipe.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // nothing
            }
        });

        mIngredientsField = (EditText) v.findViewById(R.id.recipe_ingredients);
        mIngredientsField.setText(mRecipe.getIngredients());
        mIngredientsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRecipe.setIngredients(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // nothing
            }
        });

        mDirectionsField = (EditText) v.findViewById(R.id.recipe_directions);
        mDirectionsField.setText(mRecipe.getDirections());
        mDirectionsField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mRecipe.setDirections(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // nothing
            }
        });

        mPhotoButton = (ImageButton) v.findViewById(R.id.recipe_camera);

        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getActivity().getPackageManager();
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),
                        "Rotate phone 90 degrees to the left when taking photo!", Toast.LENGTH_SHORT)
                        .show();
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.recipe_photo);
        updatePhoto();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Similar to inflating an activity/fragment
        inflater.inflate(R.menu.fragment_recipe, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_delete_recipe:
                UUID recipeId = mRecipe.getId();
                RecipeManager.get(getActivity()).deleteRecipe(recipeId);
                this.getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != Activity.RESULT_OK) {
            return;
        }

        else if (requestCode == REQUEST_PHOTO) {
            updatePhoto();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updatePhoto();
    }

    private void updatePhoto() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap (
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }
}
