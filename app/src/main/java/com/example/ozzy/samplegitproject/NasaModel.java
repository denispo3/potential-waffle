package com.example.ozzy.samplegitproject;

/**
 * Author: Ozzy
 * Created by: ModelGenerator on 18.05.2016
 */
public class NasaModel {
    public String copyright;
    public String date;
    public String explanation;
    public String hdurl;
    public String mediaType;
    public String serviceVersion;
    public String title;
    public String url;

    @Override
    public String toString() {
        return "NasaModel{" +
                "copyright='" + copyright + '\'' +
                ", date='" + date + '\'' +
                ", explanation='" + explanation + '\'' +
                ", hdurl='" + hdurl + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}