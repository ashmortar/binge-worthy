package com.example.guest.binge_worthy.models;

import org.parceler.Parcel;

@Parcel
public class CrewMember {
    String credit_id;
    String department;
    int gender;
    int id;
    String job;
    String name;
    String profile_path;

    public CrewMember() {}

    public CrewMember(String credit_id, String department, int gender, int id, String job, String name, String profile_path) {
        this.credit_id = credit_id;
        this.department = department;
        this.gender = gender;
        this.id = id;
        this.job = job;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public int getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getJob() {
        return job;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }
}
