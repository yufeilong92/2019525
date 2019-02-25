package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.andview.refreshview.callback.IFooterCallBack;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.MainActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.PersionContract;
import com.xuechuan.xcedu.mvp.model.PersionModel;
import com.xuechuan.xcedu.mvp.presenter.PersionPresenter;
import com.xuechuan.xcedu.service.SubmitHearService;
import com.xuechuan.xcedu.service.SubmitProgressService;
import com.xuechuan.xcedu.utils.AddressPickTask;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.FileUtil;
import com.xuechuan.xcedu.utils.ImageUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.PerInfomVo;
import com.xuechuan.xcedu.vo.ResultVo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnLinkageListener;
import cn.addapp.pickers.picker.AddressPicker;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.util.ConvertUtils;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PersionActivity
 * @Package com.xuechuan.xcedu.ui.user
 * @Description: 个人信息修改页
 * @author: L-BackPacker
 * @date: 2018/5/22 9:25
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/22
 */
public class PersionActivity extends BaseActivity implements View.OnClickListener, PersionContract.View {


    private TextView mTvMPSubmit;
    private ImageView mIvMPImg;
    private TextView mEtMPName;
    private LinearLayout mLlMPName;
    private LinearLayout mLiMPSex;
    private TextView mTvMPBirthday;
    private LinearLayout mLlMPBirthday;
    private TextView mTvMPCity;
    private LinearLayout mLlMPCity;
    private TextView mTvMPPhone;
    private LinearLayout mLlMPPhone;
    private RelativeLayout mRlMPPhone;
    private int themid = R.style.picture_Sina_style;
    private List<LocalMedia> selectList = new ArrayList<>();
    private Context mContext;
    private String mProvince;
    private String mCity;
    /**
     * 权限
     */
    private int persion = 111;
    private RadioButton mRdbMPBoy;
    private RadioButton mRdbMPGirl;
    private PersionPresenter mPresenter;
    public static String PERINFOM = "pertype";
    private PerInfomVo.DataBean mInfomVo;
    private AlertDialog mDialog;
    private ArrayList<String> mPathlist;
    private String mPath;
    private AlertDialog imgDialgo;
    private AlertDialog dialog;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion);
        initView();
    }*/


    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_persion);
        if (getIntent() != null) {
            mInfomVo = (PerInfomVo.DataBean) getIntent().getSerializableExtra(PERINFOM);
        }
        initView();
        initData();
    }

    private void initData() {
        mPresenter = new PersionPresenter();
        mPresenter.BasePersion(new PersionModel(), this);
        if (mInfomVo != null) {
            if (!StringUtil.isEmpty(mInfomVo.getHeadicon()))
                MyAppliction.getInstance().displayImages(mIvMPImg, mInfomVo.getHeadicon(), true);
            mEtMPName.setText(mInfomVo.getNickname());
            int gender = mInfomVo.getGender();
            if (gender == 1) {
                mRdbMPBoy.setChecked(true);
            } else if (gender == 2) {
                mRdbMPGirl.setChecked(true);
            }
            String birthday = mInfomVo.getBirthday();
            mTvMPBirthday.setText(birthday);
            mProvince = mInfomVo.getProvince();
            mCity = mInfomVo.getCity();
            mTvMPCity.setText(mInfomVo.getCity());
            mTvMPPhone.setText(mInfomVo.getPhone());
        }

    }

