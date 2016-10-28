package com.liteng.topbar.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NestedScrollView mNestedScrollView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;
    private TextView mTvTitle;
    private Toolbar mToolbar;

    private String[] mTitles = {"西游记", "西游记", "西游记"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTvTitle = (TextView) mToolbar.findViewById(R.id.tv_title);

        setSupportActionBar(mToolbar);

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mNestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        //设置 NestedScrollView 的内容是否拉伸填充整个视图，
        //这个设置是必须的，否者我们在里面设置的ViewPager会不可见
        mNestedScrollView.setFillViewport(true);

        mTabLayout.setupWithViewPager(mViewPager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRangle = appBarLayout.getTotalScrollRange();
                //初始verticalOffset为0，不能参与计算。
                if (verticalOffset == 0) {
                    mTvTitle.setAlpha(0.0f);
                } else {
                    //保留一位小数
                    float alpha = Math.abs(Math.round(1.0f * verticalOffset / scrollRangle) * 10) / 10;
                    mTvTitle.setAlpha(alpha);
                }
            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MyFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    public static class MyFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            String string = getString(R.string.text);
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                strings.add(string);
            }
            RecyclerView recyclerView = new RecyclerView(getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new MyListAdapter(strings));
            return recyclerView;
        }


        class MyListAdapter extends RecyclerView.Adapter {
            private List<String> mStrings;

            public MyListAdapter(List<String> strings) {
                this.mStrings = strings;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item, parent, false);
                return new MyListViewHolder(convertView);
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                MyListViewHolder viewHolder = (MyListViewHolder) holder;
                viewHolder.mTextView.setText(mStrings.get(position));
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        }

        class MyListViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;

            public MyListViewHolder(View itemView) {
                super(itemView);
                mTextView = (TextView) itemView.findViewById(R.id.tv_content);
            }
        }
    }


}
