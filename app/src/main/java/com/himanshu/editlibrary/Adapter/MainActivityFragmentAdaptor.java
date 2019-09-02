package com.himanshu.editlibrary.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.himanshu.editlibrary.Fragment.MainCamera;
import com.himanshu.editlibrary.Fragment.TextFragment;


public class MainActivityFragmentAdaptor extends FragmentPagerAdapter {

    private static int FRAGMENT_NUM = 2;

    public MainActivityFragmentAdaptor(FragmentManager fm, int num_of_tab) {
        super(fm);
        this.FRAGMENT_NUM = num_of_tab;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return MainCamera.newInstance();

            case 1:
                return TextFragment.newInstance();

            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_NUM;
    }
}
