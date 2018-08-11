package com.example.user.waste_for_best;

public class upload {

    private String mdescriptionText;
    private String mImageUrl;

    public upload()
    {
        //Empty Constructor needed
    }
    public upload(String description,String imageUrl)
    {
        if(description.trim().equals(""))
        {
            description="no name";
        }
        mdescriptionText=description;
        mImageUrl=imageUrl;
    }

    public String getName()
    {
        return mdescriptionText;
    }

    public void setName(String description)
    {
        mdescriptionText=description;
    }
    public String getImageUrl()
    {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        mImageUrl=imageUrl;
    }
}
