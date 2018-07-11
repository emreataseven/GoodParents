package com.ataseven.goodparents;

import android.os.Parcel;
import android.os.Parcelable;

public class Images implements Parcelable
{

    public Images() {

    }

    public Images(String img_url, int featured) {
        this.img_url = img_url;
        this.featured = featured;
    }

    private String img_url;

    private int featured;

    protected Images(Parcel in) {
        img_url = in.readString();
        featured = in.readInt();
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel in) {
            return new Images(in);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };

    public String getImg_url ()
    {
        return img_url;
    }

    public void setImg_url (String img_url)
    {
        this.img_url = img_url;
    }

    public int getFeatured ()
    {
        return featured;
    }

    public void setFeatured (int featured)
    {
        this.featured = featured;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [img_url = "+img_url+", featured = "+featured+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(img_url);
        parcel.writeInt(featured);
    }
}