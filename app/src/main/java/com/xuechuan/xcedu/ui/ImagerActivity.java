package com.xuechuan.xcedu.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.luck.picture.lib.photoview.PhotoView;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;

import java.io.File;

public class ImagerActivity extends AppCompatActivity implements View.OnClickListener {

    private PhotoView mPhoneView;

    private static String PARAMT_KEY = "com.xuechuan.xcedu.uiImagerActivity.paramt_key";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.uiImagerActivity.paramt_key1";
    private ImageView mIcBack;
    private String mPath;
    private int mType;
    public static final int NET = 2;
    public static final int URI = 1;

    public static Intent start_Intent(Context context, String path, int type) {
        Intent intent = new Intent(context, ImagerActivity.class);
        intent.putExtra(PARAMT_KEY, path);
        intent.putExtra(PARAMT1_KEY, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imager);
        if (getIntent() != null) {
            mPath = getIntent().getStringExtra(PARAMT_KEY);
            mType = getIntent().getIntExtra(PARAMT1_KEY, 0);
        }
        initView();
        initData();
    }

    private void initData() {
        switch (mType) {
            case URI:
                Uri uri = getImageContentUri(this, new File(mPath));
                mPhoneView.setImageURI(uri);
                break;
            case NET:
                MyAppliction.getInstance().displayImages(mPhoneView,mPath,false);
                break;

        }


    }

    private void initView() {
        mPhoneView = (PhotoView) findViewById(R.id.phoneView);

        mIcBack = (ImageView) findViewById(R.id.ic_back);
        mIcBack.setOnClickListener(this);
        mPhoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ic_back:
                this.finish();
                break;
        }
    }

    public static Uri getImageContentUri(Context context, java.io.File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

}
