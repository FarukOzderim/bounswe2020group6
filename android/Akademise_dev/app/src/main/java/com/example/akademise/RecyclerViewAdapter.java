package com.example.akademise;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static final String MyIDPEREFERENCES = "MyIDPrefs";
    private int myId;
    public static final String accessID = "XXXXXID";

    List<GetProjects> projects;
    SearchedUsers searchedUsers;
    Context context;
    List<Integer> userId=new ArrayList<>();

    public RecyclerViewAdapter(Context ct, List<GetProjects> prj, SearchedUsers srchdUsr) {
        context = ct;
        projects = prj;
        searchedUsers = srchdUsr;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        loadIDData();
        if(position<projects.size()) {
            holder.title.setText(projects.get(position).getTitle());
            holder._abstract.setText(projects.get(position).getSummary());
            holder.imageView.setImageResource(R.drawable.ic_folder_foreground);
            userId.add(projects.get(position).getUserId());
        }
        else{
            String person= searchedUsers.getUsers().get(position-projects.size()).getName()+" "+
                    searchedUsers.getUsers().get(position-projects.size()).getSurname();
            holder.title.setText(person);
            holder._abstract.setText(searchedUsers.getUsers().get(position-projects.size()).getTitle());
            holder.imageView.setImageResource(R.drawable.ic_profile_foreground);
            userId.add(searchedUsers.getUsers().get(position-projects.size()).getId());
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if(position<projects.size()) {
                    if (userId.get(position) == myId) {
                        intent = new Intent(context, ProjectDetailsActivity.class);


                    } else {
                        intent = new Intent(context, ProjectDetailsUserActivity.class);
                    }

                    //Toast.makeText(context, String.valueOf(userId.get(position))+" "+String.valueOf(position), Toast.LENGTH_LONG).show();
                    intent.putExtra("project", projects.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }
                else{
                    intent = new Intent(context, ProfileActivity.class);
                    if (userId.get(position) == myId) {
                        intent.putExtra("me",1);
                        //Toast.makeText(context, String.valueOf(userId.get(position)), Toast.LENGTH_LONG).show();


                    } else {
                        //Toast.makeText(context, String.valueOf(userId.get(position))+" "+String.valueOf(position-projects.size()), Toast.LENGTH_LONG).show();
                        intent.putExtra("otherUser",0);
                    }
                    intent.putExtra("user", searchedUsers.getUsers().get(position-projects.size()));
                    context.startActivity(intent);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        if(searchedUsers!=null)
            return projects.size() +searchedUsers.getUsers().size();
        else {
            if(projects!=null)
            return projects.size();
            else return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView _abstract;
        ImageView imageView;
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            _abstract = itemView.findViewById(R.id.tv_abstract);
            imageView = itemView.findViewById(R.id.iv_project);
            mView = itemView;
        }

    }

    private void loadIDData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MyIDPEREFERENCES, MODE_PRIVATE);
        myId = sharedPreferences.getInt(accessID, 0);

    }
}
