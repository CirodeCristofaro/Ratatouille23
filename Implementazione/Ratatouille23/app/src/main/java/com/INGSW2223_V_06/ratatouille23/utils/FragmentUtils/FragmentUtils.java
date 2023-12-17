package com.INGSW2223_V_06.ratatouille23.utils.FragmentUtils;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.INGSW2223_V_06.ratatouille23.R;

public class FragmentUtils {
    private static String TAG = "FragmentUtils";

    public static void changeFragment(Fragment fragmentToChange, Fragment attuale) {
        Log.i(TAG, "changeFragment: ");
        FragmentManager fragmentManager = attuale.requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutActivityMain, fragmentToChange);
        fragmentTransaction.commit();
    }
}
