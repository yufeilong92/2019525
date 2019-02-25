package com.xuechuan.xcedu.ui.wenda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.model.Progress;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.contract.ProblemTagContract;
import com.xuechuan.xcedu.mvp.contract.WenDaSubmitContract;
import com.xuechuan.xcedu.mvp.model.ProblemTagModel;
import com.xuechuan.xcedu.mvp.model.WenDaSubmitModel;
import com.xuechuan.xcedu.mvp.presenter.ProblemTagPresenter;
import com.xuechuan.xcedu.mvp.presenter.WenDaSubmitPresenter;
import com.xuechuan.xcedu.ui.ImagerActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.ImageUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.ProblemTagVo;
import com.xuechuan.xcedu.vo.ResultVo;
import com.xuechuan.xcedu.weight.FlowLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: CreateProblemActivity
 * @Package com.xuechuan.xcedu.ui.wenda
 * @Description: 提交问题界面
 * @author: L-BackPacker
 * @date: 2019.01.10 下午 2:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2019.01.10
 */
public class CreateProblemActivity extends BaseActivity implements WenDaSubmitContract.View, View.OnClickListener, ProblemTagContract.View {

    private ImageView mIvImgone;
    private ImageView mIvImgtwo;
    private ImageView mIvImgthree;
    private FlowLayout mFlProblemContent;
    private EditText mEtInputProblem;
    private int themid = R.style.picture_Sina_style;
    private Context mContext;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String mPath;
    private AlertDialog imgDialgo;
    private List<String> mPathlist;
    private boolean mImgOne = false, mImgTwo = false, mImgThree = false;
    private ImageView mIvImgoneDelete;
    private ImageView mIvImgtwoDelete;
    private ImageView mIvImgthreeDelete;


    private static String PARAMT_KEY = "com.xuechuan.xcedu.ui.wendaCreateProblemActivity.paramt_key";
    private static String PARAMT1_KEY = "com.xuechuan.xcedu.ui.wendaCreateProblemActivity.paramt_key1";
    private DialogUtil mDialogUtil;
    private LayoutInflater mInflater;
    private WenDaSubmitPresenter mPresenter;
    private List<Integer> mSelectLists;
    private boolean mIsFirst;
    private Button mBtnCreateProblemSubmit;
    private int mProblemId;
    private AlertDialog mShowDialog;
    private ProblemTagPresenter mProblemPresenter;
    private ArrayList<String> mImagerPaths;

    public static Intent start_Intent(Context context, int problemid, boolean isfirst) {
        Intent intent = new Intent(context, CreateProblemActivity.class);
        intent.putExtra(PARAMT_KEY, problemid);
        intent.putExtra(PARAMT1_KEY, isfirst);
        return intent;
    }

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_problem);
        initView();
    }
