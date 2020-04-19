package com.devsanjeev.jobbox.retrofit;

import com.devsanjeev.jobbox.employee.applyApplication.NewApplicationRequest;
import com.devsanjeev.jobbox.employee.employeeLogin.RequestEmployee;
import com.devsanjeev.jobbox.employee.employeeLogin.ResponseEmployee;
import com.devsanjeev.jobbox.employee.employeeProfile.EmployeeProfileResponse;
import com.devsanjeev.jobbox.employee.newFeeds.Application;
import com.devsanjeev.jobbox.employer.createdApplication.CreatedApplicationResponse;
import com.devsanjeev.jobbox.employer.employerLogin.RequestEmployer;
import com.devsanjeev.jobbox.employer.employerProfile.EmployerProfileResponse;
import com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {


    //candidate
    @POST("/candidate/authenticate")
    Call<ResponseEmployee> loginEmployee(@Body RequestEmployee employee);

    @POST("/candidate/register")
    Call<com.devsanjeev.jobbox.employee.employeeRegister.ResponseEmployee> registerEmployee(@Body com.devsanjeev.jobbox.employee.employeeRegister.RequestEmployee employee);

    @POST("/candidate/getCandidateDetails/{id}")
    Call<EmployeeProfileResponse> getEmployeeProfile(@Path("id") String id);

    //applications
    @GET("/apply/all")
    Call<ArrayList<Application>> allApplications();
    @PUT("/apply/apply/{id}")
    Call<Application> applyApplication(@Path("id") String id, @Body NewApplicationRequest request);
    @POST("/apply/appliedApplications/{id}")
    Call<ArrayList<Application>> appliedApplications(@Path("id") String id);
    /*
    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);

    */


    //employer

    @POST("/employer/authenticate")
    Call<com.devsanjeev.jobbox.employer.employerLogin.ResponseEmployer> loginEmployer(@Body RequestEmployer employer);

    @POST("/employer/register")
    Call<ResponseEmployer> registerEmployer(@Body com.devsanjeev.jobbox.employer.employerRegister.RequestEmployer employer);

    @POST("/employer/getEmployerDetails/{id}")
    Call<EmployerProfileResponse> getEmployerProfile(@Path("id") String id);

    @POST("/apply/createdApplications/{id}")
    Call<ArrayList<CreatedApplicationResponse>> createdApplications(@Path("id") String id);
}

