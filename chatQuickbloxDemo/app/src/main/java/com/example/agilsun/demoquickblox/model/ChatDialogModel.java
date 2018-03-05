package com.example.agilsun.demoquickblox.model;

import java.util.List;

/**
 * *******************************************
 * * Created by HoLu on 28/02/2018.         **
 * * All rights reserved                    **
 * *******************************************
 */

public class ChatDialogModel {
    private String idDialog;
    private String nameDialog;
    private String image;
    private List<Integer> occupantsid;
    private int unRead;


    public ChatDialogModel() {
    }

    public ChatDialogModel(String idDialog, String nameDialog, String image) {
        this.idDialog = idDialog;
        this.nameDialog = nameDialog;
        this.image = image;
    }

    public ChatDialogModel(String idDialog, String nameDialog, String image, List<Integer> occupantsid) {
        this.idDialog = idDialog;
        this.nameDialog = nameDialog;
        this.image = image;
        this.occupantsid = occupantsid;
    }

    public ChatDialogModel(String idDialog, String nameDialog, String image, List<Integer> occupantsid, int unRead) {
        this.idDialog = idDialog;
        this.nameDialog = nameDialog;
        this.image = image;
        this.occupantsid = occupantsid;
        this.unRead = unRead;
    }

    public String getIdDialog() {
        return idDialog;
    }

    public void setIdDialog(String idDialog) {
        this.idDialog = idDialog;
    }

    public String getNameDialog() {
        return nameDialog;
    }

    public void setNameDialog(String nameDialog) {
        this.nameDialog = nameDialog;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Integer> getOccupantsid() {
        return occupantsid;
    }

    public void setOccupantsid(List<Integer> occupantsid) {
        this.occupantsid = occupantsid;
    }

    public int getUnRead() {
        return unRead;
    }

    public void setUnRead(int unRead) {
        this.unRead = unRead;
    }
}
