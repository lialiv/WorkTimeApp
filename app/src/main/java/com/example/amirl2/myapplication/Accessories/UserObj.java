package com.example.amirl2.myapplication.Accessories;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AmirL2 on 8/8/2017.
 */

public class UserObj implements Parcelable

{
    public int id;
    public String firstName;
    public String lastName;
    public String username;
    public String password;

    public UserObj(int id, String firstName, String lastName, String username, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public UserObj(String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public UserObj() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName= firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName= lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // For Parcelable
    public UserObj(Parcel in) {
            this.id = in.readInt();
            this.firstName = in.readString();
            this.lastName = in.readString();
            this.username = in.readString();
            this.password =  in.readString();
        }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.firstName);
        parcel.writeString(this.lastName);
        parcel.writeString(this.username);
        parcel.writeString(this.password);

    }


    public static final Parcelable.Creator<UserObj> CREATOR
            = new Parcelable.Creator<UserObj>() {
        public UserObj createFromParcel(Parcel in) {
            return new UserObj(in);
        }

        public UserObj[] newArray(int size) {
            return new UserObj[size];
        }
    };


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", first name='" + firstName + '\'' +
                ", last name='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}



