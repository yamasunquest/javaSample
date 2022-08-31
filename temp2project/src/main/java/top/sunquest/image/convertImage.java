package top.sunquest.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class convertImage {
    public static void main(String[] args) {
        BufferedImage bi=file2img("/Users/sunkai/Desktop/20220419164614.jpg"); //读取图片
        BufferedImage bii=img_color_contrast(bi,100);
        img2file(bii,"jpg","/Users/sunkai/Desktop/test1.jpg"); //生成图片
    }


    public static BufferedImage img_color_contrast(BufferedImage imgsrc, int contrast) {
        try {
            int contrast_average = 128;
            //创建一个不带透明度的图片
            BufferedImage back=new BufferedImage(imgsrc.getWidth(), imgsrc.getHeight(),BufferedImage.TYPE_INT_RGB);
            int width = imgsrc.getWidth();
            int height = imgsrc.getHeight();
            int pix;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = imgsrc.getRGB(j, i);
                    Color color = new Color(pixel);

                    if (color.getRed() < contrast_average)
                    {
                        pix = color.getRed()- Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    }
                    else
                    {
                        pix = color.getRed() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int red= pix;

                    if (color.getGreen() < contrast_average)
                    {
                        pix = color.getGreen()- Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    }
                    else
                    {
                        pix = color.getGreen() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int green= pix;

                    if (color.getBlue() < contrast_average)
                    {
                        pix = color.getBlue()- Math.abs(contrast);
                        if (pix < 0) pix = 0;
                    }
                    else
                    {
                        pix = color.getBlue() + Math.abs(contrast);
                        if (pix > 255) pix = 255;
                    }
                    int blue= pix;

                    color = new Color(red,green,blue);
                    int x=color.getRGB();
                    back.setRGB(j,i,x);
                }
            }
            return back;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //读取图片
    public static BufferedImage file2img(String imgpath) {
        try {
            BufferedImage bufferedImage= ImageIO.read(new File(imgpath));
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //保存图片,extent为格式，"jpg"、"png"等
    public static void img2file(BufferedImage img,String extent,String newfile) {
        try {
            ImageIO.write(img, extent, new File(newfile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
