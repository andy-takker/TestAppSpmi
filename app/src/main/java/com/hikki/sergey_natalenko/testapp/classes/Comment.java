package com.hikki.sergey_natalenko.testapp.classes;

import java.util.Date;
import java.util.UUID;

public class Comment {
    private Date mDate;
    private UUID mId;
    private UUID mAuthorId;
    private UUID mObjectId;
    private String mText;

    public Comment(UUID authorId, UUID id, UUID object, String text, Date date){
        mId = id;
        mAuthorId = authorId;
        mText = text;
        mObjectId = object;
        mDate = date;
    }

    public Date getDate() {
        return mDate;
    }

    public UUID getId() {
        return mId;
    }

    public UUID getObjectId() {
        return mObjectId;
    }

    public UUID getAuthorId(){
        return mAuthorId;
    }

    public String getText() {
        return mText;
    }

//    public int getLikes() {
//        return Likes;
//    }
}
