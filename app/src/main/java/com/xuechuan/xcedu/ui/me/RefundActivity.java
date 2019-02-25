package com.xuechuan.xcedu.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.adapter.RefundCauneAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.DialogBgUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.GiftVo;
import com.xuechuan.xcedu.vo.SelectRefundVo;
import com.xuechuan.xcedu.weight.CommonPopupWindow;
import com.xuechuan.xcedu.weight.ContentEditText;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: RefundActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 退款界面
 * @author: L-BackPacker
 * @date: 2018/8/20 16:22
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/8/20
 */
public class RefundActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIvTakeBook;
    private ImageView mIvTakeGift;
    private TextView mTvTakeValue;
    private TextView mTvTakeNumber;
    private ImageButton mIvRefundRed;
    private TextView mTvRefundNumber;
    private ImageButton mIvRefundAdd;
    private TextView mTvRefundCause;
    private TextView mTvRefundValue;
    private TextView mTvRefundValueAmount;
    private TextView mTvRefundPhone;
    private EditText mEtRefundPhone;
    private Button mBtnRefundPost;
    private ContentEditText mEtRefundContent;
    private LinearLayout mLlRemarks;
    private LinearLayout mRefundRoot;
    private TextView mTvTakeTitle;
    private ImageView mIvGoodStatus;


