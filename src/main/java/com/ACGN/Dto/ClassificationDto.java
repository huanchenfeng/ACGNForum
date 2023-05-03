package com.ACGN.Dto;

public class ClassificationDto {
    private int type;

    private int num;
    public String getType() {
        switch(this.type){
            case 0 :
                return "动画";
            case 1 :
                return  "漫画";
            case 2 :
                return  "游戏";
            case 3 :
                return  "小说";
            default :
                return "其他";

        }

    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
