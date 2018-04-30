package com.semicolon.entity;

import com.semicolon.entity.Enumerations.ReactionType;

public class Reaction {
    private int id;
    private int memberId;
    private int postId;
    private int photoId;
    private int answerId;
    private ReactionType reactionType;

    public Reaction(int id, int memberId, int postId, int photoId, int answerId, ReactionType reactionType) {
        this.id = id;
        this.memberId = memberId;
        this.postId = postId;
        this.photoId = photoId;
        this.answerId = answerId;
        this.reactionType = reactionType;
    }

    public Reaction(int memberId, int postId, int photoId, int answerId, ReactionType reactionType) {
        this.memberId = memberId;
        this.postId = postId;
        this.photoId = photoId;
        this.answerId = answerId;
        this.reactionType = reactionType;
    }

    public Reaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }
}
