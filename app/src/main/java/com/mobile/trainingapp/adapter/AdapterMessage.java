package com.mobile.trainingapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobile.trainingapp.R;
import com.mobile.trainingapp.model.Message;
import com.mobile.trainingapp.model.Workout;

import java.util.List;

public class AdapterMessage extends ArrayAdapter<Message> {

    public static class ViewHolder {
        private TextView user;
        private TextView workout;
    }

    public AdapterMessage(@NonNull Context context, @NonNull List<Message> objects) {
        super(context, R.layout.list_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.user = convertView.findViewById(R.id.text1);
            viewHolder.workout = convertView.findViewById(R.id.text2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert message != null;
        viewHolder.user.setText(message.getNomeEnviou());
        viewHolder.workout.setText(message.getNomeEnviou() + " convidou você para realizar o treino " + message.getWorkout());

        return convertView;
    }
}
