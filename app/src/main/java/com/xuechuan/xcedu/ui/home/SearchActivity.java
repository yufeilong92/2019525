package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.HomeReasultViewPagAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.BaseVo;
import com.xuechuan.xcedu.fragment.home.ArticleReasultFragment;
import com.xuechuan.xcedu.fragment.bank.BankReasultFragment;
import com.xuechuan.xcedu.mvp.model.SearchModelImpl;
import com.xuechuan.xcedu.mvp.presenter.SearchPresenter;
import com.xuechuan.xcedu.mvp.view.SearchView;
import com.xuechuan.xcedu.net.HomeService;
import com.xuechuan.xcedu.net.view.StringCallBackView;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveHistoryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.HotKeyVo;
import com.xuechuan.xcedu.weight.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxx
 * @Title: SearchActivity
 * @Package com.xuechuan.xcedu.ui
 * @Description: 搜索页
 * @author: YFL
 * @date: 2018/4/15  14:29
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/4/15
 */
public class SearchActivity extends BaseActivity implements View.OnClickListener, SearchView {

    private static String ARG_PARAM1 = "arg_param1";
    private static String ARG_PARAM2 = "arg_param2";
    private EditText mEtSearch;
    private ImageView mIvSearch;
    private ImageView mIvSearchClear;
    private RelativeLayout mRlSearchHistory;
    private SaveHistoryUtil mInstance;
    private Context mContext;
    private FlowLayout mFlSearchHistory;
    private FlowLayout mFlSearchHost;
    private LayoutInflater mInflater;
    private LinearLayout mLiSearchHistory;
    private ViewPager mVpSearchReasult;
    private LinearLayout mLiSearchReasult;
    private TabLayout mTabTitleContent;
    private SearchPresenter mPresenter;
    //
    private List<String> mLists = null;

