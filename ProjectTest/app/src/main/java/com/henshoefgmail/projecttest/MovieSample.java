package com.henshoefgmail.projecttest;



/**
 * Created by Ss12Dark on 3/9/2018.
 */

public class MovieSample {

    private int _id;//the id if for the my own use and not the real id- its beed a check key to see if the
                // movie already existed or i made a new one
    private String subject;//title
    private String body;
    private String url;
    private int orderNumber;//the id number of the movie by the API i got
    private int watched=0;//the times you watched the movie- always start at 0

    public MovieSample(){}

    public MovieSample(int id, String subject , String body , String url){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
    }


    public MovieSample(String subject , String body , String url){
        this.subject = subject;
        this.body = body;
        this.url = url;
    }

    public MovieSample(String subject , String body){
        this.subject = subject;
        this.body = body;

    }

    public MovieSample(String subject){
        this.subject = subject;
    }

    public MovieSample(int id, String subject , String body , String url, int orderNumber){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
    }

    public MovieSample(int id, String subject , String body , String url, int orderNumber, int watched){
        this._id = id;
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
        this.watched=watched;
    }

    public MovieSample(String subject , String body , String url, int orderNumber){
        this.subject = subject;
        this.body = body;
        this.url = url;
        this.orderNumber = orderNumber;
    }

    public MovieSample(String subject , String body, int orderNumber){
        this.subject = subject;
        this.body = body;
        this.orderNumber = orderNumber;
    }

    public MovieSample(String subject, int orderNumber){

        this.subject = subject;
        this.orderNumber = orderNumber;

    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
