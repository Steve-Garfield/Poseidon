package com.example.poseidon;

public class ProductList {
    private String img;
    private int ml;
    private int flag;
    private String name;

    public ProductList() {}

    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public int getMl() {
        return ml;
    }
    public void setMl(int ml) {
        this.ml = ml;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
