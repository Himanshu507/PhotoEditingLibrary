package com.himanshu.editlibrary.EditLib;

class ViewPojo {

    public float Translate_x ;
    public float Translate_y ;
    public float Angle;
    public float Scale;
    public String Type;

    public float getTranslate_x() {
        return Translate_x;
    }

    public float getTranslate_y() {
        return Translate_y;
    }

    public float getAngle() {
        return Angle;
    }

    public float getScale() {
        return Scale;
    }

    public String getType() {
        return Type;
    }

    public void setTranslate_x(float x) {
        this.Translate_x = x;
    }

    public void setTranslate_y(float y) {
        this.Translate_y = y;
    }

    public void setAngle(float rotation) {
        this.Angle = rotation;
    }

    public void setScale(float scale) {
        this.Scale = scale;
    }

    public void setType(String name) {
        this.Type = name;
    }
}
