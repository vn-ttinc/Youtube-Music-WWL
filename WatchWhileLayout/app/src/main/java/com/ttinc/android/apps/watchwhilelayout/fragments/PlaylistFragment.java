package com.ttinc.android.apps.watchwhilelayout.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ttinc.android.apps.watchwhilelayout.FragmentHost;
import com.ttinc.android.apps.watchwhilelayout.R;
import com.ttinc.android.apps.watchwhilelayout.utils.Dataset;

/**
 * Created by thangn on 3/1/17.
 */

public class PlaylistFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CustomAdapter mAdapter;
    private TextView mTitleView;
    private TextView mSubTitleView;
    private FragmentHost mFragmentHost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.playlist_fragment, container, false);
        this.mTitleView = (TextView) rootView.findViewById(R.id.li_title);
        this.mSubTitleView = (TextView) rootView.findViewById(R.id.li_subtitle);
        this.mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        this.mLayoutManager = new LinearLayoutManager(getActivity());
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        this.mRecyclerView.scrollToPosition(0);

        this.mAdapter = new CustomAdapter(Dataset.datasetItems);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        this.mFragmentHost = (FragmentHost) activity;
    }

    private void onItemClicked(Dataset.DatasetItem item) {
        if (this.mFragmentHost != null) {
            this.mFragmentHost.goToDetail(item);
        }
    }

    public void updateItem(Dataset.DatasetItem item) {
        this.mTitleView.setText(item.title);
        this.mSubTitleView.setText(item.subtitle);
    }

    private class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Dataset.DatasetItem[] mDataSet;

        public CustomAdapter(Dataset.DatasetItem[] datasetItems) {
            this.mDataSet = datasetItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
            return new CustomAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).getTitleView().setText(this.mDataSet[position].title);
            ((ViewHolder) holder).getSubTitleView().setText(this.mDataSet[position].subtitle);
        }

        @Override
        public int getItemCount() {
            return this.mDataSet.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView titleView;
            private final TextView subtitleView;

            public ViewHolder(View v) {
                super(v);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlaylistFragment.this.onItemClicked(CustomAdapter.this.mDataSet[getAdapterPosition()]);
                    }
                });
                titleView = (TextView) v.findViewById(R.id.li_title);
                subtitleView = (TextView) v.findViewById(R.id.li_subtitle);
            }

            public TextView getTitleView() {
                return titleView;
            }

            public TextView getSubTitleView() {
                return subtitleView;
            }
        }
    }
}
