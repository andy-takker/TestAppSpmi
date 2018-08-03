package com.hikki.sergey_natalenko.testapp.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Note {
    private Date mDate;
    private UUID mId;
    private UUID mAuthor;
    private String mText;
    private List<UUID> mComments;
    private List<UUID> mLikes;
    private boolean mHasFiles;

    public Note(UUID author, String text, boolean hasFiles){
        mId = UUID.randomUUID();
        mDate = new Date();
        mAuthor = author;
        mText = text;
        mHasFiles = hasFiles;
        mLikes = new ArrayList<>();
        mComments = new ArrayList<>();
    }

    public Note(UUID author, UUID id, Date date, String text, boolean hasFiles){
        mId = id;
        mDate = date;
        mAuthor = author;
        mText = text;
        mHasFiles = hasFiles;
        mLikes = new ArrayList<>();
        mComments = new ArrayList<>();
    }

    public void setText(String text) {
        mText = text;
    }

    public Date getDate() {
        return mDate;
    }

    public UUID getId() {
        return mId;
    }

    public UUID getAuthor() {
        return mAuthor;
    }

    public String getText() {
        return mText;
    }

    public int getLikes() {
        return mLikes.size();
    }

    public boolean getHasFiles() {
        return mHasFiles;
    }

    public void addLikes(UUID user) {
        if (this.isLiked(user)){
            mLikes.remove(user);
        } else {
            mLikes.add(user);
        }
    }
    public boolean isLiked(UUID user){
        return mLikes.contains(user);
    }

    public int getComments(){
        return mComments.size();
    }
}
