package com.ataseven.goodparents;

import android.os.Parcel;
import android.os.Parcelable;

public class Owners implements Parcelable
{
    private int id;

    private String address;

    private String name;

    private String about;

    private String photo;
    public Owners() {

    }
    protected Owners(Parcel in) {
        id = in.readInt();
        address = in.readString();
        name = in.readString();
        about = in.readString();
        photo = in.readString();
    }

    public static final Creator<Owners> CREATOR = new Creator<Owners>() {
        @Override
        public Owners createFromParcel(Parcel in) {
            return new Owners(in);
        }

        @Override
        public Owners[] newArray(int size) {
            return new Owners[size];
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

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getAbout ()
    {
        return about;
    }

    public void setAbout (String about)
    {
        this.about = about;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public void setPhoto (String photo)
    {
        this.photo = photo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", address = "+address+", name = "+name+", about = "+about+", photo = "+photo+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(address);
        parcel.writeString(name);
        parcel.writeString(about);
        parcel.writeString(photo);
    }
}