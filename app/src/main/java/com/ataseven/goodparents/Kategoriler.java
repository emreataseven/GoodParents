package com.ataseven.goodparents;

public class Kategoriler
{
    private String img_url;

    private int id;

    private String name;

    public String getImg_url ()
    {
        return img_url;
    }

    public void setImg_url (String img_url)
    {
        this.img_url = img_url;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [img_url = "+img_url+", id = "+id+", name = "+name+"]";
    }
}