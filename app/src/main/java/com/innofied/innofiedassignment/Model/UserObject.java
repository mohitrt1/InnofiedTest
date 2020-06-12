package com.innofied.innofiedassignment.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohit on 9/23/2019.
 */
public class UserObject {
    @SerializedName("id")
    private int id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("email")
    private String emailId;
    @SerializedName("avatar")
    private String imgPath;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getImgPath() {
        return imgPath;
    }
}
