package com.blue.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class ImageFileCache  
{  
    private static final String TAG = "ImageFileCache";  
      
    //ͼƬ����Ŀ¼  
    private static final String IMGCACHDIR = "/sdcard/ImgCach";  
      
    //�����cache�ļ���չ��  
    private static final String CACHETAIL = ".cach";  
                                                              
    private static final int MB = 1024*1024;  
      
    private static final int CACHE_SIZE = 1;  
      
    //��SD��ʣ��ռ�С��10M��ʱ���������  
    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;  
                                                                  
    public ImageFileCache()   
    {  
        //�������ļ�����  
        removeCache(IMGCACHDIR);              
    }  
                                                                  
    /**  
     * �ӻ����л�ȡͼƬ  
     */  
    public Bitmap getImageFromFile(final String url)   
    {      
        final String path = IMGCACHDIR + "/" + convertUrlToFileName(url);  
        File file = new File(path);  
        if (file != null && file.exists())   
        {  
            Bitmap bmp = BitmapFactory.decodeFile(path);  
            if (bmp == null)   
            {  
                file.delete();  
            }   
            else   
            {  
                updateFileTime(path);  
                Log.d(TAG, "get bmp from FileCache,url=" + url);  
                return bmp;  
            }  
        }  
        return null;  
    }  
                                                                  
    /** 
     * ��ͼƬ�����ļ�����  
     */  
    public void saveBitmapToFile(Bitmap bm, String url)   
    {  
        if (bm == null) {  
            return;  
        }  
        //�ж�sdcard�ϵĿռ�  
        if (FREE_SD_SPACE_NEEDED_TO_CACHE > SdCardFreeSpace())   
        {  
            //SD�ռ䲻��  
            return;  
        }  
          
        String filename = convertUrlToFileName(url);  
        File dirFile = new File(IMGCACHDIR);  
        if (!dirFile.exists())  
            dirFile.mkdirs();  
        File file = new File(IMGCACHDIR +"/" + filename);  
        try   
        {  
            file.createNewFile();  
            OutputStream outStream = new FileOutputStream(file);  
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);  
            outStream.flush();  
            outStream.close();  
        }   
        catch (FileNotFoundException e)   
        {  
            Log.d(TAG, "FileNotFoundException");  
        }   
        catch (IOException e)   
        {  
            Log.d(TAG, "IOException");  
        }  
    }   
                                                                  
    /** 
     * ����洢Ŀ¼�µ��ļ���С�� 
     * ���ļ��ܴ�С���ڹ涨��CACHE_SIZE����sdcardʣ��ռ�С��FREE_SD_SPACE_NEEDED_TO_CACHE�Ĺ涨 
     * ��ôɾ��40%���û�б�ʹ�õ��ļ� 
     */  
    private boolean removeCache(String dirPath)   
    {  
        File dir = new File(dirPath);  
        File[] files = dir.listFiles();  
          
        if (files == null)   
        {  
            return true;  
        }  
          
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))  
        {  
            return false;  
        }  
                                                              
        int dirSize = 0;  
        for (int i = 0; i < files.length; i++)   
        {  
            if (files[i].getName().contains(CACHETAIL))   
            {  
                dirSize += files[i].length();  
            }  
        }  
                                                              
        if (dirSize > CACHE_SIZE * MB || FREE_SD_SPACE_NEEDED_TO_CACHE > SdCardFreeSpace())   
        {  
            int removeFactor = (int) (0.4 * files.length);  
            Arrays.sort(files, new FileLastModifSort());  
            for (int i = 0; i < removeFactor; i++)   
            {  
                if (files[i].getName().contains(CACHETAIL))   
                {  
                    files[i].delete();  
                }  
            }  
        }  
                                                              
        if (SdCardFreeSpace() <= CACHE_SIZE)   
        {  
            return false;  
        }  
                                                                      
        return true;  
    }  
                                                                  
    /** 
     * �޸��ļ�������޸�ʱ�� 
     */  
    public void updateFileTime(String path)   
    {  
        File file = new File(path);  
        long newModifiedTime = System.currentTimeMillis();  
        file.setLastModified(newModifiedTime);  
    }  
                                                                  
    /**  
     * ����SD���ϵ�ʣ��ռ�  
     */  
    private int SdCardFreeSpace()  
    {  
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());  
        double sdFreeMB = ((double)stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;  
        return (int) sdFreeMB;  
    }   
                                                                  
    /**  
     * ��urlת���ļ���  
     */  
    private String convertUrlToFileName(String url)  
    {  
        return url.hashCode() + CACHETAIL;  
    }  
                                                                  
    /** 
     * �����ļ�������޸�ʱ��������� 
     */  
    private class FileLastModifSort implements Comparator<File>   
    {  
        public int compare(File file0, File file1)   
        {  
            if (file0.lastModified() > file1.lastModified())  
            {  
                return 1;  
            }   
            else if (file0.lastModified() == file1.lastModified())   
            {  
                return 0;  
            }   
            else   
            {  
                return -1;  
            }  
        }  
    }  
  
}  
