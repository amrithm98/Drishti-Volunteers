package com.cse.amrith.drishti17volunteers.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amrith on 3/11/17.
 */

public class Student {
    public int score;
    @SerializedName("normalizedScore")
    public int gunt_score;
    public String name,uid,phone,picture,collegeId,college,id;
    public enum Accomodation{none,male,female};
    public boolean registered;
}
