package com.android.lixiang.liangwei.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.lixiang.liangwei.R;
import com.android.lixiang.liangwei.ui.fragment.area3.bak.Species;

import java.util.List;

public class SelectPropertiesAdapter extends ArrayAdapter<Species> {

    private int resourceId;
    private int selectedItem;

    public SelectPropertiesAdapter(Context context, int textViewResourceId, List<Species> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    public void setSelectedItem(int selectedItem) {
        this.selectedItem = selectedItem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Species species = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);

        TextView speciesname = (TextView) view.findViewById(R.id.species_name);
        AppCompatImageView mCheckIV = view.findViewById(R.id.mCheckIV);
        speciesname.setText(species.getSpecies_name());
        if (position == selectedItem) {
            mCheckIV.setVisibility(View.VISIBLE);
        }
        return view;

    }
}
