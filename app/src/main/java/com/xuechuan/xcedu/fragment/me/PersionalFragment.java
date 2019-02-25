package com.xuechuan.xcedu.fragment.me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.mvp.contract.PersionInfomContract;
import com.xuechuan.xcedu.mvp.model.PersionInfomModel;
import com.xuechuan.xcedu.mvp.presenter.PersionInfomPresenter;
import com.xuechuan.xcedu.net.BitmapService;
import com.xuechuan.xcedu.ui.me.AddVauleActivity;
import com.xuechuan.xcedu.ui.me.ExamHistoryActivity;
import com.xuechuan.xcedu.ui.me.FeedBackActivity;
import com.xuechuan.xcedu.ui.me.GenuineActivity;
import com.xuechuan.xcedu.ui.me.MyMsgActivity;
import com.xuechuan.xcedu.ui.me.MyOrderActivity;
import com.xuechuan.xcedu.ui.me.PersionActivity;
import com.xuechuan.xcedu.ui.me.SettingActivity;
import com.xuechuan.xcedu.ui.me.SysDataActivity;
import com.xuechuan.xcedu.ui.me.SystemMsgActivity;
import com.xuechuan.xcedu.ui.me.TakeDeliveryActivity;
import com.xuechuan.xcedu.ui.net.NetBookDownActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.ImageUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.AdvisorBean;
import com.xuechuan.xcedu.vo.PerInfomVo;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PersionalFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 我的信息
 * @author: L-BackPacker
 * @date: 2018/4/11 17:15
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/11
 */
