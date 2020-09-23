package com.mobile.trainingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobile.trainingapp.R;
import com.mobile.trainingapp.model.Exercice;

import java.util.List;

public class AdapterExercice extends ArrayAdapter<Exercice> {

    public static class ViewHolder {
        private TextView name;
        private TextView repetition;
    }

    public AdapterExercice(@NonNull Context context, @NonNull List<Exercice> objects) {
        super(context, R.layout.list_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Exercice exercice = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.name = convertView.findViewById(R.id.text1);
            viewHolder.repetition = convertView.findViewById(R.id.text2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert exercice != null;
        viewHolder.name.setText(exercice.getEname());
        viewHolder.repetition.setText(String.valueOf(exercice.getErepetition()));

        return convertView;
    }
}
