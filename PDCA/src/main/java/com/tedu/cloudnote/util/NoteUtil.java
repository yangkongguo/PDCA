package com.tedu.cloudnote.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

import org.apache.tomcat.util.codec.binary.Base64;

public class NoteUtil {
	
	public static String createId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
	
	/**
	 * 将传入的src加密处理
	 * @param src 明文字符串
	 * @return 加密后的字符串结果
	 * @throws Exception 
	 */
	public static String md5(String src) throws Exception{
		//将字符串信息采用MD5处理
		MessageDigest md = 
			MessageDigest.getInstance("MD5");
		byte[] output = md.digest(src.getBytes());
		//将MD5处理结果利用Base64转成字符串
		String s = 
		  Base64.encodeBase64String(output);
		return s;
	}
	//创建验证码
	public BufferedImage createCode(){
		return null;
	}
	 private static Random random = new Random();
	    /*
	     * 获得字体
	     */
	    public static Font getFont(int fSize){
	        return new Font("Fixedsys",Font.CENTER_BASELINE,fSize);
	    }
	    
	      /*
	     * 绘制干扰线
	     */
	    public static void drowLine(Graphics g, int width, int height){
	        int x = random.nextInt(width);
	        int y = random.nextInt(height);
	        int xl = random.nextInt(13);
	        int yl = random.nextInt(15);
	        g.drawLine(x, y, x+xl, y+yl);
	    }
	    
	     /*
	     * 获得颜色
	     */
	    public static Color getRandColor(int fc,int bc){
	        if(fc > 255)
	            fc = 255;
	        if(bc > 255)
	            bc = 255;
	        int r = fc + random.nextInt(bc-fc-16);
	        int g = fc + random.nextInt(bc-fc-14);
	        int b = fc + random.nextInt(bc-fc-18);
	        return new Color(r,g,b);
	    }
	    /**
	     * 生成验证码图片
	     * 
	     * @param securityCode
	     *            验证码字符
	     * @return BufferedImage 图片
	     */
	    public static BufferedImage createImage() {
	    	 String[]seed = new String[]{  
	                    "abcdefghijklmnopqrstuvwxyz",    
	                    "ABCDEFGHIJKLMNOPQRSTUVWXYZ",    
	                    "0123456789"} ;
	    	 String result = "";  
	    	 for(int i=0;i<seed.length;i++){  
	    		 int idx = (int) Math.floor(Math.random()*3);//随机获取一个3以内的整数  
	    		 result += seed[idx].indexOf((int)Math.floor(Math.random()*seed[idx].length()));  
	    	 }  
	        // 验证码长度
	        int codeLength = result.length();
	        // 字体大小
	        int fSize = 50;
	        int fWidth = fSize + 1;
	        // 图片宽度
	        int width = codeLength * fWidth + 6;
	        // 图片高度
	        int height = fSize * 2 + 1;
	        // 图片
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        Graphics g = image.createGraphics();
	        // 设置背景色
	        g.setColor(Color.WHITE);
	        // 填充背景
	        g.fillRect(0, 0, width, height);
	        // 设置边框颜色
	        g.setColor(Color.LIGHT_GRAY);
	        // 边框字体样式
	        g.setFont(new Font("Arial", Font.BOLD, height - 2));
//	        g.setFont(getFont());
	        // 绘制边框
	        g.drawRect(0, 0, width - 1, height - 1);
	         //绘制干扰线
	        for(int i=0;i<=40;i++){
	            drowLine(g,width,height);
	        }
	        
	        
	        // 绘制验证码
	        int codeY = height - 20;
	        // 设置字体颜色和样式
	        g.setColor(new Color(random.nextInt(101),random.nextInt(111),random.nextInt(121)));
	        g.setFont(getFont(fSize));
	        for (int i = 0; i < codeLength; i++) {
	            g.drawString(String.valueOf(result.charAt(i)), i * 16 + 5, codeY);
	        }
	        // 关闭资源
	        g.dispose();
	        return image;
	    }

	    
	public static void main(String[] args) throws Exception{
//		System.out.println(md5("123456"));
//		System.out.println(md5("12354654676dfdfdfdf"));
		System.out.println(createId());
		System.out.println(createId());
	}
	
}