public class PersionalFragment extends BaseFragment implements View.OnClickListener, PersionInfomContract.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 权限
     */
    private int persion = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;
    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;
    private String mParam1;
    private String mParam2;
    private ImageView mIvMHear;
    private TextView mTvMName;
    private ImageView mIvMEdith;
    private TextView mTvMPhone;
    private LinearLayout mLlMHear;
    private LinearLayout mLlMMyMsg;
    private LinearLayout mLlMDown;
    private LinearLayout mLlMOrder;
    private LinearLayout mLlMAddvaluer;
    private LinearLayout mLlMGenuine;
    private LinearLayout mLlMFeedback;
    private LinearLayout mLlMNotice;
    private LinearLayout mLlMSetting;
    private Context mContext;
    private PerInfomVo.DataBean mDataInfom;
    private PersionInfomPresenter mPresenter;
    private ImageView mIvPersionImg;
    private ImageView mIvMPSystem;
    private ImageView mIvMPTiShi;
    private LinearLayout mLlMErwerima;
    private String path;
    private TextView mTvMMyTeacher;
    private LinearLayout mLlMTearcher;
    private LinearLayout mLIRootUser;
    private LinearLayout mLlMSynchrodata;
    private LinearLayout mLlMExamLog;
    private LinearLayout mLlRootUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public PersionalFragment() {
    }

    public static PersionalFragment newInstance(String param1, String param2) {
        PersionalFragment fragment = new PersionalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*   @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
           View view = inflater.inflate(R.layout.fragment_persional, container, false);
           initView(view);
           return view;
       }
   */
    @Override
    protected int initInflateView() {
        return R.layout.fragment_persional;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }

    private void initData() {
        if (mPresenter == null) {
            mPresenter = new PersionInfomPresenter();
            mPresenter.basePresenter(new PersionInfomModel(), this);
        }
    }

    private void initView(View view) {
        mContext = getActivity();
        mIvMHear = (ImageView) view.findViewById(R.id.iv_m_hear);
        mLIRootUser = (LinearLayout) view.findViewById(R.id.ll_root_user);
        mTvMName = (TextView) view.findViewById(R.id.tv_m_name);
        mIvMEdith = (ImageView) view.findViewById(R.id.iv_m_edith);
        mIvMEdith.setOnClickListener(this);
        mTvMPhone = (TextView) view.findViewById(R.id.tv_m_phone);
        mLlMHear = (LinearLayout) view.findViewById(R.id.ll_m_hear);
        mLlMHear.setOnClickListener(this);
        mLlMMyMsg = (LinearLayout) view.findViewById(R.id.ll_m_my_msg);
        mLlMMyMsg.setOnClickListener(this);
        mLlMDown = (LinearLayout) view.findViewById(R.id.ll_m_down);
        mLlMDown.setOnClickListener(this);
        mLlMOrder = (LinearLayout) view.findViewById(R.id.ll_m_order);
        mLlMOrder.setOnClickListener(this);
        mLlMAddvaluer = (LinearLayout) view.findViewById(R.id.ll_m_addvaluer);
        mLlMAddvaluer.setOnClickListener(this);
        mLlMGenuine = (LinearLayout) view.findViewById(R.id.ll_m_genuine);
        mLlMGenuine.setOnClickListener(this);
        mLlMFeedback = (LinearLayout) view.findViewById(R.id.ll_m_feedback);
        mLlMFeedback.setOnClickListener(this);
        mLlMNotice = (LinearLayout) view.findViewById(R.id.ll_m_notice);
        mLlMNotice.setOnClickListener(this);
        mLlMSetting = (LinearLayout) view.findViewById(R.id.ll_m_setting);
        mLlMSetting.setOnClickListener(this);

        mIvPersionImg = (ImageView) view.findViewById(R.id.iv_persion_img);
        mIvMPSystem = (ImageView) view.findViewById(R.id.iv_m_p_system);
        mIvMPSystem.setOnClickListener(this);
        mIvMPTiShi = (ImageView) view.findViewById(R.id.iv_m_p_ti_shi);
        mIvMPTiShi.setOnClickListener(this);
        mLlMErwerima = (LinearLayout) view.findViewById(R.id.ll_m_erwerima);
        mLlMErwerima.setOnClickListener(this);
        mTvMMyTeacher = (TextView) view.findViewById(R.id.tv_m_my_teacher);
        mLlMTearcher = (LinearLayout) view.findViewById(R.id.ll_m_tearcher);
        mLlMTearcher.setOnClickListener(this);
        mLlMSynchrodata = (LinearLayout) view.findViewById(R.id.ll_m_synchrodata);
        mLlMSynchrodata.setOnClickListener(this);
        mLlMExamLog = (LinearLayout) view.findViewById(R.id.ll_m_exam_log);
        mLlMExamLog.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_m_hear://编辑
                if (!Utils.isNet(mContext)) {
                    T_ERROR(mContext);
                    return;
                }
                Intent intent = new Intent(mContext, PersionActivity.class);
                intent.putExtra(PersionActivity.PERINFOM, mDataInfom);
                intent.putExtra(PersionActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.persion));
                 startActivity(intent);
                break;
            case R.id.ll_m_my_msg://我的信息
                Intent intent6 = new Intent(mContext, MyMsgActivity.class);
                intent6.putExtra(MyMsgActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.mymsg_notice));
                startActivity(intent6);
                break;
            case R.id.ll_m_down://我的下载
                 startActivity(new Intent(mContext, NetBookDownActivity.class));
                break;
            case R.id.ll_m_order://我的订单
               startActivity(new Intent(mContext, MyOrderActivity.class));
                break;
            case R.id.ll_m_addvaluer://增值服务
                Intent intent1 = new Intent(mContext, AddVauleActivity.class);
                intent1.putExtra(AddVauleActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(mContext, R.string.addvuale));
               startActivity(intent1);
                break;
            case R.id.ll_m_genuine://正版验证
                Intent intent2 = new Intent(mContext, GenuineActivity.class);
                intent2.putExtra(GenuineActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(mContext, R.string.authentic_verification));
               startActivity(intent2);
                break;
            case R.id.ll_m_feedback://用户反馈
                Intent intent3 = new Intent(mContext, FeedBackActivity.class);
                intent3.putExtra(FeedBackActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(mContext, R.string.feedbBack));
                startActivity(intent3);
                break;
            case R.id.ll_m_notice://系统通告
                Intent intent5 = new Intent(mContext, SystemMsgActivity.class);
                intent5.putExtra(SystemMsgActivity.CSTR_EXTRA_TITLE_STR, getString(R.string.system_notice));
               startActivity(intent5);
                break;
            case R.id.ll_m_setting://设置
                Intent intent4 = new Intent(mContext, SettingActivity.class);
                if (mDataInfom != null) {
                    List<PerInfomVo.DataBean.ThirdaccountBean> thirdaccount = mDataInfom.getThirdaccount();
                    if (thirdaccount != null && !thirdaccount.isEmpty()) {
                        intent4.putExtra(SettingActivity.WEIXINNAME, mDataInfom.getThirdaccount().get(0).getNickname());
                    }
                }
                intent4.putExtra(SettingActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(mContext, R.string.setting));
                startActivity(intent4);
                break;
            case R.id.ll_m_erwerima://扫一扫
                Intent star = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(star, REQUEST_CODE);
                break;
            case R.id.ll_m_tearcher://班主任
                showMyTeacherDialog();
                break;
            case R.id.ll_m_synchrodata://同步数据
                if (mDataInfom != null) {
                    Intent intent7 = SysDataActivity.start_Intent(mContext, mDataInfom.getId());
                    intent7.putExtra(SysDataActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.synchrodata));
                    startActivity(intent7);
                }
                break;
            case R.id.ll_m_exam_log://考试记录
                Intent intent8 = ExamHistoryActivity.start_Intent(mContext, "", "");
                intent8.putExtra(ExamHistoryActivity.CSTR_EXTRA_TITLE_STR, getStrWithId(R.string.exam_log));
                startActivity(intent8);
                break;

        }
    }

    private AlertDialog mDownDialog;

    private void showMyTeacherDialog() {
        if (mDataInfom == null) {
            T_ERROR(mContext, getStrWithId(R.string.loading));
            return;
        }
        AdvisorBean advisor = mDataInfom.getAdvisor();
        if (advisor == null) {
            T_ERROR(mContext, "暂无任课老师");
            return;
        }
        DialogUtil.ShowTeacherVo vo = new DialogUtil.ShowTeacherVo(advisor.getHeadimg(), advisor.getName(), advisor.getTelphone(), advisor.getWechatimg());
//        DialogUtil.ShowTeacherVo vo = new DialogUtil.ShowTeacherVo(mDataInfom.getHeadicon(), "于飞龙", "18317837451", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2168427908,4072089613&fm=200&gp=0.jpg");
        DialogUtil.showTeacherDialog(mContext, vo, new DialogUtil.OnDownClickListener() {


            @Override
            public void onClickDown(String path) {
                mDownDialog = DialogUtil.showDialog(mContext, "", "下载中...");
                saveBitmap(path);
            }

            @Override
            public void onClickMsg() {
                T.showToast(mContext, getString(R.string.down_error));
            }

            @Override
            public void onClickPhone(String phone) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);
            }
        });

    }

    private void saveBitmap(String path) {
        final BitmapService service = BitmapService.get_Instance(mContext);
        service.requestBitmap(path, new BitmapService.BitmapInfaceCallBack() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                dialogDimiss(mDownDialog);
                String newpath = ImageUtil.saveBitmap(mContext, bitmap);
                final Snackbar snackbar = Snackbar.make(mLIRootUser, "", Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(mContext.getResources().getColor(R.color.red_text));
                snackbar.getView().setBackgroundColor(mContext.getResources().getColor(R.color.text_fu_color));
                snackbar.setAction("查看", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_IMAGE);
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
                T.showToast(mContext, getString(R.string.save_success));
            }

            @Override
            public void onError(String msg) {
                dialogDimiss(mDownDialog);
                T.showToast(mContext, getString(R.string.down_error));
            }
        });
    }

    private void openImgDilaog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_evweuna, null);
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

            }
        });
        btnOpenCame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                openImg();
            }
        });
    }

    @Override
    public void InfomSuccess(String cont) {
        L.d(cont);
        Gson gson = new Gson();
        PerInfomVo vo = gson.fromJson(cont, PerInfomVo.class);
        if (vo == null) return;
        if (vo.getStatus() != null && vo.getStatus().getCode() == 200) {
            mDataInfom = vo.getData();
            MyAppliction.getInstance().setUserData(vo);
            bindViewData(mDataInfom);
            bindMyTeacher(mDataInfom.getAdvisor());
        } else {
            T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
            L.e(vo.getStatus().getMessage());
        }

    }

    private void bindMyTeacher(AdvisorBean advisor) {
        if (advisor != null)
            mTvMMyTeacher.setText(advisor.getName());
    }

    private void bindViewData(PerInfomVo.DataBean data) {
        String nickname = data.getNickname();
        if (nickname.length() > 7) {
            nickname = data.getNickname().substring(0, 7);
            nickname = nickname + "...";
        }
        mTvMName.setText(nickname);
        mTvMPhone.setText(Utils.phoneData(data.getPhone()));
        if (!StringUtil.isEmpty(data.getHeadicon())) {
            MyAppliction.getInstance().displayImages(mIvMHear, data.getHeadicon(), true);
        } else {
            mIvMHear.setImageResource(R.mipmap.common_icon_defaultpicture_mini);
        }
        if (data.isIshavemembernotify()) {
            mIvPersionImg.setImageResource(R.mipmap.m_icon_massage_n);
        } else {
            mIvPersionImg.setImageResource(R.mipmap.ic_m_massage);
        }

        if (data.isIshavesystemnotify()) {
            mIvMPTiShi.setImageResource(R.mipmap.common_rp);
        } else {
            mIvMPTiShi.setVisibility(View.GONE);
        }
/*        if (data.isIshavewaitaddress()) {
            showDailog();
        }*/


    }

    /**
     * 显示订单确认按钮
     */
    private void showDailog() {
        DialogUtil dialogUtil = DialogUtil.getInstance();
        dialogUtil.showTitleDialog(mContext, getString(R.string.order_infom_1), getStrWithId(R.string.sure),
                getStrWithId(R.string.cancel), true);
        dialogUtil.setTitleClickListener(new DialogUtil.onTitleClickListener() {
            @Override
            public void onSureClickListener() {
                Intent intent = TakeDeliveryActivity.newInstance(mContext, "");
                startActivity(intent);
            }

            @Override
            public void onCancelClickListener() {

            }
        });

    }

    @Override
    public void InfomError(String cont) {
        L.d(cont);
        T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isVisible) return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.reqeustMInfo(mContext);
            }
        }, 200);
    }

    private List<LocalMedia> selectList = new ArrayList<>();

    private void openImg() {
        PictureSelector.create(getActivity())
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_Sina_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
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
//                .compressSavePath(getPath())//压缩图片保存地址
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
//                .selectionMedia(selectList)// 是否传入已选图片
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectList) {
                        path = media.getPath();
                        CodeUtils.analyzeBitmap(path, new CodeUtils.AnalyzeCallback() {
                            @Override
                            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                                T.showToast(mContext, "解析结果:" + result);
                            }

                            @Override
                            public void onAnalyzeFailed() {
                                T.showToast(mContext, "解析二维码失败");
                            }
                        });
                    }
                    break;
            }
            /**
             * 处理二维码扫描结果
             */
            if (requestCode == REQUEST_CODE) {
                //处理扫描结果（在界面上显示）
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        T.showToast(mContext, "解析结果:" + result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        T.showToast(mContext, "解析二维码失败:");
                    }
                }
            }
        }
    }

    private boolean isVisible = false;
    /**
     * 是否第一次请求
     */
    private boolean isRequestHttp = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            if (isRequestHttp) return;
            isRequestHttp = true;
            if (mPresenter == null) return;
            mPresenter.reqeustMInfo(mContext);
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isRequestHttp = false;
    }
}
