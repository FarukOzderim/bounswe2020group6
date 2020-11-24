package com.example.akademise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AkademiseApi {


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("validate")
    Call<Validation> createValidation(@Body Validation validation, @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("search")
    Call<Projects> getProjectsSearched(@Query("query") String query,
                                            @Query("type") String type,
                                            @Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("post/get/{id}")
    Call<List<Project>> getProjects(@Path("id") int id, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("post/add")
    Call<Project> createProject(@Body Project project, @Header("Authorization") String auth);

    @POST("auth/signup")
    Call<User> createUser(@Body User user);

    @POST("auth/login")
    Call<User> createUserLogin(@Body User user);

    @POST("auth/jwt")
    Call<Token> sendToken(@Body Token token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("profile/update")
    Call<Affiliation> createAffiliation(@Body Affiliation affiliation, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("profile/{id}")
    Call<Affiliation> getMyProfile(@Path("id") int id, @Header("Authorization") String auth);

}
