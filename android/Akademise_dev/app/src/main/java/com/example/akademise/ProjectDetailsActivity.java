package com.example.akademise;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectDetailsActivity extends AppCompatActivity {
    AkademiseApi akademiseApi;
    public static final String MyPEREFERENCES = "MyPrefs";
    public static final String accessToken = "XXXXX";
    private String myToken;

    GetProjects project;
    TextView title;
    TextView summary;
    TextView status;
    TextView milestones;
    TextView requirements;
    TextView tags;
    Button files;
    Button invite;

    TextView privacy;
    TextView collaborators;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

        title = findViewById(R.id.title);

        invite = findViewById(R.id.btnReqInvProject);
        files = findViewById(R.id.btnFilesProject);
        summary = findViewById(R.id.tvAbstractProject);
        status=findViewById(R.id.tvStatusProject);
        milestones=findViewById(R.id.tvMilestoneProject);
        requirements=findViewById(R.id.tvRequirementsProject);
        tags=findViewById(R.id.tvTagsProject);
        privacy=findViewById(R.id.tvPrivacyProjectDetails);
        collaborators = findViewById(R.id.tvCollaborators);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-91-31-85.compute-1.amazonaws.com:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadData();
        akademiseApi = retrofit.create(AkademiseApi.class);

        getData();

        getWholeData(project.getId());

        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openShowProjectFilesActivity();
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 Bundle bundle = new Bundle();
                 bundle.putInt("project_id", project.getId());
                 RequestInvitationFragment frg = new RequestInvitationFragment();
                 frg.setArguments(bundle);
                 getSupportFragmentManager().beginTransaction().replace(R.id.req_and_invitations,
                 frg).commit();
                 **/
                openInvitationActivity();
            }
        });

    }

    private void openShowProjectFilesActivity() {
        Intent intent = new Intent(this, ShowProjectFilesActivity.class);
        intent.putExtra("project_id", project.getId());
        List<ProjectFiles> files = project.getProject_files();
        String allfiles="";
        if(files!=null && files.size()!=0) {
            for (int i = 0; i < files.size() - 1; i++) {
                allfiles += files.get(i).getFile_name() + "<,>";
            }
            if (files.size() != 0) {
                allfiles += files.get(files.size() - 1).getFile_name();
            }
        }
        intent.putExtra("project_files", allfiles);
        startActivity(intent);
    }

    public void openInvitationActivity() {
        Intent intent = new Intent(this, RequestInvitationActivity.class);
        intent.putExtra("project_id", project.getId());
        startActivity(intent);
    }


    private void getData(){
        String test= getIntent().getClass().toString();
        //Toast.makeText(this, test, Toast.LENGTH_LONG).show();
        if(getIntent().hasExtra("project")){
            project = (GetProjects) getIntent().getSerializableExtra("project");
        }
        else{

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(MyPEREFERENCES, Context.MODE_PRIVATE);
        myToken = sharedPreferences.getString(accessToken, "");
        Log.d("mytoken", myToken.toString());

    }

    private void getWholeData(int id){
        Call<List<GetProjects>> call= akademiseApi.getProjects(id, 1,"Bearer "+myToken);
        call.enqueue(new Callback<List<GetProjects>>() {
            @Override
            public void onResponse(Call<List<GetProjects>> call, Response<List<GetProjects>> response) {

                if(!response.isSuccessful()){
                    Log.d("Project", "onResponse: not successful");
                    return;
                }

                List<GetProjects> projects = response.body();
                GetProjects project = projects.get(0);
                title.setText(project.getTitle());
                summary.setText(project.getSummary());
                String[] statusArray = getResources().getStringArray(R.array.status_array);
                status.setText(statusArray[(project.getStatus()%5)]);
                requirements.setText(project.getRequirements());
                String tagList="";
                for(int i=0; i<project.getProject_tags().size(); i++){
                    tagList+= project.getProject_tags().get(i).getTag()+" ";
                }
                tags.setText(tagList);
                if(project.getPrivacy()==0) privacy.setText("Private");
                else privacy.setText("Public");
                String collaboratorList="";
                for(int i=0; i<project.getProject_collaborators().size(); i++){
                    collaboratorList+= project.getProject_collaborators().get(i).getUser().getName()+" "+
                            project.getProject_collaborators().get(i).getUser().getSurname()+", ";
                }
                collaborators.setText(collaboratorList);

            }

            @Override
            public void onFailure(Call<List<GetProjects>> call, Throwable t) {

                Log.d("Project", "onFailure: failed");

            }
        });
    }
}


