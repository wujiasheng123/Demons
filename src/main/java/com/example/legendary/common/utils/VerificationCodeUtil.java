package com.example.legendary.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 随机生成验证码图片 图片长宽默认70 * 35
 * @author 吴嘉晟
 */
public class VerificationCodeUtil {

	/**
	 * 图片宽度
	 */
	private static int width = 70;

	/**
	 * 图片高度
	 */
	private static int HEIGHT = 35;
	private static final Random R = new Random();

	/**
	 * 验证码上的文本
	 */
	private static String text ;
	
	/**
	 * 设置宽度
	 * @param width 宽度
	 */
	public static void setWidth(int width) {
		VerificationCodeUtil.width = width;
	}
	
	/**
	 * 设置高度
	 * @param height 高度
	 */
	public static void setHeight(int height) {
		VerificationCodeUtil.HEIGHT = height;
	}
	
	/**
	 * 随机生成颜色
	 * @return Color
	 */
	private static Color randomColor () {
	    int red = R.nextInt(150);
	    int green = R.nextInt(150);
	    int blue = R.nextInt(150);
	    return new Color(red, green, blue);
	}
	
	/**
	 * 随机生成字体
	 * @return 字体
	 */
	private static Font randomFont () {
		// {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"}
		String[] fontNames  = {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑", "楷体_GB2312"};
	    int index = R.nextInt(fontNames.length);
	    String fontName = fontNames[index];
	    //生成随机的字体名称
	    int style = R.nextInt(4);
	    //生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
	    int size = R.nextInt(5) + 24;
	    //生成随机字号, 24 ~ 28
	    return new Font(fontName, style, size);
	}
	
	/**
	 * 画干扰线
	 */
	private static void drawLine (BufferedImage image) {
		// 干扰线条数
		int num = 6 * (width * HEIGHT / (70 * 35));
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		for(int i = 0; i < num; i++) {
			//生成两个点的坐标，即4个值
		    int x1 = R.nextInt(width);
		    int y1 = R.nextInt(HEIGHT);
		    int x2 = R.nextInt(width);
		    int y2 = R.nextInt(HEIGHT);
		    g2.setStroke(new BasicStroke(1.5F));
		    g2.setColor(randomColor());
		    //随机生成干扰线颜色
		    g2.drawLine(x1, y1, x2, y2);
		    //画线
		    }
	}
	
	/**
	 * 随机生成字符
	 * @return 字符
	 */
	private static char randomChar () {
		// 可选字符
		String codes  = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	    int index = R.nextInt(codes.length());
	    return codes.charAt(index);
	}
	
	/**
	 * 创建BufferedImage
	 * @return 图片
	 */
	private static BufferedImage createImage () {
	    BufferedImage image = new BufferedImage(width, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = (Graphics2D)image.getGraphics();
	    g2.setColor(new Color(255, 255, 255));
	    g2.fillRect(0, 0, width, HEIGHT);
	     return image;
	}
	
	/**
	 * 得到验证码
	 * @return
	 */
	private static BufferedImage getImage () {
	    BufferedImage image = createImage();
	    //创建图片缓冲区
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		//得到绘制环境
		StringBuilder sb = new StringBuilder();
		//用来装载生成的验证码文本
		// 向图片中画4个字符
		for(int i = 0; i < 4; i++)  {
			//循环四次，每次生成一个字符
			String s = randomChar() + "";
			//随机生成一个字母
	    	sb.append(s);
	    	//把字母添加到sb中
	    	float x = i * 1.0F * width / 4;
	    	//设置当前字符的x轴坐标
	    	g2.setFont(randomFont());
	    	//设置随机字体
	    	g2.setColor(randomColor());
	    	//设置随机颜色
	    	g2.drawString(s, x, HEIGHT-5);
	    	//画图
		}
		text = sb.toString();
		//把生成的字符串赋给了this.text
		drawLine(image);
		//添加干扰线
	    return image;        
	}
	
	/**
	 * 返回验证码图片上的文本
	 * @return 文本
	 */
	public static String getText () {
	    return text;
	}
	
	/**
	 * 保存图片到指定的位置
	 * @param out 流
	 */
	public static void output (OutputStream out)
	            throws IOException {
	    ImageIO.write(getImage(), "JPEG", out);
	}
}
