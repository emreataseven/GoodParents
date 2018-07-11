package com.ataseven.goodparents;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Activity implements Parcelable
{
    public Activity() {
        this.locImages = new ArrayList<Images>();
        this.locCategories = new ArrayList<Categories>();
        this.locOwners = new ArrayList<Owners>();
    }

    private int id;

    private int quota;

    private String price;

    private String address;

    private int activity_owner_id;

    private String description;

    private String name;

    private ArrayList<Images> locImages;

    private ArrayList<Categories> locCategories;

    private String longitude;

    private String latitude;

    private String notes;

    private ArrayList<Owners> locOwners;

    protected Activity(Parcel in) {
        id = in.readInt();
        quota = in.readInt();
        price = in.readString();
        address = in.readString();
        activity_owner_id = in.readInt();
        description = in.readString();
        name = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        notes = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(quota);
        dest.writeString(price);
        dest.writeString(address);
        dest.writeInt(activity_owner_id);
        dest.writeString(description);
        dest.writeString(name);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(notes);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel in) {
            return new Activity(in);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public int getQuota ()
    {
        return quota;
    }

    public void setQuota (int quota)
    {
        this.quota = quota;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public int getActivity_owner_id ()
    {
        return activity_owner_id;
    }

    public void setActivity_owner_id (int activity_owner_id) {this.activity_owner_id = activity_owner_id;}

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public ArrayList<Images> getImages ()
    {
        return locImages;
    }

    public void setImages (Images images)
    {
        this.locImages.add(images);
    }

    public ArrayList<Categories> getCategories ()
    {
        return locCategories;
    }

    public void setCategories (Categories categories)
    {
        this.locCategories.add(categories);
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getNotes ()
    {
        return notes;
    }

    public void setNotes (String notes)
    {
        this.notes = notes;
    }

    public ArrayList<Owners> getOwners ()
    {
        return locOwners;
    }

    public void setOwners (Owners owners)
    {
        this.locOwners.add(owners);
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", quota = "+quota+", price = "+price+", address = "+address+", activity_owner_id = "+activity_owner_id+", description = "+description+", name = "+name+", longitude = "+longitude+", latitude = "+latitude+", notes = "+notes+"]";
    }
}