*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_problem);
        if (getIntent() != null) {
            mIsFirst = getIntent().getBooleanExtra(PARAMT1_KEY, true);
            mProblemId = getIntent().getIntExtra(PARAMT_KEY, 0);
        }
        initView();
        initUtils();
        initData();
    }

    private void initUtils() {
        mSelectLists = new ArrayList<>();
        mDialogUtil = DialogUtil.getInstance();
        mPresenter = new WenDaSubmitPresenter();
        mPresenter.initModelView(new WenDaSubmitModel(), this);

    }

    private void initData() {
        mProblemPresenter = new ProblemTagPresenter();
        mProblemPresenter.initModelView(new ProblemTagModel(), this);
        mProblemPresenter.requestProblemtags(mContext);

    }


    private void initView() {
        mContext = this;
        mIvImgone = (ImageView) findViewById(R.id.iv_imgone);
        mIvImgone.setOnClickListener(this);
        mIvImgtwo = (ImageView) findViewById(R.id.iv_imgtwo);
        mIvImgtwo.setOnClickListener(this);
        mIvImgthree = (ImageView) findViewById(R.id.iv_imgthree);
        mIvImgthree.setOnClickListener(this);
        mFlProblemContent = (FlowLayout) findViewById(R.id.fl_problem_content);
        mEtInputProblem = (EditText) findViewById(R.id.et_input_problem);
        mEtInputProblem.setOnClickListener(this);
        mIvImgoneDelete = (ImageView) findViewById(R.id.iv_imgone_delete);
        mIvImgoneDelete.setOnClickListener(this);
        mIvImgtwoDelete = (ImageView) findViewById(R.id.iv_imgtwo_delete);
        mIvImgtwoDelete.setOnClickListener(this);
        mIvImgthreeDelete = (ImageView) findViewById(R.id.iv_imgthree_delete);
        mIvImgthreeDelete.setOnClickListener(this);
        mBtnCreateProblemSubmit = (Button) findViewById(R.id.btn_create_problem_submit);
        mBtnCreateProblemSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_imgone:
                if (!mImgOne) {
                    showDialog();
                } else {
                    Intent intent = ImagerActivity.start_Intent(mContext, mPathlist.get(0), ImagerActivity.URI);
                    startActivity(intent);
                }
                break;
            case R.id.iv_imgtwo:
                if (!mImgTwo) {
                    showDialog();
                } else {
                    Intent intent = ImagerActivity.start_Intent(mContext, mPathlist.get(1), ImagerActivity.URI);
                    startActivity(intent);
                }
                break;
            case R.id.iv_imgthree:
                if (!mImgThree) {
                    showDialog();
                } else {
                    Intent intent = ImagerActivity.start_Intent(mContext, mPathlist.get(2), ImagerActivity.URI);
                    startActivity(intent);
                }
                break;
            case R.id.iv_imgone_delete://删除
                showDeleteDialog(0);
                break;
            case R.id.iv_imgtwo_delete:
                showDeleteDialog(1);
                break;
            case R.id.iv_imgthree_delete:
                showDeleteDialog(2);
                break;
            case R.id.btn_create_problem_submit://提交
                String trim = mEtInputProblem.getText().toString().trim();
                if (StringUtil.isEmpty(trim)) {
                    T.showToast(mContext, getStringWithId(R.string.input_content));
                    return;
                } else {
                    mShowDialog = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.submit_loading));
                    StringBuffer buffer = new StringBuffer();
                    if (mSelectLists != null) {
                        for (int i = 0; i < mSelectLists.size(); i++) {
                            if (i == mSelectLists.size() - 1) {
                                buffer.append(i);
                            } else {
                                buffer.append(i);
                                buffer.append(",");
                            }
                        }
                    }
                    doSubmitEvent(trim, buffer.toString());
                }
                break;
        }
    }

    private void doSubmitEvent(String trim, String tages) {
        if (mIsFirst) {
            mPresenter.submtiProblem(mContext, 0, trim, mPathlist, tages);
        } else {
            mPresenter.submtiProblem(mContext, mProblemId, trim, mPathlist, tages);
        }
    }


    /**
     * 权限
     */
    private int persion = 111;

    private void showDialog() {
        if (EasyPermissions.hasPermissions(CreateProblemActivity.this
                , DataMessageVo.Persmission[0], DataMessageVo.Persmission[1], DataMessageVo.Persmission[3])) {
            showOpenAlbum();
        } else {
            PermissionRequest build = new PermissionRequest.Builder(CreateProblemActivity.this,
                    persion, DataMessageVo.Persmission[0], DataMessageVo.Persmission[1], DataMessageVo.Persmission[3])
                    .setNegativeButtonText(R.string.cancel)
                    .setPositiveButtonText(R.string.allow)
                    .build();
            EasyPermissions.requestPermissions(build);
        }
    }

    private void showDeleteDialog(final int index) {
        mDialogUtil.showTitleDialog(mContext, getStringWithId(R.string.suredelete), getStringWithId(R.string.delect),
                getStringWithId(R.string.cancel), true);
        mDialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                String s = mImagerPaths.get(index);
                mPathlist.remove(index);
                if (selectList != null && !selectList.isEmpty())
                    for (int i = 0; i < selectList.size(); i++) {
                        LocalMedia media = selectList.get(i);
                        if (media.getPath().equals(s)) {
                            selectList.remove(i);
                        }
                    }
                mImagerPaths.remove(index);
                if (mPathlist == null || mPathlist.isEmpty()) {
                    mImgOne = false;
                    mImgTwo = false;
                    mImgThree = false;
                    showAddImg(true, false, false, false, false, false);
                    return;
                }
                setImg(mPathlist);
            }

            @Override
            public void onCancelClickListener() {

            }
        });


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

        PictureSelector.create(CreateProblemActivity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(themid)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(3)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
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
        PictureSelector.create(CreateProblemActivity.this)
                .openCamera(PictureMimeType.ofImage())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
                /*  .theme(themid)// 主题样式设置 具体参考 values/styles
                  .maxSelectNum(3)// 最大图片选择数量
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
                  .minimumCompressSize(100)// 小于100kb的图片不压缩*/
                //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                //.rotateEnabled() // 裁剪是否可旋转图片
                //.scaleEnabled()// 裁剪是否可放大缩小图片
                //.videoQuality()// 视频录制质量 0 or 1
                //.videoSecond()////显示多少秒以内的视频or音频也可适用
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    private String getPath() {
        String dbDir = Environment.getExternalStorageDirectory().toString();
        dbDir += "/xuechuan/image/";
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
                    imgDialgo = DialogUtil.showDialog(mContext, "", getStringWithId(R.string.loading));
                    if (mImagerPaths == null)
                        mImagerPaths = new ArrayList<>();
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
//                        mPath = media.getPath();
//                        getPath(mPath);
                        if (!mImagerPaths.contains(media.getPath())) {
                            mImagerPaths.add(media.getPath());
                        }
                    /*    if (media.isCompressed()) {
                            L.d("图片压缩后的路径====" + media.getCompressPath());
                            Bitmap bitmap = BitmapFactory.decodeFile(media.getPath());
                            mIvMPImg.setImageBitmap(bitmap);
                            mPathlist = new ArrayList<>();
                            mPathlist.add(media.getCompressPath());
                        }*/
                    }
                    getPath(mImagerPaths);
                    break;
            }
        }
    }

    private void getPath(final ArrayList<String> paths) {
        //因为压缩是一个耗时的过程，所以采用异步的方式
        new Thread((new Runnable() {
            @Override
            public void run() {
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < paths.size(); i++) {
                    String s = paths.get(i);
                    String path = ImageUtil.getSmallBitmap(s);
                    list.add(path);
                }
                ;
                Message message = new Message();
                message.what = 2;
                message.obj = list;
                handler.sendMessage(message);
//                handler.sendEmptyMessage(2);
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
                ArrayList<String> list = (ArrayList<String>) msg.obj;
                setImg(list);
            }

        }
    };

    public void setImg(List<String> list) {
        if (list != null && !list.isEmpty()) {
            switch (list.size()) {
                case 1:
                    MyAppliction.getInstance().displayImages(mIvImgone, "file://" + list.get(0), false);
                    mImgOne = true;
                    mImgTwo = false;
                    mImgThree = false;
                    showAddImg(true, true, true, false, false, false);
                    break;
                case 2:
                    MyAppliction.getInstance().displayImages(mIvImgone, "file://" + list.get(0), false);
                    MyAppliction.getInstance().displayImages(mIvImgtwo, "file://" + list.get(1), false);
                    mImgOne = true;
                    mImgTwo = true;
                    mImgThree = false;
                    showAddImg(true, true, true, true, true, false);
                    break;
                case 3:
                    MyAppliction.getInstance().displayImages(mIvImgone, "file://" + list.get(0), false);
                    MyAppliction.getInstance().displayImages(mIvImgtwo, "file://" + list.get(1), false);
                    MyAppliction.getInstance().displayImages(mIvImgthree, "file://" + list.get(2), false);
                    mImgOne = true;
                    mImgTwo = true;
                    mImgThree = true;
                    showAddImg(true, true, true, true, true, true);
                    break;

            }
            doSubmitProblem(list);
        }
    }

    private void doSubmitProblem(List<String> list) {
        this.mPathlist = list;
    }

    private void showAddImg(boolean one, boolean delone, boolean two, boolean deltwo, boolean three, boolean detthree) {
        mIvImgone.setVisibility(one ? View.VISIBLE : View.INVISIBLE);
        mIvImgoneDelete.setVisibility(delone ? View.VISIBLE : View.GONE);
        if (!delone)
            mIvImgone.setImageResource(R.mipmap.ask_addimg);
        mIvImgtwo.setVisibility(two ? View.VISIBLE : View.INVISIBLE);
        if (!deltwo) {
            mIvImgtwo.setImageResource(R.mipmap.ask_addimg);
        }
        mIvImgtwoDelete.setVisibility(deltwo ? View.VISIBLE : View.GONE);
        mIvImgthree.setVisibility(three ? View.VISIBLE : View.INVISIBLE);
        if (!detthree) {
            mIvImgthree.setImageResource(R.mipmap.ask_addimg);
        }
        mIvImgthreeDelete.setVisibility(detthree ? View.VISIBLE : View.GONE);
    }

    @Override
    public void SubmitProblemScu(String con) {
        dismissDialog(mShowDialog);
        ResultVo vo = Utils.getGosnT(con, ResultVo.class);
        if (vo.getStatus().getCode() == 200) {
            if (vo.getData().getStatusX() == 1) {
                T.showToast(mContext, getStringWithId(R.string.submit_success));
                if (mPathlist != null && !mPathlist.isEmpty()) {
                    for (int i = 0; i < mPathlist.size(); i++) {
                        String s = mPathlist.get(i);
                        File file = new File(s);
                        file.delete();
                    }
                }
                mImagerPaths = null;
                mPathlist = null;
                this.finish();

            }
        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void SubmitProblemErr(String con) {
        dismissDialog(mShowDialog);
        T_ERROR(mContext);
    }

    @Override
    public void SubmitProgressHearImg(Progress progress) {

    }

    @Override
    public void ProblemTagSuccess(String success) {
        ProblemTagVo vo = Utils.getGosnT(success, ProblemTagVo.class);
        if (vo.getStatus().getCode() == 200) {
            List<ProblemTagVo.DatasBean> datas = vo.getDatas();
            if (datas == null || datas.isEmpty()) return;
            addTagViewData(datas);

        } else {
            T_ERROR(mContext);
        }
    }

    @Override
    public void ProblemTagError(String msg) {
        T_ERROR(mContext);
    }

    private void addTagViewData(List<ProblemTagVo.DatasBean> list) {
        mInflater = LayoutInflater.from(mContext);
        for (int i = 0; i < list.size(); i++) {
            final ProblemTagVo.DatasBean bean = list.get(i);
            View itemvo = mInflater.inflate(R.layout.itme_create_tag, mFlProblemContent, false);
            final CheckBox checkBox = (CheckBox) itemvo.findViewById(R.id.tv_problem_tag);
            checkBox.setText(bean.getValue());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkBox.setTextColor(isChecked ? mContext.getResources().getColor(R.color.red_text) :
                            mContext.getResources().getColor(R.color.text_fu_color));
                    if (isChecked) {
                        mSelectLists.add(bean.getId());
                    } else {
                        if (mSelectLists.contains(bean.getId())) {
                            mSelectLists.remove(bean.getId());
                        }
                    }
                }
            });

            mFlProblemContent.addView(itemvo);
        }
    }
}
