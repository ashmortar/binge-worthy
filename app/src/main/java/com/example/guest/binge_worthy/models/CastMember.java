package com.example.guest.binge_worthy.models;

import org.parceler.Parcel;

/**
 * Created by Guest on 3/21/18.
 */

@Parcel
public class CastMember {
    int cast_id;
    String character;
    String credit_id;
    int gender;
    int id;
    String name;
    String profile_path;

    public CastMember() {}

    public CastMember(int cast_id, String character, String credit_id, int gender, int id, String name, String profile_path) {
        this.cast_id = cast_id;
        this.character = character;
        this.credit_id = credit_id;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.profile_path = "https://image.tmdb.org/t/p/original" + profile_path;
    }

    public int getCast_id() {
        return cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public int getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfile_path() {
        return profile_path;
    }
}