/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        initView();
    }*/

    private static String TAG_MARK = "tag_mark";
    public static final String TAKESTATUS = "daifahuo";
    public static final String TAKESTATUSYI = "yifahuo";
    private static final String INFOMDATA = "infomdata";
    private CommonPopupWindow popRefundCause;
    private Context mContext;
    private String mTag;
    private GiftVo mInfom;

    public static Intent newInstance(Context context, String tag_mark, GiftVo obj) {
        Intent intent = new Intent(context, RefundActivity.class);
        intent.putExtra(TAG_MARK, tag_mark);
        intent.putExtra(INFOMDATA, (Serializable) obj);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_refund);
        if (getIntent() != null) {
            mTag = getIntent().getStringExtra(TAG_MARK);
            mInfom = (GiftVo) getIntent().getSerializableExtra(INFOMDATA);
        }
        initView();
        initData();
    }

    private void initData() {
        if (mTag.equalsIgnoreCase(TAKESTATUS)) {//代发货
            mLlRemarks.setVisibility(View.GONE);
        } else if (mTag.equalsIgnoreCase(TAKESTATUSYI)) {//已发货
            mLlRemarks.setVisibility(View.VISIBLE);
        }
        if (mInfom != null) {
            mIvTakeGift.setVisibility(mInfom.isMainProducts() ? View.GONE : View.VISIBLE);
            MyAppliction.getInstance().displayImages(mIvTakeBook, mInfom.getCoverimg(), false);
            mTvTakeTitle.setText(mInfom.getName());
            mTvRefundNumber.setText(String.valueOf(mInfom.getNum()));
            mTvRefundValue.setText(String.valueOf(mInfom.getPrice()));
        }
    }

    private void initView() {
        mContext = this;
        mIvTakeBook = (ImageView) findViewById(R.id.iv_take_book);
        mIvTakeGift = (ImageView) findViewById(R.id.iv_take_gift);
        mTvTakeValue = (TextView) findViewById(R.id.tv_take_value);
        mTvTakeNumber = (TextView) findViewById(R.id.tv_take_number);
        mIvRefundRed = (ImageButton) findViewById(R.id.iv_refund_red);
        mIvRefundRed.setOnClickListener(this);
        mTvRefundNumber = (TextView) findViewById(R.id.tv_refund_number);
        mIvRefundAdd = (ImageButton) findViewById(R.id.iv_refund_add);
        mIvRefundAdd.setOnClickListener(this);
        mTvRefundCause = (TextView) findViewById(R.id.tv_refund_cause);
        mTvRefundCause.setOnClickListener(this);
        mTvRefundValue = (TextView) findViewById(R.id.tv_refund_value);
        mTvRefundValueAmount = (TextView) findViewById(R.id.tv_refund_value_amount);
        mTvRefundPhone = (TextView) findViewById(R.id.tv_refund_phone);
        mEtRefundPhone = (EditText) findViewById(R.id.et_refund_phone);
        mBtnRefundPost = (Button) findViewById(R.id.btn_refund_post);

        mBtnRefundPost.setOnClickListener(this);
        mEtRefundContent = (ContentEditText) findViewById(R.id.et_refund_content);
        mEtRefundContent.setOnClickListener(this);
        mLlRemarks = (LinearLayout) findViewById(R.id.ll_remarks);
        mLlRemarks.setOnClickListener(this);
        mRefundRoot = (LinearLayout) findViewById(R.id.refund_root);
        mRefundRoot.setOnClickListener(this);
        mTvTakeTitle = (TextView) findViewById(R.id.tv_take_title);
        mTvTakeTitle.setOnClickListener(this);
        mIvGoodStatus = (ImageView) findViewById(R.id.iv_good_status);
        mIvGoodStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refund_post://提交
                submit();
                break;
            case R.id.tv_refund_cause://退款原因
                showpopRefundCauseLayout();
                break;
            case R.id.iv_refund_red:
                String str = getTextStr(mTvRefundNumber);
                redOrAdd(str, false);
                break;
            case R.id.iv_refund_add:
                if (mInfom.getNum() == Integer.parseInt(getTextStr(mTvRefundNumber))) {
                    return;
                }
                String add = getTextStr(mTvRefundNumber);
                redOrAdd(add, true);
                break;
        }
    }

    private void redOrAdd(String str, boolean isAddOrRed) {
        int i = Integer.parseInt(str);
        if (i == 0 && !isAddOrRed) {
            return;
        }
        if (isAddOrRed)
            ++i;
        else
            --i;

        mTvRefundNumber.setText(String.valueOf(i));
    }

    private void submit() {
        String textStr = getTextStr(mTvRefundCause);
        if (StringUtil.isEmpty(textStr)) {
            T.showToast(mContext, getString(R.string.please_select_refund));
            return;
        }
        String phone = getTextStr(mTvRefundPhone);
        if (StringUtil.isEmpty(phone)) {
            T.showToast(mContext, getStringWithId(R.string.please_input_phone));
            return;
        }
        String number = getTextStr(mTvRefundNumber);
        if (number.equals("0")){
           T.showToast(mContext,"请选择退款数量");
            return;
        }


    }

    /**
     * 设置答题卡布局
     */
    private void showpopRefundCauseLayout() {
        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels;
        popRefundCause = new CommonPopupWindow(this, R.layout.pop_item_refund_cause, ViewGroup.LayoutParams.MATCH_PARENT, (int) (screenHeight * 0.7)) {
            private RecyclerView mRecyclerView;

            @Override
            protected void initView() {
                View view = getContentView();
                mRecyclerView = (RecyclerView) view.findViewById(R.id.rlv_case_data);
            }

            @Override
            protected void initEvent() {
                String cause = getTextStr(mTvRefundCause);
                ArrayList<SelectRefundVo> list = getRefundList(mContext, R.array.refund_case, cause);
                bindRefundCaseAdapter(list);
            }

            @Override
            protected void initWindow() {
                super.initWindow();
                PopupWindow instance = getPopupWindow();
                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        DialogBgUtil.setBackgroundAlpha(1f, mContext);
                    }
                });
            }

            private void bindRefundCaseAdapter(ArrayList<SelectRefundVo> list) {
                RefundCauneAdapter adapter = new RefundCauneAdapter(mContext, list);
                GridLayoutManager manager = new GridLayoutManager(mContext, 1);
                manager.setOrientation(GridLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(manager);
                mRecyclerView.setAdapter(adapter);
                adapter.setClickListener(new RefundCauneAdapter.onItemClickListener() {
                    @Override
                    public void onClickListener(SelectRefundVo obj, int position) {
                        mTvRefundCause.setText(obj.getTitle());
                        popRefundCause.getPopupWindow().dismiss();
                    }
                });

            }
        };
        popRefundCause.showAtLocation(mRefundRoot, Gravity.BOTTOM, 0, 0);
        DialogBgUtil.setBackgroundAlpha(0.5f, RefundActivity.this);
    }

    /**
     * 获取用户选中
     *
     * @param context
     * @param id
     * @return
     */
    public static ArrayList<SelectRefundVo> getRefundList(Context context, int id, String title) {
        ArrayList<String> list = ArrayToListUtil.arraytoList(context, id);
        ArrayList<SelectRefundVo> listRefunds = new ArrayList<>();
        if (StringUtil.isEmpty(title)) {
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                SelectRefundVo selectRefundVo = new SelectRefundVo();
                selectRefundVo.setTitle(s);
                selectRefundVo.setSelect(false);
                listRefunds.add(selectRefundVo);
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                String s = list.get(i);
                SelectRefundVo selectRefundVo = new SelectRefundVo();
                selectRefundVo.setTitle(s);
                selectRefundVo.setSelect(title.equalsIgnoreCase(s) ? true : false);
                listRefunds.add(selectRefundVo);
            }
        }

        return listRefunds;
    }


}
