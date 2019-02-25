package com.xuechuan.xcedu.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;

import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName: ${type_name}
 * @Description: ${todo}
 * @author: ${libiao}
 * @date: ${date} ${time}
 * ${tags}
 */
public class ImageUtil {
    public static String getSmallBitmap(String filePath) {
//        return getimage(filePath);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 400, 400);
        options.inJustDecodeBounds = false;

        Bitmap bitmap = centerSquareScaleBitmap(BitmapFactory.decodeFile(filePath, options), 320);
//        return compressAndSave(BitmapFactory.decodeFile(filePath, options));
        return compressAndSave(bitmap);
    }

    // 计算图片的缩放值
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    // 这里是根据质量压缩.可以吧图片压缩到100K以下,然后保存到本地文件
    private static String compressAndSave(Bitmap image) {
        String path = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
//        System.out.println(baos.toByteArray().length + "");
            while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;// 每次都减少10
            }
            path = FileUtil.saveImg(baos);

            if (image != null && image.isRecycled()) {
                image.recycle();
                image = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 在这里已经压缩到100以下了，但是只要调用了decodeStream就又会涨到200K,所以在操作之前把图片先保存到本地
        // 如果需要返回一个bitmap对象，则调用如下方法
        // ByteArrayInputStream isBm = new
        // ByteArrayInputStream(baos.toByteArray());
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        // Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        // 把ByteArrayInputStream数据生成图片
        return path;
    }

    /**
     * @param context
     * @param uri
     * @return
     * @Title: getPhotoPath
     * @Description: 根据Uri得到该图片的路径(兼容4.4)
     * @return: String
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPhotoPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        if (widthOrg >= edgeLength && heightOrg >= edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;

            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }
            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;

            try {
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
//                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        } else {
            Bitmap scaledBitmap;
            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, edgeLength, edgeLength, true);
                result = Bitmap.createBitmap(scaledBitmap, 0, 0, edgeLength, edgeLength);
//                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }

        }

        return result;
    }

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    private static final String SD_PATH = "/sdcard/DCIM/Camera/";
    private static final String IN_PATH = "/DCIM/Camera/";

    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    public static String saveBitmap(Context context, Bitmap mBitmap) {
        if (mBitmap == null) return null;
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
//           String pathname = savePath + generateFileName() + ".jpg";
            savePath = savePath + generateFileName() + ".jpg";
           /*  filePic = new File(pathname);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();*/

//            MediaStore.Images.Media.insertImage(context.getContentResolver(), filePic.getAbsolutePath(), pathname, null);
            MediaStore.Images.Media.insertImage(context.getContentResolver(), mBitmap, savePath, null);
//            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePic.getAbsolutePath())));
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" +savePath)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//        return filePic.getAbsolutePath();
        return savePath;
    }

    public static String insertImageW(ContentResolver cr, String imagePath, String name, String description)
            throws FileNotFoundException {
        // Check if file exists with a FileInputStream
        FileInputStream stream = new FileInputStream(imagePath);
        try {
            Bitmap bm = BitmapFactory.decodeFile(imagePath);
            String ret = insertImageT(cr, bm, name, description);
            bm.recycle();
            return ret;
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }

    public static String insertImageT(ContentResolver cr, Bitmap source, String title, String description) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        Uri url = null;
        String stringUrl = null; /* value to be returned */

        try {
            String CONTENT_AUTHORITY_SLASH = "content://" + "media" + "/";
            Uri uri = Uri.parse(CONTENT_AUTHORITY_SLASH + "external" + "/images/media");
            url = cr.insert(uri, values);
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }

        return stringUrl;
    }

    public static String saveGifBitmap(Context context, Bitmap mBitmap) {
        if (mBitmap == null) return null;
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            String pathname = savePath + generateFileName() + ".gif";
            filePic = new File(pathname);
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            MediaStore.Images.Media.insertImage(context.getContentResolver(), filePic.getAbsolutePath(), pathname, null);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filePic.getAbsolutePath())));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    /**
     * @param imageView 图片控件
     * @param path      图片地址
     * @param id        为空是加载本地图片
     * @param isRound   是否为园
     */
    public static void setImagaImg(ImageView imageView, String path, int id, boolean isRound) {
        if (StringUtil.isEmpty(path)) {
            imageView.setImageResource(id);
        } else {
            MyAppliction.getInstance().displayImages(imageView, path, isRound);
        }
    }

}
