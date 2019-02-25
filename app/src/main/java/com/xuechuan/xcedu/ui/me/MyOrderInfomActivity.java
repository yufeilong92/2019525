package com.xuechuan.xcedu.ui.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.vo.MyOrderNewVo;
import com.xuechuan.xcedu.vo.MyOrderVo;
import com.xuechuan.xcedu.vo.OrderDetailVo;

import java.io.Serializable;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: MyOrderInfomActivity
 * @Package com.xuechuan.xcedu.ui.me
 * @Description: 订单详情
 * @author: L-BackPacker
 * @date: 2018/5/26 16:39
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/26
 */
public class MyOrderInfomActivity extends BaseActivity {

    private TextView mTvOrdetinfomName;
    private TextView mTvOrdetinfomCode;
    private TextView mTvOrdetinfomTime;
    private TextView mTvOrdetinfomPrice;
    private TextView mTvOrdetinfomType;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_order_infom);
//        initView();
//    }
    private static String ORDERTINFOM = "ordertinfom";
    private MyOrderVo.DatasBean mInfom;

    public static Intent newInstance(Context context, MyOrderVo.DatasBean ordertinfom) {
        Intent intent = new Intent(context, MyOrderInfomActivity.class);
        intent.putExtra(ORDERTINFOM, (Serializable) ordertinfom);
        return intent;
    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_order_infom);
        if (getIntent() != null) {
            mInfom = (MyOrderVo.DatasBean) getIntent().getSerializableExtra(ORDERTINFOM);
        }
        initView();
        intData();

    }

    private void intData() {
        mTvOrdetinfomCode.setText(mInfom.getOrdernum());
        StringBuilder builder = new StringBuilder();
        List<OrderDetailVo> details = mInfom.getDetails();
        for (int i = 0; i < details.size(); i++) {
            if (i == details.size() - 1) {
                builder.append(details.get(i));
            } else {
                builder.append(details.get(i) + "\n");
            }
        }
        mTvOrdetinfomName.setText(builder.toString());
        mTvOrdetinfomTime.setText(mInfom.getCreatetime());
        mTvOrdetinfomPrice.setText((mInfom.getTotalprice()-mInfom.getDiscounts())+"");
        mTvOrdetinfomType.setText("");

    }

    private void initView() {
        mTvOrdetinfomName = (TextView) findViewById(R.id.tv_ordetinfom_name);
        mTvOrdetinfomCode = (TextView) findViewById(R.id.tv_ordetinfom_code);
        mTvOrdetinfomTime = (TextView) findViewById(R.id.tv_ordetinfom_time);
        mTvOrdetinfomPrice = (TextView) findViewById(R.id.tv_ordetinfom_price);
        mTvOrdetinfomType = (TextView) findViewById(R.id.tv_ordetinfom_type);
    }

}
