package com.hugh.basis.puzzleGame;

import android.graphics.Bitmap;

/**
 * Created by chenyw on 2019-11-20.
 */
public class ImagePiece {

    private int index;
    private Bitmap bitmap;

    //构造方法
    public ImagePiece() {

    }

    //有参构造方法
    public ImagePiece(int index, Bitmap bitmap) {
        this.index = index;
        this.bitmap = bitmap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}
