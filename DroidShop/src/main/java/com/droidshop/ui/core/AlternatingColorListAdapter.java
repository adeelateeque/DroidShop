
package com.droidshop.ui.core;

import java.util.List;

import android.view.LayoutInflater;

import com.droidshop.R.drawable;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

/**
 * List adapter that colors rows in alternating colors
 *
 * @param <V>
 */
public abstract class AlternatingColorListAdapter<V> extends
        SingleTypeAdapter<V> {

    private final int primaryResource;

    private final int secondaryResource;

    /**
     * Create adapter with alternating row colors
     *
     * @param layoutId
     * @param inflater
     * @param items
     */
    public AlternatingColorListAdapter(final int layoutId,
            final LayoutInflater inflater, final List<V> items) {
        this(layoutId, inflater, items, true);
    }

    /**
     * Create adapter with alternating row colors
     *
     * @param layoutId
     * @param inflater
     * @param items
     * @param selectable
     */
    public AlternatingColorListAdapter(final int layoutId,
            LayoutInflater inflater, final List<V> items, boolean selectable) {
        super(inflater, layoutId);

        if (selectable) {
            primaryResource = drawable.table_background_selector;
            secondaryResource = drawable.table_background_alternate_selector;
        } else {
            primaryResource = com.droidshop.R.color.pager_background;
            secondaryResource = com.droidshop.R.color.pager_background_alternate;
        }

        setItems(items);
    }


    @Override
    protected void update(final int position, final V item) {
        if (position % 2 != 0)
            updater.view.setBackgroundResource(primaryResource);
        else
            updater.view.setBackgroundResource(secondaryResource);
    }
}
