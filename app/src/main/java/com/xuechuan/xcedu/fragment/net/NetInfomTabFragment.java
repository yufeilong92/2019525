package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.event.ScrollEvent;
import com.xuechuan.xcedu.ui.net.HomeBookInfomActivity;
import com.xuechuan.xcedu.weight.ScrollWebView;

import org.greenrobot.eventbus.EventBus;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetInfomTabFragment
 * @Package com.xuechuan.xcedu.fragment.home
 * @Description: 资讯详情
 * @author: L-BackPacker
 * @date: 2018.11.26 下午 2:02
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018.11.26
 */
public class NetInfomTabFragment extends BaseFragment implements View.OnClickListener {
    private static final String MURL = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mUrl;
    private String mParam2;
    private Context mContext;
    private ScrollWebView mWebNetInfomContent;
    private HomeBookInfomActivity mHomeBookInfomActivity;

    public static NetInfomTabFragment newInstance(String url, String param2) {
        NetInfomTabFragment fragment = new NetInfomTabFragment();
        Bundle args = new Bundle();
        args.putString(MURL, url);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(MURL);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mHomeBookInfomActivity = (HomeBookInfomActivity) context;
    }
    /*    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_infom_tab, container, false);
        initView(view);
        return view;
    }*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_infom_tab;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        initData();
    }


    private void initData() {
        setWebVIewSetting(mWebNetInfomContent);
        mWebNetInfomContent.loadUrl(mUrl);
    }

    private void initView(View view) {
        mContext = getActivity();
        mWebNetInfomContent = (ScrollWebView) view.findViewById(R.id.web_net_infom_content);
        mWebNetInfomContent.setOnClickListener(this);

        mWebNetInfomContent.setOnScrollChangeListener(new ScrollWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {
                //底部
            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                //顶部
                if (l == -1000)
                    EventBus.getDefault().postSticky(new ScrollEvent("top"));
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                //滑动中
                Log.e("===", "onScrollChanged: " + l + t + oldl + oldt);
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

}
