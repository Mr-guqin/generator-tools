package com.koo.generator.utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

/**
 * @author gu.wq
 * @version 1.0
 * @type IdCardUtil
 * @desc
 * @date 2024/3/21 14:27
 */
public class IdCardUtil {
    private Font font = new Font("黑体", Font.BOLD, 14);// 添加字体的属性设置

    private Graphics2D g = null;

    private int fontsize = 0;

    private int x = 0;

    private int y = 0;

    public static int color_range = 210;

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageLocal(String imgName) {
        try {
            return ImageIO.read(new File(imgName));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入本地图片到缓冲区
     */
    public BufferedImage loadImageUrl(URL url) {
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 导入网络图片到缓冲区
     */
    public BufferedImage loadImageUrl(String imgName) {
        try {
            URL url = new URL(imgName);
            return ImageIO.read(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 生成新图片到本地
     */
    public void writeImageLocal(String newImage, BufferedImage img) {
        if (newImage != null && img != null) {
            try {
                File outputfile = new File(newImage);
                ImageIO.write(img, "jpg", outputfile);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 设定文字的字体等
     */
    public void setFont(String fontStyle, int fontSize) {
        this.fontsize = fontSize;
        this.font = new Font(fontStyle, Font.BOLD, fontSize); //
    }

    /**
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本）
     * space 字体间距
     */
    public BufferedImage modifyImage(BufferedImage img, Object content, int x,
                                     int y, Integer space) {

        try {
            int w = img.getWidth();
            int h = img.getHeight();
            g = img.createGraphics();
            g.setBackground(Color.black);
            g.setColor(Color.black);//设置字体颜色
            if (this.font != null) {
                g.setFont(this.font);
            }
            // 验证输出位置的纵坐标和横坐标
            if (x >= h || y >= w) {
                this.x = h - this.fontsize + 2;
                this.y = w;
            } else {
                this.x = x;
                this.y = y;
            }
            if (content != null) {
                if (space != null && space > 0) {
                    String lineString = content.toString();
                    // 绘制字符的时候添加间隔
                    for (int i = 0; i < lineString.length(); i++) {
                        g.drawString(String.valueOf(lineString.charAt(i)), this.x, this.y);
                        this.x += space;
                    }
                } else {
                    g.drawString(content.toString(), this.x, this.y);
                }
            }
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return img;
    }

    /**
     * 图像合成
     *
     * @param b
     * @param d
     * @return
     */
    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d, int x, int y) {
        try {
            //            int w = b.getWidth();
            //            int h = b.getHeight();
            g = d.createGraphics();
            g.drawImage(b, x, y, 800, 1000, null); //调整粘贴图片的位置
            g.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return d;
    }

    /**
     * 指定身份证号码解析
     *
     * @param result
     * @param idCardNo
     */
    public void defineIdno(Map<String, String> result, String idCardNo) {
        String sex = idCardNo.substring(16, 17);
        int i = Integer.parseInt(sex);
        if (i % 2 == 0) {
            sex = "女";//M
        } else {
            sex = "男";//F
        }
        result.put("idCardNo", idCardNo);//更改身份证
        result.put("year", idCardNo.substring(6, 10));//更改年
        result.put("month", idCardNo.substring(10, 12));//更改月
        result.put("day", idCardNo.substring(12, 14));//更改日
        result.put("sex", sex);//性别
    }


    /**
     * base64 编码转换为 BufferedImage
     * 图片白底转透明
     *
     * @param base64
     * @return
     */
    public BufferedImage base64ToBufferedImage(String base64) {
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] bytes1 = decoder.decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bufferedImage = ImageIO.read(bais);
            //  图片白底转透明
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            BufferedImage bf = new BufferedImage(
                    imageIcon.getIconWidth(), imageIcon.getIconHeight(),
                    BufferedImage.TYPE_USHORT_555_RGB);
            Graphics2D g2D = (Graphics2D) bf.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0,
                    imageIcon.getImageObserver());
            int alpha;
            for (int j1 = bf.getMinY(); j1 < bf
                    .getHeight(); j1++) {
                for (int j2 = bf.getMinX(); j2 < bf
                        .getWidth(); j2++) {
                    int rgb = bf.getRGB(j2, j1);
                    if (colorInRange(rgb)) {
                        alpha = 0;
                    } else {
                        alpha = 255;
                    }
                    rgb = (alpha << 24) | (rgb & 0x00ffffff);
                    bf.setRGB(j2, j1, rgb);
                }
            }
            g2D.drawImage(bf, 0, 0, imageIcon.getImageObserver());
            return bf;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转rgb
     *
     * @param color
     * @return
     */
    public boolean colorInRange(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        if (red >= color_range && green >= color_range && blue >= color_range) {
            return true;
        }
        return false;
    }
}
