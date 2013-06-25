package com.droidshop;

import java.util.ArrayList;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class MergeAdapter extends BaseAdapter implements SectionIndexer {
    protected ArrayList<ListAdapter> pieces = new ArrayList<ListAdapter>();
    protected String noItemsText;

    public MergeAdapter() {
        super();
    }

    public void addAdapter(ListAdapter adapter) {
        pieces.add(adapter);
        adapter.registerDataSetObserver(new CascadeDataSetObserver());
    }

    public Object getItem(int position) {
        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                return (piece.getItem(position));
            }

            position -= size;
        }

        return (null);
    }

    public void setNoItemsText(String text){
        noItemsText = text;
    }

    public ListAdapter getAdapter(int position) {
        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                return (piece);
            }

            position -= size;
        }

        return (null);
    }

    public int getCount() {
        int total = 0;

        for (ListAdapter piece : pieces) {
            total += piece.getCount();
        }

        if(total == 0 && noItemsText != null){
            total = 1;
        }

        return (total);
    }

    @Override
    public int getViewTypeCount() {
        int total = 0;

        for (ListAdapter piece : pieces) {
            total += piece.getViewTypeCount();
        }

        return (Math.max(total, 1)); // needed for setListAdapter() before
                                        // content add'
    }

    @Override
    public int getItemViewType(int position) {
        int typeOffset = 0;
        int result = -1;

        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                result = typeOffset + piece.getItemViewType(position);
                break;
            }

            position -= size;
            typeOffset += piece.getViewTypeCount();
        }

        return (result);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return (false);
    }

    @Override
    public boolean isEnabled(int position) {
        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                return (piece.isEnabled(position));
            }

            position -= size;
        }

        return (false);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {

                return (piece.getView(position, convertView, parent));
            }

            position -= size;
        }

        if(noItemsText != null){
            TextView text = new TextView(parent.getContext());
            text.setText(noItemsText);
            return text;
        }

        return (null);
    }

    public long getItemId(int position) {
        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                return (piece.getItemId(position));
            }

            position -= size;
        }

        return (-1);
    }

    public int getPositionForSection(int section) {
        int position = 0;

        for (ListAdapter piece : pieces) {
            if (piece instanceof SectionIndexer) {
                Object[] sections = ((SectionIndexer) piece).getSections();
                int numSections = 0;

                if (sections != null) {
                    numSections = sections.length;
                }

                if (section < numSections) {
                    return (position + ((SectionIndexer) piece)
                            .getPositionForSection(section));
                } else if (sections != null) {
                    section -= numSections;
                }
            }

            position += piece.getCount();
        }

        return (0);
    }

    public int getSectionForPosition(int position) {
        int section = 0;

        for (ListAdapter piece : pieces) {
            int size = piece.getCount();

            if (position < size) {
                if (piece instanceof SectionIndexer) {
                    return (section + ((SectionIndexer) piece)
                            .getSectionForPosition(position));
                }

                return (0);
            } else {
                if (piece instanceof SectionIndexer) {
                    Object[] sections = ((SectionIndexer) piece).getSections();

                    if (sections != null) {
                        section += sections.length;
                    }
                }
            }

            position -= size;
        }

        return (0);
    }

    public Object[] getSections() {
        ArrayList<Object> sections = new ArrayList<Object>();

        for (ListAdapter piece : pieces) {
            if (piece instanceof SectionIndexer) {
                Object[] curSections = ((SectionIndexer) piece).getSections();

                if (curSections != null) {
                    for (Object section : curSections) {
                        sections.add(section);
                    }
                }
            }
        }

        if (sections.size() == 0) {
            return (null);
        }

        return (sections.toArray(new Object[0]));
    }

    private class CascadeDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }

        @Override
        public void onInvalidated() {
            notifyDataSetInvalidated();
        }
    }
}
