package com.xuechuan.xcedu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuechuan.xcedu.event.SearchEvenText;
import com.xuechuan.xcedu.event.SearchEventWen;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.home.MyNetBookIndicatorAdapter;
import com.xuechuan.xcedu.adapter.home.MyTagPagerAdapter;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.fragment.home.ResultAtirleFragment;
import com.xuechuan.xcedu.fragment.home.ResultBooksFragment;
import com.xuechuan.xcedu.fragment.home.ResultNetFragment;
import com.xuechuan.xcedu.fragment.home.ResultQuestionFragment;
import com.xuechuan.xcedu.utils.ArrayToListUtil;
import com.xuechuan.xcedu.utils.SaveHistoryUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.Utils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: SearchResultActivity
 * @Package com.xuechuan.xcedu.ui.home
 * @Description: 搜索结果
 * @author: L-BackPacker
 * @date: 2018/5/8 18:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/8
 */
public class SearchResultActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout mTabTitleContent;
    private ViewPager mVpSearchReasult;


    /**
     * 搜索
     */
    private static String SEARCHKEY = "key";
    private String mSearchKey;
    private Context mContext;
    private EditText mEtResulteSearch;
    private ImageView mIvResulteSearch;
    private MagicIndicator mMagicIndicator;
    private SaveHistoryUtil mInstance;

    public static Intent newInsanter(Context context, String content) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(SEARCHKEY, content);
        return intent;
    }

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_result);
        if (getIntent() != null) {
            mSearchKey = getIntent().getStringExtra(SEARCHKEY);
        }
        initView();
        initIndicator();
        mInstance = SaveHistoryUtil.getInstance(mContext);
    }


    private void initIndicator() {
        ArrayList<String> list = ArrayToListUtil.arraytoList(mContext, R.array.search_reasult);
        mMagicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.25f);
        if (list.size() > 4) {
            commonNavigator.setAdjustMode(false);
        } else {
            commonNavigator.setAdjustMode(true);
        }
        MyNetBookIndicatorAdapter adapter = new MyNetBookIndicatorAdapter(list, mVpSearchReasult);
        mMagicIndicator.setNavigator(commonNavigator);
        commonNavigator.setAdapter(adapter);
        List<Fragment> fragments = creartFragment(list);
        MyTagPagerAdapter tagPagerAdapter = new MyTagPagerAdapter(getSupportFragmentManager(), fragments);
        mVpSearchReasult.setAdapter(tagPagerAdapter);
        mVpSearchReasult.setOffscreenPageLimit(4);
        ViewPagerHelper.bind(mMagicIndicator, mVpSearchReasult);

    }

    private List<Fragment> creartFragment(List<String> list) {
        if (list.size() < 2) {
            mMagicIndicator.setVisibility(View.GONE);
        }
        List<Fragment> fragments = new ArrayList<>();
        //题库
        ResultQuestionFragment questionFragment = ResultQuestionFragment.newInstance(mSearchKey, DataMessageVo.QUESTION);
        //文章
        ResultAtirleFragment fragment = ResultAtirleFragment.newInstance(mSearchKey, DataMessageVo.ARTICLE);

        // TODO: 2018.11.22  初始化图书和网课
        //图书
        ResultBooksFragment booksFragment = ResultBooksFragment.newInstance(mSearchKey, DataMessageVo.BOOK);
        //网课
        ResultNetFragment netFragment = ResultNetFragment.newInstance(mSearchKey, DataMessageVo.CLASS);
        fragments.add(questionFragment);
        fragments.add(fragment);
        fragments.add(booksFragment);
        fragments.add(netFragment);
        return fragments;
    }


    private void initView() {
        mContext = this;
        mVpSearchReasult = (ViewPager) findViewById(R.id.vp_search_reasult);
        mEtResulteSearch = (EditText) findViewById(R.id.et_resulte_search);
        mIvResulteSearch = (ImageView) findViewById(R.id.iv_resulte_search);
        mMagicIndicator = (MagicIndicator) findViewById(R.id.magicIndicator);
        mIvResulteSearch.setOnClickListener(this);
        mEtResulteSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    Utils.hideInputMethod(mContext, mEtResulteSearch);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_resulte_search://搜搜
                search();
                break;
            default:

        }
    }

    private void search() {
        String str = getTextStr(mEtResulteSearch);
        if (StringUtil.isEmpty(str)) {
            T.showToast(mContext, "内容不能为空");
            return;
        }
        mSearchKey = getTextStr(mEtResulteSearch);
        mInstance.saveHistory(str);
        EventBus.getDefault().postSticky(new SearchEvenText(mSearchKey));
        EventBus.getDefault().postSticky(new SearchEventWen(mSearchKey));

    }

}
