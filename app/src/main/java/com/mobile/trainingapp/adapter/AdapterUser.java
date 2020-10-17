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
import com.mobile.trainingapp.model.User;

import java.util.List;

public class AdapterUser extends ArrayAdapter<User> {

    private List<User> users;

    public static class ViewHolder{
        private TextView name;
    }

    public AdapterUser(@NonNull Context context, @NonNull List<User> objects) {
        super(context, android.R.layout.simple_list_item_1,objects);
        this.users = objects;
    }

    public List<User> getUsers() {
        return users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder.name = convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert user != null;
        viewHolder.name.setText(user.getUname());

        return convertView;
    }
}
