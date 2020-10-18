package com.mobile.trainingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.trainingapp.R;
import com.mobile.trainingapp.TrainingActivity;
import com.mobile.trainingapp.model.Workout;

import java.util.List;

public class AdapterWorkout extends  RecyclerView.Adapter<AdapterWorkout.ViewHolder> {

    private Context mContext;
    private List<Workout> mWorkouts;

    public AdapterWorkout(Context mContext, List<Workout> mWorkouts){
        this.mWorkouts= mWorkouts;
        this.mContext= mContext;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.workout_item, parent, false);
        return new AdapterWorkout.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Workout workout = mWorkouts.get(position);
        holder.username.setText("Treino de " + workout.getTid());
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TrainingActivity.class);
                intent.putExtra("exercices", workout.getExercices());
                mContext.startActivity(intent);
            }
        });
         */
    }


    @Override
    public int getItemCount() {
        return mWorkouts.size();
    }

    public List<Workout> getmWorkouts() { return mWorkouts; }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image= itemView.findViewById(R.id.profile_image);
        }
    }
}