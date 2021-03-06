package com.example.akademise;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AkademiseApi {


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("validate")
    Call<Validation> createValidation(@Body Validation validation, @Header("Authorization") String auth);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("search")
    Call<RootGetProjects> getProjectsSearched(@Query("query") String query,
                                              @Query("type") String type,
                                              @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("search")
    Call<SearchedUsers> getUsersSearched(@Query("query") String query,
                                         @Query("type") String type,
                                         @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("autoComplete/getTitles")
    Call<Result> getTitles(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("autoComplete/getDepartments")
    Call<Result> getDepartments(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("autoComplete/getTags")
    Call<Result> getTags(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("autoComplete/getUniversities")
    Call<Result> getUniversities(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("autoComplete/addTitle")
    Call<Title> addTitle(@Body Title title, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("autoComplete/addDepartment")
    Call<Department> addDepartment(@Body Department department, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("autoComplete/addTag")
    Call<Tag> addTag(@Body Tag tag, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("autoComplete/addUniversity")
    Call<University> addUniversity(@Body University university, @Header("Authorization") String auth);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("post/get/{id}/{type}")
    Call<List<GetProjects>> getProjects(@Path("id") int id, @Path("type") int type, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("post/add")
    Call<Project> createProject(@Body Project project, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @PATCH("post/update/{id}")
    Call<Project> updateAbstract(@Path("id") int id, @Body Summary summary, @Header("Authorization") String auth);

    @POST("auth/signup")
    Call<User> createUser(@Body User user);

    @POST("auth/login")
    Call<User> createUserLogin(@Body User user);

    @POST("auth/jwt")
    Call<Token> sendToken(@Body Token token);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("profile/update")
    Call<PersonalInfo> createAffiliation(@Body PersonalInfo personalInfo, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PATCH("profile/update")
    Call<ProfileUpdate> updateProfile(@Body ProfileUpdate profile, @Header("Authorization") String auth);

    @POST("profile/biography")
    Call<Biography> updateBio(@Body Biography biography, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("profile/{id}")
    Call<Profile> getMyProfile(@Path("id") int id, @Header("Authorization") String auth);

    @POST("profile/up")
    Call <Upvote> addUp(@Body Upvote upvote, @Header("Authorization") String auth);

    @POST("profile/disup")
    Call <Upvote> removeUp(@Body Upvote upvote, @Header("Authorization") String auth);

    @POST("follow/add")
    Call <Upvote> follow(@Body Upvote upvote, @Header("Authorization") String auth);

    @POST("follow/remove")
    Call <Upvote> unfollow(@Body Upvote upvote, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("profile/googlescholar")
    Call<GoogleScholar> getGoogleScholar(@Body Url url, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @DELETE("collab/delete_request/{id}")
    Call<Collab>  deleteReq(@Path("id") int id, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("collab/get_requests")
    Call<List<Request>> getRequests(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("collab/add_collaborator")
    Call<Collab>  addCollab(@Body Collab c, @Header("Authorization") String auth);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("collab/add_request")
    Call<Invitation> addInvitation(@Body Invitation invitation, @Header("Authorization") String auth);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("home/posts")
    Call<Home> getHome(@Header("Authorization") String auth);


    @GET("/follow/followers")
    Call<Follower> getFollowers(@Header("Authorization") String auth);


    @GET("/follow/followings")
    Call<FollowingUsers> getFollowings(@Header("Authorization") String auth);


}

