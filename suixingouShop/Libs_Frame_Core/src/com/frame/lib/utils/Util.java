package com.frame.lib.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class Util {

	/**
	 * 产生随机数
	 * 
	 * @return
	 */
	public static String GenDummy() {
		String result = "";
		Random rnd = new Random();
		for (int i = 1; i <= 8; i++) {
			result = result + rnd.nextInt(9);
		}
		return result;
	}
	/**是不是( 9999.99 )形式的数据
	 * @param num
	 * @returnboolean
	 */
	public static boolean isNumber(String num){
		return matches(num,"[0-9]+");
	}
	/**判断是否为密码
	 * 是否为字母,数字,下划线
	 * @param pw
	 * @returnboolean
	 */
	public static boolean isPassWord(String pw){
		return matches(pw,"^([A-Za-z0-9]|_){6,20}$");
//		return matches(pw,"^(\\w|_){6,20}$");
	}
	/**生成文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static File createFile(String path,String fileName){
		File file = new File(path);
		if(!file.exists()){
			file.mkdir();
		}
		File name = new File(path+fileName);
		if(name.exists()){
			name.delete();
		}
		try {
			name.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return name;
	}
	/**判断是否为手机号
	 * 是否为1开头的11数字
	 * @param phoneNum
	 * @returnboolean
	 */
	public static boolean isPhoneNum(String phoneNum){
		return matches(phoneNum,"^1\\d{10}$");
	}
	/**判断是否为手机号
	 * 是否为1开头的11数字
	 * @param phoneNum
	 * @returnboolean
	 */
	public static boolean isNum(String phoneNum){
		return matches(phoneNum,"^\\d$");
	}
	/**比对正则
	 * @param str
	 * @param reg
	 * @returnboolean
	 */
	public static boolean matches(String str,String reg){
		boolean boo =  false;
		Pattern pat = Pattern.compile(reg);  
		Matcher mat = pat.matcher(str);  
		boo = mat.matches();
		return boo;
	}
	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}
		
		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	/**质量压缩方法
	 * @param image
	 * @return
	 */
	public static int compressImage(Bitmap image,int picSize) {  
		int options = 100;  
		int temSize = 512000;
		if(picSize > 15060){
			temSize = picSize;
		}
		if(temSize>= 1048576){
			temSize = 1048576;
		}
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        while ( baos.toByteArray().length >=temSize) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            options =options - 10;//每次都减少10  
            if(options <=0){
            	options = 1;
            	break;
            }
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
        }
        baos.reset();
        return options;  
    }
	
//	/**质量压缩方法
//	 * @param image
//	 * @return
//	 */
//	public static Bitmap compressImage(Bitmap image,int picSize) {  
//		int temSize = 300;
//		if(picSize > 15){
//			temSize = picSize;
//		}
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
//        int options = 100;  
//        while ( baos.toByteArray().length / 1024>=temSize) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
//            baos.reset();//重置baos即清空baos  
//            options -= 10;//每次都减少10  
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
//        }
//        BitmapFactory.Options opt = new BitmapFactory.Options();  
//        opt.inPreferredConfig = Bitmap.Config.RGB_565;   
//        opt.inPurgeable = true;  
//        opt.inInputShareable = true;  
//        opt.inJustDecodeBounds = false;
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, opt);//把ByteArrayInputStream数据生成图片  
//        baos.reset();
//        return bitmap;  
//    }
//	
	public static Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>300) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }

	public static BitmapFactory.Options getImageOption(String imaPath,int width,int height){
		BitmapFactory.Options newOpts = new BitmapFactory.Options(); 
		newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(imaPath,newOpts);//此时返回bm为空  
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        int be = 1;//be=1表示不缩放  
        if(w > width){
        	be = (newOpts.outWidth / width);  
        	if(be == 1 && newOpts.outWidth % width != 0){
        		be ++;
        	}
        }else if(h > height){
        	be = (newOpts.outHeight / height);  
        	if(be == 1 && newOpts.outHeight % height != 0){
        		be ++;
        	}
        }
        newOpts.inSampleSize = be;
        bitmapRecycle(bitmap);
		return newOpts;
	}
	
	
	
