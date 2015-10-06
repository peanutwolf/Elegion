package com.vigursky.ipsumswipe;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigursky on 01.10.2015.
 */

public class MainActivity extends AppCompatActivity {

    private IpsumPageAdapter pageAdapter;
    private List<IpsumFragmentContent> fragmentContents;
    private ViewPager pager;
    private static final String CONTENT_KEY = "CONTENT_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_swiper);

        if(savedInstanceState == null || !savedInstanceState.containsKey(CONTENT_KEY)){
            fragmentContents = getFragments();
        }else{
            fragmentContents = savedInstanceState.getParcelableArrayList(CONTENT_KEY);
        }

        pageAdapter = new IpsumPageAdapter(getSupportFragmentManager());

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);
        pager.setVisibility(View.VISIBLE);
        pager.setPageTransformer(false, pageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete_fragment) {
            fragmentContents.remove(pager.getCurrentItem());
            pageAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CONTENT_KEY, (ArrayList) fragmentContents);
        super.onSaveInstanceState(outState);
    }

    class IpsumPageAdapter extends FragmentStatePagerAdapter implements ViewPager.PageTransformer {
        private List<IpsumFragmentContent> content;
        private static final float MIN_ALPHA_SLIDE = 0.35f;

        public IpsumPageAdapter(FragmentManager fm) {
            super(fm);
            this.content = MainActivity.this.fragmentContents;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            args.putInt("position", position);
            args.putString("content", content.get(position).toString());
            return  Fragment.instantiate(getApplicationContext(), IpsumFragment.class.getName(), args);
        }

        @Override
        public int getCount() {
            return content.size();
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }

        public void deleteItem(int position){
            if(content.size() > 0)
                content.remove(position);
        }

        @Override
        public void transformPage(View view, float position) {
            final float alpha;
            final float translationX;

            if (position < 0 && position > -1) {
                alpha = Math.max(MIN_ALPHA_SLIDE, 1 - Math.abs(position));
                int pageWidth = view.getWidth();
                float translateValue = position * -pageWidth;
                if (translateValue > -pageWidth) {
                    translationX = translateValue;
                } else {
                    translationX = 0;
                }
            } else {
                alpha = 1;
                translationX = 0;
            }

            view.setAlpha(alpha);
            view.setTranslationX(translationX);

        }

    }
    private List<IpsumFragmentContent> getFragments(){
        List<IpsumFragmentContent> pages = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            pages.add(new IpsumFragmentContent("Fragment number = " + i));
        }

        return pages;
    }
}