/*
    private String phoneData(String phone) {
        String str = "****";
        StringBuilder sb = new StringBuilder(phone);
        sb.replace(3, 7, str);
        return sb.toString();
    }
*/


    private void initView() {
        mContext = this;
        mTvMPSubmit = (TextView) findViewById(R.id.tv_m_p_submit);
        mTvMPSubmit.setOnClickListener(this);
        mIvMPImg = (ImageView) findViewById(R.id.iv_m_p_img);
        mEtMPName = (EditText) findViewById(R.id.et_m_p_name);
        mLlMPName = (LinearLayout) findViewById(R.id.ll_m_p_name);
        mLiMPSex = (LinearLayout) findViewById(R.id.li_m_p_sex);
        mTvMPBirthday = (TextView) findViewById(R.id.tv_m_p_birthday);
        mLlMPBirthday = (LinearLayout) findViewById(R.id.ll_m_p_birthday);
        mLlMPBirthday.setOnClickListener(this);
        mTvMPCity = (TextView) findViewById(R.id.tv_m_p_city);
        mLlMPCity = (LinearLayout) findViewById(R.id.ll_m_p_city);
        mLlMPCity.setOnClickListener(this);
        mTvMPPhone = (TextView) findViewById(R.id.tv_m_p_phone);
        mLlMPPhone = (LinearLayout) findViewById(R.id.ll_m_p_phone);
        mRlMPPhone = (RelativeLayout) findViewById(R.id.rl_m_p_phone);
        mRlMPPhone.setOnClickListener(this);
        mRdbMPBoy = (RadioButton) findViewById(R.id.rdb_m_p_boy);
        mRdbMPBoy.setOnClickListener(this);
        mRdbMPGirl = (RadioButton) findViewById(R.id.rdb_m_p_girl);
        mRdbMPGirl.setOnClickListener(this);
    }


    private void submit() {
        int gender = -1;
        String name = getTextStr(mEtMPName);
        if (StringUtil.isEmpty(name)) {
            T.showToast(mContext, getString(R.string.name_empty));
            return;
        }
        if (mRdbMPBoy.isChecked()) {
            gender = 1;
        }
        if (mRdbMPGirl.isChecked()) {
            gender = 2;
        }
        String brithday = getTextStr(mTvMPBirthday);
        if (mPathlist != null && !mPathlist.isEmpty())
            mPresenter.submitPersionHear(mContext, mPathlist);
        dialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
        mPresenter.submitPersionInfom(mContext, name, gender, brithday, mProvince, mCity);
    }

    private void selectTime() {
   /*     DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showSelectTime(this, true);
        dialogUtil.setSelectTimeListener(new DialogUtil.onTimeClickListener() {
            @Override
            public void onTimeListener(String time) {
                mTvMPBirthday.setText(time);
            }
        });*/
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        DatePicker picker = new DatePicker(this);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(1911, 1, 1);
        picker.setRangeEnd(3000, 1, 1);
        picker.setSelectedItem(year, month + 1, date);
        picker.setWeightEnable(true);
        picker.setLineColor(Color.WHITE);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                mTvMPBirthday.setText(year + "-" + month + "-" + day);
            }
        });
        picker.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_m_p_birthday://时间
                selectTime();
                break;
            case R.id.rl_m_p_phone://头像
                if (EasyPermissions.hasPermissions(PersionActivity.this
                        , DataMessageVo.Persmission[0], DataMessageVo.Persmission[1], DataMessageVo.Persmission[3])) {
                    showOpenAlbum();
                } else {
                    PermissionRequest build = new PermissionRequest.Builder(PersionActivity.this,
                            persion, DataMessageVo.Persmission[0], DataMessageVo.Persmission[1], DataMessageVo.Persmission[3])
                            .setNegativeButtonText(R.string.cancel)
                            .setPositiveButtonText(R.string.allow)
                            .build();
                    EasyPermissions.requestPermissions(build);
                }
                break;
            case R.id.ll_m_p_city:
                if (!Utils.handleOnDoubleClick()) {
                    showCity();
                }
                break;
            case R.id.tv_m_p_submit://提交
                submit();
                break;

            default:

        }
    }

    private void showCity() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(true);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                T.showToast(mContext, "数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    mTvMPCity.setText(city.getAreaName());
                    mProvince = province.getAreaName();
                    mCity = city.getAreaName();
                } else {
                    mProvince = province.getAreaName();
                    mCity = city.getAreaName();
                    mTvMPCity.setText(city.getAreaName());
                }
            }
        });
        task.execute(mProvince, mCity, "");
    }

    private void showOpenAlbum() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_opnealbum, null);
        Button btnOpenCame = view.findViewById(R.id.btn_open_came);
        Button btnOpenAum = view.findViewById(R.id.btn_open_alum);
        builder.setCancelable(true);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        btnOpenAum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                openAlbum();
            }
        });
        btnOpenCame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startCame();
            }
        });
    }

    private void openAlbum() {

        PictureSelector.create(PersionActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themid)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
//                .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
//                .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                .isCamera(false)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                .enableCrop(cb_crop.isChecked())// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(480, 480)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
//                .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
//                .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .cropCompressQuality(90)// 裁剪压缩质量 默认100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled(true) // 裁剪是否可旋转图片
                //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()//显示多少秒以内的视频or音频也可适用
                //.recordVideoSecond()//录制视频秒数 默认60s
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    public void startCame() {
        // 单独拍照
        PictureSelector.create(PersionActivity.this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                .theme(themid)// 主题样式设置 具体参考 values/styles
                .maxSelectNum(1)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
//               .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
//               .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                .isCamera(true)// 是否显示拍照按钮
//               .enableCrop(cb_crop.isChecked())// 是否裁剪
                .compress(true)// 是否压缩
                .glideOverride(480, 480)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
//               .isGif(cb_isGif.isChecked())// 是否显示gif图片
//               .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
                .circleDimmedLayer(true)// 是否圆形裁剪
//               .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//               .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//               .openClickSound(cb_voice.isChecked())// 是否开启点击声音
                .selectionMedia(selectList)// 是否传入已选图片
                .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                .cropCompressQuality(90)// 裁剪压缩质量 默认为100
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()////显示多少秒以内的视频or音频也可适用
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private String getPath() {
        String dbDir = android.os.Environment.getExternalStorageDirectory().toString();
        dbDir += "/xuechuan";
        dbDir += "/image";//数据库所在目录
        String dbPath = dbDir;//数据库路径
        //判断目录是否存在，不存在则创建该目录
//        File dirFile = new File(dbDir);
        File dbFile = new File(dbPath);
        if (!dbFile.exists())
            dbFile.mkdirs();
        //数据库文件是否创建成功
        boolean isFileCreateSuccess = false;
        //判断文件是否存在，不存在则创建该文件
        if (!dbFile.exists()) {
            try {
                isFileCreateSuccess = dbFile.createNewFile();//创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File outfile = null;
        if (isFileCreateSuccess) {
            outfile = dbFile;
        } else {
            outfile = new File("/sdcard/image");
        }
        // 如果文件不存在，则创建一个新文件
        if (!outfile.isDirectory()) {
            try {
                outfile.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outfile.getPath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                        mPath = media.getPath();
                        imgDialgo = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
                        getPath(mPath);
                    /*    if (media.isCompressed()) {
                            L.d("图片压缩后的路径====" + media.getCompressPath());
                            Bitmap bitmap = BitmapFactory.decodeFile(media.getPath());
                            mIvMPImg.setImageBitmap(bitmap);
                            mPathlist = new ArrayList<>();
                            mPathlist.add(media.getCompressPath());
                        }*/
                    }
                    break;
            }
        }
    }

    private void getPath(final String paths) {
        //因为压缩是一个耗时的过程，所以采用异步的方式
        new Thread((new Runnable() {
            @Override
            public void run() {
                String path = ImageUtil.getSmallBitmap(paths);
                if (!StringUtil.isEmpty(mPath)) {
                    mPath = path;
                }
                handler.sendEmptyMessage(2);
            }
        })).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                if (imgDialgo != null && imgDialgo.isShowing())
                    imgDialgo.dismiss();
                if (mPath != null) {
//                Bitmap bitmap = BitmapFactory.decodeFile(mPath);
//                mIvMPImg.setImageBitmap(bitmap);
                    MyAppliction.getInstance().displayImages(mIvMPImg, "file://" + mPath, true);
                    mPathlist = new ArrayList<>();
                    mPathlist.add(mPath);
                }
            }

        }
    };

    @Override
    public void SubmitPersionSuccess(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                finish();
                T.showToast(mContext, getStringWithId(R.string.sava_success));
            } else
                T.showToast(mContext, vo.getData().getMessage());
        } else {
            T.showToast(mContext, getString(R.string.net_error));
        }
    }


    @Override
    public void SubmitPersionError(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
    }

    @Override
    public void SubmitPersionHearScu(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        Gson gson = new Gson();
        ResultVo vo = gson.fromJson(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() != 1) {
                T.showToast(mContext, vo.getData().getMessage());
                return;
            }
            File file = new File(mPath);
            file.delete();
            T.showToast(mContext, "头像上传成功，等待管理员审核");
            SubmitHearService.startActionBaz(mContext, 10000, "上传完成");
        } else {
            SubmitHearService.startActionBaz(mContext, 10000, "上传失败,是否重新上传");
        }
    }

    @Override
    public void SubmitPersionHearErr(String con) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        T.showToast(mContext, getStringWithId(R.string.net_error));
        L.d(con);
    }

    @Override
    public void SubmitProgressHear(Progress progress) {
        SubmitHearService.startActionBaz(mContext, (int) (progress.fraction * 10000), "头像上传中...");
    }


}
