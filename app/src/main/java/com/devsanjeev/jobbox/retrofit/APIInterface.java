package com.devsanjeev.jobbox.retrofit;

import com.devsanjeev.jobbox.employee.employeeLogin.RequestEmployee;
import com.devsanjeev.jobbox.employee.employeeLogin.ResponseEmployee;
import com.devsanjeev.jobbox.employer.employerLogin.RequestEmployer;
import com.devsanjeev.jobbox.employer.employerRegister.ResponseEmployer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {


    //candidate
    @POST("/employee/authenticate")
    Call<ResponseEmployee> loginEmployee(@Body RequestEmployee employee);

    @POST("/employee/register")
    Call<com.devsanjeev.jobbox.employee.employeeRegister.ResponseEmployee> registerEmployee(@Body com.devsanjeev.jobbox.employee.employeeRegister.RequestEmployee employee);

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
}

