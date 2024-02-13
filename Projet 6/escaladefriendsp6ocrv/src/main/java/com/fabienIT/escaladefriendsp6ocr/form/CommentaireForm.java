package com.fabienIT.escaladefriendsp6ocr.form;

import javax.validation.constraints.NotNull;

public class CommentaireForm {
    
    @NotNull
    private Long sitetId;

    @NotNull
    private int userId;

    public Long getSitetId() {
        return sitetId;
    }

    public void setSitetId(Long sitetId) {
        this.sitetId = sitetId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}