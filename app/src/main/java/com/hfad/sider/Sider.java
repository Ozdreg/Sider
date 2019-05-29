package com.hfad.sider;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Sider implements Serializable {
    private String[] name;
    private String[] type;
    private String[] alc;
    private String[] des;
    private String[] cmp;
    private String[] com;
    private Double[] prc;
    private Double[] scr;
    private Double[] vol;
    private Bitmap[] img;
    private int[] index;

    public Sider(String[] name, String[] type, String[] alc, String [] des, String[] cmp, String[] com, Double[] prc, Double[] scr, Double[] vol, Bitmap[] img, int[] index){
        this.name = name;
        this.type = type;
        this.alc = alc;
        this.des = des;
        this.cmp = cmp;
        this.com = com;
        this.prc = prc;
        this.scr = scr;
        this.vol = vol;
        this.img = img;
        this.index = index;
    }

    public String getName(int i) {
        return name[i];
    }

    public void setName(String name[]) {
        this.name = name;
    }

    public String getType(int i) {
        return type[i];
    }

    public void setType(String type[]) { this.type = type;}

    public String  getAlc(int i) {
        return alc[i];
    }

    public void setAlc(String alc[]) {
        this.alc = alc;
    }

    public String  getDes(int i) {
        return des[i];
    }

    public void setDes(String des[]) {
        this.des = des;
    }

    public String  getCmp(int i) {
        return cmp[i];
    }

    public void setCmp(String cmp[]) {
        this.cmp = cmp;
    }

    public String getCom(int i) { return com[i];}

    public void setCom(String com[]) {this.com = com;}

    public Double getPrc(int i) { return prc[i]; }

    public void  setPrc(Double prc[]) {this.prc = prc;}

    public Double getScr(int i) { return scr[i]; }

    public void  setScr(Double scr[]) {this.scr = scr;}

    public Double getVol(int i) { return vol[i]; }

    public void  setVol(Double vol[]) {this.vol = vol;}

    public Bitmap getImg(int i) { return img[i]; }

    public void  setImg(Bitmap img[]) {this.img = img;}

    public int getIndex(int i) { return index[i]; }

    public void setIndex(int index[]) {this.index = index;}

}

