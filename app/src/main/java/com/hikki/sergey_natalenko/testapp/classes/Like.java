package com.hikki.sergey_natalenko.testapp.classes;

import java.util.UUID;

public class Like {
    private UUID mId;
    private UUID mAuthorId;

    public Like(UUID authorId, UUID id){
        mId = id;
        mAuthorId = authorId;
    }

    public UUID getId() {
        return mId;
    }

    public UUID getAuthorId() {
        return mAuthorId;
    }
}
