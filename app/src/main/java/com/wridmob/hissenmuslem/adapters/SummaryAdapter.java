package com.wridmob.hissenmuslem.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.wridmob.hissenmuslem.R;
import com.wridmob.hissenmuslem.entities.Summary;

import java.util.ArrayList;
import java.util.List;

public class SummaryAdapter  extends BaseAdapter implements Filterable {

    Activity activity;
    List<Summary> data;
    List<Summary> filterData;
    static LayoutInflater inflater = null;
    private ItemFilter mFilter = new ItemFilter();

    public SummaryAdapter(Activity activity, List<Summary> data) {
        this.activity = activity;
        this.data = data;
        this.filterData=data;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filterData.size();
    }

    @Override
    public Summary getItem(int position) {
        return filterData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Summary summary = filterData.get(position);

        if (view == null)
            view = inflater.inflate(R.layout.item_summary, null);

        TextView textAtPage = (TextView) view.findViewById(R.id.text_number);
        textAtPage.setText(""+summary.atPage);

        TextView textSummary = (TextView) view.findViewById(R.id.text_summary);
        textSummary.setText(summary.name);

        return view;
    }

    public Filter getFilter() {
        return mFilter;
    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString();
            FilterResults results = new FilterResults();
            final List<Summary> list = data;
            int count = list.size();
            final ArrayList<Summary> nlist = new ArrayList<Summary>(count);
            Summary filterableString ;
            for (int i = 0; i < count; i++) {
                filterableString = list.get(i);
                if (filterableString.name.contains(filterString)) {
                    nlist.add(filterableString);
                }
            }
            results.values = nlist;
            results.count = nlist.size();
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterData = (ArrayList<Summary>) results.values;
            notifyDataSetChanged();
        }

    }


}