package com.blue.cache;
//package com.blue.ui.cache;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.Config;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.PorterDuff.Mode;
//import android.graphics.PorterDuffXfermode;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
///* 
// * ͼƬ���� 
// * �첽��ȡͼƬ��ֱ�ӵ���loadImage()�������ú����Լ��ж��Ǵӻ��滹��������� 
// * ͬ����ȡͼƬ��ֱ�ӵ���getBitmap()�������ú����Լ��ж��Ǵӻ��滹��������� 
// * ���ӱ��ػ�ȡͼƬ������getBitmapFromNative() 
// * �����������ͼƬ������getBitmapFromHttp() 
// *  
// */  
//  
//public class ImageManager implements IManager  
//{  
//    private final static String TAG = "ImageManager";  
//      
//    private ImageMemoryCache imageMemoryCache; //�ڴ滺��  
//      
//    private ImageFileCache   imageFileCache; //�ļ�����  
//      
//    //�������ص�image�б�  
//    public static HashMap<String, Handler> ongoingTaskMap = new HashMap<String, Handler>();  
//      
//    //�ȴ����ص�image�б�  
//    public static HashMap<String, Handler> waitingTaskMap = new HashMap<String, Handler>();  
//      
//    //ͬʱ����ͼƬ���̸߳���  
//    final static int MAX_DOWNLOAD_IMAGE_THREAD = 4;  
//      
//    private final Handler downloadStatusHandler = new Handler(){  
//        public void handleMessage(Message msg)  
//        {  
//            startDownloadNext();  
//        }  
//    };  
//      
//    public ImageManager()  
//    {  
//        imageMemoryCache = new ImageMemoryCache();  
//        imageFileCache = new ImageFileCache();  
//    }  
//      
//    /** 
//     * ��ȡͼƬ�����̵߳���� 
//     */  
//    public void loadBitmap(String url, Handler handler)   
//    {  
//        //�ȴ��ڴ滺���л�ȡ��ȡ��ֱ�Ӽ���  
//        Bitmap bitmap = getBitmapFromNative(url);  
//          
//        if (bitmap != null)  
//        {  
//            Log.d(TAG, "loadBitmap:loaded from native");  
//            Message msg = Message.obtain();  
//            Bundle bundle = new Bundle();  
//            bundle.putString("url", url);  
//            msg.obj = bitmap;  
//            msg.setData(bundle);  
//            handler.sendMessage(msg);  
//        }   
//        else  
//        {  
//            Log.d(TAG, "loadBitmap:will load by network");  
//            downloadBmpOnNewThread(url, handler);  
//        }  
//    }  
//      
//    /** 
//     * �����߳�����ͼƬ 
//     */  
//    private void downloadBmpOnNewThread(final String url, final Handler handler)  
//    {  
//        Log.d(TAG, "ongoingTaskMap'size=" + ongoingTaskMap.size());  
//          
//        if (ongoingTaskMap.size() >= MAX_DOWNLOAD_IMAGE_THREAD)   
//        {  
//            synchronized (waitingTaskMap)   
//            {  
//                waitingTaskMap.put(url, handler);  
//            }  
//        }   
//        else   
//        {  
//            synchronized (ongoingTaskMap)   
//            {  
//                ongoingTaskMap.put(url, handler);  
//            }  
//  
//            new Thread()   
//            {  
//                public void run()   
//                {  
//                    Bitmap bmp = getBitmapFromHttp(url);  
//  
//                    // ���������Ƿ�ɹ����������ض������Ƴ�,����ҵ���߼��ж��Ƿ���������  
//                    // ����ͼƬʹ����httpClientRequest�������Ѿ�������������  
//                    synchronized (ongoingTaskMap)   
//                    {  
//                        ongoingTaskMap.remove(url);  
//                    }  
//                      
//                    if(downloadStatusHandler != null)  
//                    {  
//                        downloadStatusHandler.sendEmptyMessage(0);  
//                      
//                    }  
//  
//                    Message msg = Message.obtain();  
//                    msg.obj = bmp;  
//                    Bundle bundle = new Bundle();  
//                    bundle.putString("url", url);  
//                    msg.setData(bundle);  
//                      
//                    if(handler != null)  
//                    {  
//                        handler.sendMessage(msg);  
//                    }  
//  
//                }  
//            }.start();  
//        }  
//    }  
//  
//      
//    /** 
//     * ���δ��ڴ棬�����ļ��������ϼ��ص���bitmap,�������̵߳����� 
//     */  
//    public Bitmap getBitmap(String url)  
//    {  
//        // ���ڴ滺���л�ȡͼƬ  
//        Bitmap bitmap = imageMemoryCache.getBitmapFromMemory(url);  
//        if (bitmap == null)   
//        {  
//            // �ļ������л�ȡ  
//            bitmap = imageFileCache.getImageFromFile(url);  
//            if (bitmap != null)   
//            {                 
//                // ��ӵ��ڴ滺��  
//                imageMemoryCache.addBitmapToMemory(url, bitmap);  
//            }   
//            else   
//            {  
//                // �������ȡ  
//                bitmap = getBitmapFromHttp(url);  
//            }  
//        }  
//        return bitmap;  
//    }  
//      
//    /** 
//     * ���ڴ���߻����ļ��л�ȡbitmap 
//     */  
//    public Bitmap getBitmapFromNative(String url)  
//    {  
//        Bitmap bitmap = null;  
//        bitmap = imageMemoryCache.getBitmapFromMemory(url);  
//          
//        if(bitmap == null)  
//        {  
//            bitmap = imageFileCache.getImageFromFile(url);  
//            if(bitmap != null)  
//            {  
//                // ��ӵ��ڴ滺��  
//                imageMemoryCache.addBitmapToMemory(url, bitmap);  
//            }  
//        }  
//        return bitmap;  
//    }  
//      
//    /** 
//     * ͨ����������ͼƬ,���߳��޹� 
//     */  
//    public Bitmap getBitmapFromHttp(String url)  
//    {  
//        Bitmap bmp = null;  
//          
//        try  
//        {  
//            byte[] tmpPicByte = getImageBytes(url);  
//      
//            if (tmpPicByte != null)   
//            {  
//                bmp = BitmapFactory.decodeByteArray(tmpPicByte, 0,  
//                        tmpPicByte.length);  
//            }  
//            tmpPicByte = null;  
//        }  
//        catch(Exception e)  
//        {  
//            e.printStackTrace();  
//        }  
//          
//        if(bmp != null)  
//        {  
//            // ��ӵ��ļ�����  
//            imageFileCache.saveBitmapToFile(bmp, url);  
//            // ��ӵ��ڴ滺��  
//            imageMemoryCache.addBitmapToMemory(url, bmp);  
//        }  
//  
//        return bmp;  
//    }  
//      
//    /** 
//     * �������ӵ�ͼƬ��Դ 
//     *  
//     * @param url 
//     *             
//     * @return ͼƬ 
//     */  
//    public byte[] getImageBytes(String url)   
//    {  
//        byte[] pic = null;  
//        if (url != null && !"".equals(url))   
//        {  
//            Requester request = RequesterFactory.getRequester(  
//                    Requester.REQUEST_REMOTE, RequesterFactory.IMPL_HC);  
//            // ִ������  
//            MyResponse myResponse = null;  
//            MyRequest mMyRequest;  
//            mMyRequest = new MyRequest();  
//            mMyRequest.setUrl(url);  
//            mMyRequest.addHeader(HttpHeader.REQ.ACCEPT_ENCODING, "identity");  
//            InputStream is = null;  
//            ByteArrayOutputStream baos = null;  
//            try {  
//                myResponse = request.execute(mMyRequest);  
//                is = myResponse.getInputStream().getImpl();  
//                baos = new ByteArrayOutputStream();  
//                byte[] b = new byte[512];  
//                int len = 0;  
//                while ((len = is.read(b)) != -1)   
//                {  
//                    baos.write(b, 0, len);  
//                    baos.flush();  
//                }  
//                pic = baos.toByteArray();  
//                Log.d(TAG, "icon bytes.length=" + pic.length);  
//  
//            }   
//            catch (Exception e3)   
//            {  
//                e3.printStackTrace();  
//                try   
//                {  
//                    Log.e(TAG,  
//                            "download shortcut icon faild and responsecode="  
//                                    + myResponse.getStatusCode());  
//                }   
//                catch (Exception e4)   
//                {  
//                    e4.printStackTrace();  
//                }  
//            }   
//            finally   
//            {  
//                try   
//                {  
//                    if (is != null)   
//                    {  
//                        is.close();  
//                        is = null;  
//                    }  
//                }   
//                catch (Exception e2)   
//                {  
//                    e2.printStackTrace();  
//                }  
//                try   
//                {  
//                    if (baos != null)   
//                    {  
//                        baos.close();  
//                        baos = null;  
//                    }  
//                }   
//                catch (Exception e2)   
//                {  
//                    e2.printStackTrace();  
//                }  
//                try   
//                {  
//                    request.close();  
//                }   
//                catch (Exception e1)   
//                {  
//                    e1.printStackTrace();  
//                }  
//            }  
//        }  
//        return pic;  
//    }  
//      
//    /** 
//     * ȡ���ȴ����е�һ�����񣬿�ʼ���� 
//     */  
//    private void startDownloadNext()  
//    {  
//        synchronized(waitingTaskMap)  
//        {     
//            Log.d(TAG, "begin start next");  
//            Iterator iter = waitingTaskMap.entrySet().iterator();   
//          
//            while (iter.hasNext())   
//            {  
//                  
//                Map.Entry entry = (Map.Entry) iter.next();  
//                Log.d(TAG, "WaitingTaskMap isn't null,url=" + (String)entry.getKey());  
//                  
//                if(entry != null)  
//                {  
//                    waitingTaskMap.remove(entry.getKey());  
//                    downloadBmpOnNewThread((String)entry.getKey(), (Handler)entry.getValue());  
//                }  
//                break;  
//            }  
//        }  
//    }  
//      
//    public String startDownloadNext_ForUnitTest()  
//    {  
//        String urlString = null;  
//        synchronized(waitingTaskMap)  
//        {  
//            Log.d(TAG, "begin start next");  
//            Iterator iter = waitingTaskMap.entrySet().iterator();   
//          
//            while (iter.hasNext())   
//            {  
//                Map.Entry entry = (Map.Entry) iter.next();  
//                urlString = (String)entry.getKey();  
//                waitingTaskMap.remove(entry.getKey());  
//                break;  
//            }  
//        }  
//        return urlString;  
//    }  
//      
//    /** 
//     * ͼƬ��ΪԲ�� 
//     * @param bitmap:�����bitmap 
//     * @param pixels��Բ�ǵĶ�����ֵԽ��Բ��Խ�� 
//     * @return bitmap:����Բ�ǵ�bitmap 
//     */  
//    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels)   
//    {   
//        if(bitmap == null)  
//            return null;  
//          
//        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);   
//        Canvas canvas = new Canvas(output);   
//   
//        final int color = 0xff424242;   
//        final Paint paint = new Paint();   
//        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());   
//        final RectF rectF = new RectF(rect);   
//        final float roundPx = pixels;   
//   
//        paint.setAntiAlias(true);   
//        canvas.drawARGB(0, 0, 0, 0);   
//        paint.setColor(color);   
//        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);   
//   
//        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));   
//        canvas.drawBitmap(bitmap, rect, rect, paint);   
//   
//        return output;   
//    }  
//      
//    public byte managerId()   
//    {  
//        return IMAGE_ID;  
//    }  
//}  
