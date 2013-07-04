package com.droidshop.ui;

import android.view.LayoutInflater;

import com.droidshop.R;
import com.droidshop.model.FeedItem;
import com.droidshop.ui.core.AlternatingColorListAdapter;

import java.util.List;

public class FeedListAdapter extends AlternatingColorListAdapter<FeedItem> {
    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public FeedListAdapter(LayoutInflater inflater, List<FeedItem> items,
                               boolean selectable) {
        super(R.layout.news_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public FeedListAdapter(LayoutInflater inflater, List<FeedItem> items) {
        super(R.layout.news_list_item, inflater, items);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.tv_title, R.id.tv_summary,
                R.id.tv_date };
    }

    @Override
    protected void update(int position, FeedItem item) {
        super.update(position, item);

        setText(0, item.getTitle());
        setText(1, item.getContent());
        //setNumber(R.id.tv_date, item.getCreatedAt());
    }
}
