package com.hdyg.testcommon.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.hdyg.testcommon.mvp.view.base.BaseActivity;
import com.hdyg.testcommon.mvp.view.base.BaseFragment;

import java.util.List;

/**
 * @author CZB
 * @describe viewpager2适配器
 * @time 2021/1/6 17:37
 */
public class ViewPager2Adapter extends FragmentStateAdapter {

    private List<BaseFragment> mFragments;

    public ViewPager2Adapter(@NonNull BaseActivity activity, List<BaseFragment> fragments) {
        super(activity);
        this.mFragments = fragments;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