//	public static BitmapFactory.Options getImageOption(Bitmap pic,int width){
//		BitmapFactory.Options newOpts = new BitmapFactory.Options(); 
//		  ByteArrayOutputStream baos = new ByteArrayOutputStream();         
//		  pic.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
//		
//		
//		
//		newOpts.inJustDecodeBounds = true;  
//        Bitmap bitmap = BitmapFactory.decodeFile(imaPath,newOpts);//此时返回bm为空  
//        newOpts.inJustDecodeBounds = false;  
//        int w = newOpts.outWidth;  
//        int be = 1;//be=1表示不缩放  
//        if(w > width){
//        	be = (newOpts.outWidth / width);  
//        	if(be == 1 && newOpts.outWidth % width != 0){
//        		be ++;
//        	}
//        }
//        newOpts.inSampleSize = be;
//        bitmapRecycle(bitmap);
//		return newOpts;
//	}

	/**图片按比例大小压缩方法（根据路径获取图片并压缩
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getimage(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 800f;//这里设置高度为800f  
        float ww = 480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
    }  


	/**图片按比例大小压缩方法(根据Bitmap图片压缩)
	 * @param image
	 * @return
	 */
	public static Bitmap comp(Bitmap image) {  
      
    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
    if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
        baos.reset();//重置baos即清空baos  
        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
    }  
    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
    //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
    newOpts.inJustDecodeBounds = true;  
    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
    newOpts.inJustDecodeBounds = false;  
    int w = newOpts.outWidth;  
    int h = newOpts.outHeight;  
    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
    float hh = 800f;//这里设置高度为800f  
    float ww = 480f;//这里设置宽度为480f  
    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
    int be = 1;//be=1表示不缩放  
    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
        be = (int) (newOpts.outWidth / ww);  
    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
        be = (int) (newOpts.outHeight / hh);  
    }  
    if (be <= 0)  
        be = 1;  
    newOpts.inSampleSize = be;//设置缩放比例  
    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
    isBm = new ByteArrayInputStream(baos.toByteArray());  
    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
    return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
}  
	
	
	float curAngle;
	public static int difference = 5;
	public boolean rotateStraight(float angle,boolean among){
		boolean flat = false;
		float difference;
		if(among){
			difference = angle - curAngle;
			
		}else{
			
		}
		
		return flat;
	}
	
	/**保存图片
	 * @param pic
	 */
	public static boolean storePic(Bitmap pic){
		boolean flat = false;
		if(pic == null){
			return flat;
		}
		File f = Util.createFile("/mnt/sdcard/mlj","/store" + Util.GenDummy() + ".jpeg");
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(f);
			pic.compress(Bitmap.CompressFormat.JPEG, 100, fout);
			flat = true;
		} catch (Exception e) {
			flat = false;
			e.printStackTrace();
		}finally{
			try {
				if(fout != null){
					fout.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flat;
	}
	
	
	/**删除文件
	 * @param path
	 */
	public static void deleteFile(String path){
		File file = new File(path);
		deleteFile(file);
	}
	
	public static void deleteFile(File file) {
		  if (file.exists()) { // 判断文件是否存在
		      if (file.isFile()) { // 判断是否是文件
		          file.delete(); // delete()方法 你应该知道 是删除的意思;
		      } else if (file.isDirectory()) { // 否则如果它是一个目录
		          File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
		          for (int i = 0; i<files.length; i++) { // 遍历目录下所有的文件
		              deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
		          }
		      }
		      file.delete();
		  } else {
		  }
     }

	
	public static void bitmapRecycle(Bitmap pic){
		if(pic != null && !pic.isRecycled()){
			pic.recycle();
		}
	}

}
