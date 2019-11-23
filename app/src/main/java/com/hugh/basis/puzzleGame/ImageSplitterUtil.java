package com.hugh.basis.puzzleGame;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyw on 2019-11-20.
 */
public class ImageSplitterUtil {

    /**
     * 静态方法
     * 传递bitmap切成piece*piece块，返回List<ImagePiece>
     *
     * @param bitmap
     * @param piece
     * @return
     */
    public static List<ImagePiece> splitImage(Bitmap bitmap, int piece) {

        //作为返回值传递
        List<ImagePiece> imagePieces = new ArrayList<>();

        //获取图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //根据宽高取最小值达到正方形
        int pieceWidth = Math.min(width, height) / piece;

        //进行切割
        for (int i = 0; i < piece; i++) {
            for (int j = 0; j < piece; j++) {
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.setIndex(j + i * piece);

                int x = j * pieceWidth;
                int y = i * pieceWidth;

                //第一次循环为0,0,
                imagePiece.setBitmap(Bitmap.createBitmap(bitmap, x, y, pieceWidth, pieceWidth));
                /**
                 * 保存到list中进行返回
                 */
                imagePieces.add(imagePiece);
            }
        }
        return imagePieces;
    }


}