    public static void newInstance(Context context, String param1, String param2) {
        Intent intent = new Intent();
        intent.putExtra(ARG_PARAM1, param1);
        intent.putExtra(ARG_PARAM2, param2);
        context.startActivity(intent);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search);
//        initView();
//    }

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        initView();
        initData();
        bindHistoryData();
//        bingHostData();
        bindReasultData();

    }



    private void bindReasultData() {
        ArrayList<Fragment> list = new ArrayList<>();
        ArticleReasultFragment reasultFragment = new ArticleReasultFragment();
        BankReasultFragment bankReasultFragment = new BankReasultFragment();
        list.add(reasultFragment);
        list.add(bankReasultFragment);
        setViewPageAdapter(list);
    }

    private void setViewPageAdapter(ArrayList<Fragment> list) {
        if (list.size() < 2) {
            mTabTitleContent.setVisibility(View.GONE);
        } else {
            mTabTitleContent.setVisibility(View.VISIBLE);
        }
        ArrayList<String> titles = ArrayToListUtil.arraytoList(mContext, R.array.search_reasult);

        FragmentManager fragmentManager = getSupportFragmentManager();
        HomeReasultViewPagAdapter adapter = new HomeReasultViewPagAdapter(fragmentManager, mContext, list, titles);
        mVpSearchReasult.setAdapter(adapter);
        mTabTitleContent.setupWithViewPager(mVpSearchReasult);
    }

    /**
     * 绑定历史
     */
    private void bindHistoryData() {
        ArrayList<String> list = mInstance.getHistoryList(mContext);
        if (list.size() > 0)
            BuildTextViewData(mFlSearchHistory, list);
    }

    private void initData() {
        mInflater = LayoutInflater.from(mContext);
        mInstance = SaveHistoryUtil.getInstance(mContext);
        mPresenter = new SearchPresenter(new SearchModelImpl(), this);
        mPresenter.requestHostList(mContext, "10");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    search();
                    Utils.hideInputMethod(mContext,mEtSearch);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 绑定热搜
     */
    // TODO: 2018/4/19 处理缓存问题 
    private void bingHostData() {
        HomeService service = HomeService.getInstance(mContext);
        service.requestHost("10", new StringCallBackView() {

            @Override
            public void onSuccess(String hot) {
//                String hot = response.body().toString();
                L.d("获取热词数据" + hot);
                Gson gson = new Gson();
                HotKeyVo vo = gson.fromJson(hot, HotKeyVo.class);
                BaseVo.StatusBean status = vo.getStatus();

                if (status.getCode() == 200) {
                    List<String> datas = vo.getDatas();
                    BuildTextViewData(mFlSearchHost, datas);
                } else {
                    T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//                    T.showToast(mContext, response.message());
                }
            }

            @Override
            public void onError(String response) {
                T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
//                T.showToast(mContext, response.message());
            }
        });

    }

    /**
     * 创建历史记录按钮
     *
     * @param flowLayout
     * @param list
     */
    private void BuildTextViewData(FlowLayout flowLayout, List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            TextView tv = (TextView) mInflater.inflate(R.layout.search_label_tv,
                    flowLayout, false);
            tv.setText(list.get(i));

            final String str = tv.getText().toString();
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //加入搜索历史纪录记录
//                    T.showToast(mContext, str);
                    starResult(str);
                }
            });
            flowLayout.addView(tv);
        }
    }


    private void initView() {
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mIvSearch = (ImageView) findViewById(R.id.iv_search);
        mIvSearchClear = (ImageView) findViewById(R.id.iv_search_clear);
        mIvSearchClear.setOnClickListener(this);
        mRlSearchHistory = (RelativeLayout) findViewById(R.id.rl_search_history);
        mIvSearch.setOnClickListener(this);
        mContext = this;
        mFlSearchHistory = (FlowLayout) findViewById(R.id.fl_search_history);
        mFlSearchHistory.setOnClickListener(this);
        mFlSearchHost = (FlowLayout) findViewById(R.id.fl_search_host);
        mFlSearchHost.setOnClickListener(this);
        mLiSearchHistory = (LinearLayout) findViewById(R.id.li_search_history);
        mLiSearchHistory.setOnClickListener(this);
        mVpSearchReasult = (ViewPager) findViewById(R.id.vp_search_reasult);
        mVpSearchReasult.setOnClickListener(this);
        mLiSearchReasult = (LinearLayout) findViewById(R.id.li_search_reasult);
        mLiSearchReasult.setOnClickListener(this);
        mTabTitleContent = findViewById(R.id.tab_title_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                search();
                break;
            case R.id.iv_search_clear:
                mInstance.delete(mContext);
                mFlSearchHistory.removeAllViews();
                break;
            default:
                break;
        }
    }

    private void search() {
        String trim = mEtSearch.getText().toString().trim();
        if (StringUtil.isEmpty(trim)) {
            T.showToast(mContext, "内容不能为空");
            return;
        }
        mEtSearch.setText(null);
        mFlSearchHistory.removeAllViews();
        mInstance.saveHistory(trim);
        bindHistoryData();
        starResult(trim);
    }

    private void starResult(String trim) {
        Intent intent = SearchResultActivity.newInsanter(mContext, trim);
        startActivity(intent);
        finish();
    }


    @Override
    public void HostSuccess(String cont) {
        L.d("获取热词数据" + cont);
        Gson gson = new Gson();
        HotKeyVo vo = gson.fromJson(cont, HotKeyVo.class);
        BaseVo.StatusBean status = vo.getStatus();
        if (status.getCode() == 200) {
            clearData();
            addList(vo.getDatas());
            mFlSearchHost.removeAllViews();
            BuildTextViewData(mFlSearchHost, mLists);
        } else {

//            T.showToast(mContext, status.getMessage());
            T.showToast(mContext, getStringWithId(R.string.net_error));
        }
    }

    @Override
    public void HostError(String cont) {
        L.e(cont);
    }

    @Override
    public void ResultSuccess(String cont) {

    }

    @Override
    public void ResultError(String cont) {

    }

    @Override
    public void ResultMoreSuccess(String cont) {

    }

    @Override
    public void ResultMoreError(String cont) {

    }

    private void clearData() {
        if (mLists == null) {
            mLists = new ArrayList<>();
        } else {
            mLists.clear();
        }
    }

    private void addList(List list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mLists == null) {
            clearData();
        }
        mLists.addAll(list);
    }
}
