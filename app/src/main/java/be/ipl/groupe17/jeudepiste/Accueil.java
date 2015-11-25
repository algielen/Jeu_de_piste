package be.ipl.groupe17.jeudepiste;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Accueil extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Fragment currentFragment;
    private int currentPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            currentFragment = getSupportFragmentManager().getFragment(
                    savedInstanceState, "fragment");
        }

        setContentView(R.layout.activity_accueil);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //on crée les fragments une fois et on les stocke dans un array

        Fragment fragment = currentFragment;
        if (fragment == null || position != currentPosition) {
            switch (position) {
                case 0:
                    fragment = new JeuFragment();
                    currentPosition = 0;
                    break;
                case 1:
                    fragment = new ProgressionFragment();
                    currentPosition = 1;
                    break;
                case 2:
                    fragment = new AProposFragment();
                    currentPosition = 2;
                    break;
                default:
                    fragment = new PlaceholderFragment();
                    currentPosition = -1;
            }
            currentFragment = fragment;
        }
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.accueil, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //source : http://stackoverflow.com/a/17135346
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Save the fragment's instance

        if (currentFragment != null) {
            if (currentFragment.isAdded()) {
                getSupportFragmentManager().putFragment(outState, "fragment", currentFragment);
            }
        }
    }

    //retour de la prise de photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Camera.REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                //TODO : vérifier les données EXIF pour la position ?
                //TODO : mettre à jour la progression
                //Camera.galleryAddPic(model.);
                new AlertDialog.Builder(this).setTitle("Bravo.")
                        .setMessage("Vous avez bien pris une photo")
                        .setIcon(android.R.drawable.ic_dialog_map)
                        .setPositiveButton("Ok", null)
                        .show();
            }
        }
    }


}
