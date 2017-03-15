package com.cse.amrith.drishti17volunteers.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amrith on 3/15/17.
 */

public class EventModel {
    public int prize1, prize2, prize3, maxPerGroup, regFee;
    @SerializedName("id")
    public int server_id;
    transient public int id;
    public String name, description,format,category, image, day, time;
    public String contactName1, contactPhone1, contactEmail1, contactName2, contactPhone2, contactEmail2;
    public String adminId;
    public boolean isWorkshop, group;
    public EventModel() {

    }
}
