package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.AddressAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.fragment.home.HomesFragment;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.RecyclerSelectItem;
import com.xuechuan.xcedu.vo.ProvinceEvent;
import com.xuechuan.xcedu.vo.ProvincesVo;
import com.xuechuan.xcedu.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: AddressSelectActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 定位选择页
 * @author: L-BackPacker
 * @date: 2018/4/14 15:49
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/14
 */
public class AddressShowActivity extends BaseActivity {
    private RecyclerView mRlvSelAdress;
    private Context mContext;
    /**
     * 地区
     */
    private static String ADDRESS = "address";
    private String mAddress;
    private int mPosition;
    /**
     * 位标
     */
    private static String POSITONTYPE = "type";
    /**
     * 首页
     */
    public static String TYPEHOME = "typehome";
    /**
     * 列表
     */
    public static String TYPELIST = "typelist";
    private ArrayList<ProvincesVo> list;
    private String mTypeLocation;

    public static Intent newInstance(Context context, String address, String type) {
        Intent intent = new Intent(context, AddressShowActivity.class);
        intent.putExtra(ADDRESS, address);
        intent.putExtra(POSITONTYPE, type);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_address_select);
        if (getIntent() != null) {
            mAddress = getIntent().getStringExtra(ADDRESS);
            mTypeLocation = getIntent().getStringExtra(POSITONTYPE);
        }
        initView();
        initData();
        bingView();
    }


    private void initData() {
        if (list == null) {
            list = PushXmlUtil.getInstance().pushXml(mContext);
        } else {
            list.clear();
            list = PushXmlUtil.getInstance().pushXml(mContext);
        }
    }

    private void initView() {
        mContext = this;
        mRlvSelAdress = (RecyclerView) findViewById(R.id.rlv_sel_adress);
    }

    private void bingView() {
        AddressAdapter addressAdapter = new AddressAdapter(mContext, list);
        GridLayoutManager gridLayoutManager = getGridLayoutManger(mContext, mRlvSelAdress, 1);
        mRlvSelAdress.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.BOTH_SET, R.drawable.recyclerline));
        mRlvSelAdress.setAdapter(addressAdapter);
        int mPosition = getPersionPosition(mAddress, list);
        addressAdapter.setSelectItem(mAddress, mPosition);
        gridLayoutManager.scrollToPositionWithOffset(mPosition,0);
        RecyclerSelectItem.MoveToPostion(gridLayoutManager, mRlvSelAdress, mPosition);
        addressAdapter.setOnClickListener(new AddressAdapter.AddressOnClickListener() {
            @Override
            public void onClickListener(ProvincesVo vo, int position) {
                toIntent(vo, position);
            }
        });
    }

    private void toIntent(ProvincesVo vo, int position) {
        if (mTypeLocation.equals(TYPEHOME)) {
            Intent intent = new Intent();
            intent.putExtra(HomesFragment.STR_INT_PROVINCE, vo.getName());
            intent.putExtra(HomesFragment.STR_INT_CODE, vo.getCode());
            intent.putExtra(HomesFragment.STR_INT_POSITION, position);
            setResult(HomesFragment.REQUESTRESULT, intent);
            finish();
        }if (mTypeLocation.equals(TYPELIST)){
                EventBus.getDefault().postSticky(new ProvinceEvent(vo.getName(),vo.getCode()));
            finish();
        }
    }

    private int getPersionPosition(String mAddress, ArrayList<ProvincesVo> list) {
        if (mAddress == null || list == null) {
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            ProvincesVo vo = list.get(i);
            if (vo.getName().equals(mAddress)) {
                return i;
            }
        }
        return -1;
    }


}
