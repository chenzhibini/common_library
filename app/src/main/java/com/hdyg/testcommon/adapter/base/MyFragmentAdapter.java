package com.hdyg.testcommon.adapter.base;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.hdyg.common.common.BaseFragment;

import java.util.List;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    List<BaseFragment> list;

    // 造方法，方便赋值调用
    public MyFragmentAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        this.list = list;
    }

    /**
     * 重新这个方法  防止频繁的销毁视图
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    // 根据Item的位置返回对应位置的Fragment，绑定item和Fragment
    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    // 设置Item的数量
    @Override
    public int getCount() {
        return list.size();
    }

//    private int mChildCount = 0;
//
//    @Override
//    public void notifyDataSetChanged() {
//        mChildCount = getCount();
//        super.notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemPosition(Object object) {
//        if (mChildCount > 0) {
//            // 这里利用判断执行若干次不缓存，刷新
//            mChildCount--;
//            // 返回这个是强制ViewPager不缓存，每次滑动都刷新视图
//            return POSITION_NONE;
//        }
//        // 这个则是缓存不刷新视图
//        return super.getItemPosition(object);
//    }
}