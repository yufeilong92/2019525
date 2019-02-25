package com.xuechuan.xcedu.fragment.net;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;
import com.multilevel.treelist.Node;
import com.multilevel.treelist.OnTreeNodeClickListener;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.adapter.net.NetBookTableTreeAdapter;
import com.xuechuan.xcedu.base.BaseFragment;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.mvp.model.NetBookInfomModelImpl;
import com.xuechuan.xcedu.mvp.presenter.NetBookInfomPresenter;
import com.xuechuan.xcedu.mvp.view.NetBookInfomView;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.vo.ChaptersBeanVo;
import com.xuechuan.xcedu.vo.NetBookTableVo;
import com.xuechuan.xcedu.vo.VideosBeanVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: NetBooKListFragment
 * @Package com.xuechuan.xcedu.fragment
 * @Description: 未购买课程表。
 * @author: L-BackPacker
 * @date: 2018/5/14 17:07
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */
public class NetBooKListFragment extends BaseFragment implements NetBookInfomView {
    private static final String CLASSID = "classid";

    private String mCalssId;
    /***
     * 添加集合
     */
    private List<Node> mArrary = new ArrayList<>();
    private NetBookInfomPresenter mPresenter;
    private Context mContext;
    private NetBookTableTreeAdapter adapter;
    private ListView mLvNetBookContent;
    /**
     * 是否加载更多
     */
    boolean isLoading;
    /**
     * 记录数据
     */
    private List<ChaptersBeanVo> mArrayData;

    public NetBooKListFragment() {
    }


    public static NetBooKListFragment newInstance(String calssid) {
        NetBooKListFragment fragment = new NetBooKListFragment();
        Bundle args = new Bundle();
        args.putString(CLASSID, calssid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCalssId = getArguments().getString(CLASSID);
        }
    }
/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_net_boo_klist, container, false);
        initView(view);
        return view;
    }
*/

    @Override
    protected int initInflateView() {
        return R.layout.fragment_net_boo_klist;
    }

    @Override
    protected void initCreateView(View view, Bundle savedInstanceState) {
        initView(view);
        clearData();
        clearDataNumber();
        initData();
    }


    private void initData() {
        mPresenter = new NetBookInfomPresenter(new NetBookInfomModelImpl(), this);
        mPresenter.requestVideoBookOneList(mContext, 1, mCalssId);

    }


    private void initView(View view) {
        mContext = getActivity();
        mLvNetBookContent = (ListView) view.findViewById(R.id.lv_net_book_content);
    }

    /**
     * 绑定适配器数据
     */
    private void bindAdapterData() {
        adapter = new NetBookTableTreeAdapter(mLvNetBookContent, mContext, mArrary,
                1, R.mipmap.ic_spread_gray, R.mipmap.ic_more_go);
        mLvNetBookContent.setAdapter(adapter);
        adapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {

            }
        });
    }

    private void clearData() {
        if (mArrary == null) {
            mArrary = new ArrayList();
        } else {
            mArrary.clear();
        }
    }

    private void clearDataNumber() {
        if (mArrayData == null) {
            mArrayData = new ArrayList();
        } else {
            mArrayData.clear();
        }
    }

    private void addListData(List<Node> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArrary == null) {
            clearData();
        }
        mArrary.addAll(list);
    }

    private void addListDataNumber(List<ChaptersBeanVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mArrayData == null) {
            clearDataNumber();
        }
        mArrayData.addAll(list);
    }

    /**
     * 当前数据有几页
     *
     * @return
     */
    private int getNowPage() {
        if (mArrayData == null || mArrayData.isEmpty())
            return 0;
        if (mArrayData.size() % DataMessageVo.CINT_PANGE_SIZE == 0)
            return mArrayData.size() / DataMessageVo.CINT_PANGE_SIZE;
        else
            return mArrayData.size() / DataMessageVo.CINT_PANGE_SIZE + 1;
    }

    @Override
    public void VideoInfomSuccess(String result) {
        isLoading = false;
        L.d(result);
        Gson gson = new Gson();
        NetBookTableVo tableVo = gson.fromJson(result, NetBookTableVo.class);
        if (tableVo.getStatus().getCode() == 200) {
            NetBookTableVo.DataBean data = tableVo.getData();
            List<ChaptersBeanVo> chapters = data.getChapters();
            clearData();
            clearDataNumber();
            addListDataNumber(chapters);
            bindNodeList();
            bindAdapterData();
        } else {
            T.showToast(mContext, getStrWithId(mContext, R.string.net_error));
            L.e(tableVo.getStatus().getMessage());
        }

    }

    /**
     * 向集合添加数据
     */
    private void bindNodeList() {
        List<Node> objects = new ArrayList<>();
        for (int i = 0; i < mArrayData.size(); i++) {
            ChaptersBeanVo vo = mArrayData.get(i);
            int chapterid = vo.getChapterid();
            objects.add(new Node(chapterid + "", "-1", vo.getChaptername()));
            if (vo.getVideos() != null && !vo.getVideos().isEmpty()) {
                List<VideosBeanVo> videos = vo.getVideos();
                if (videos != null && !videos.isEmpty()) {
                    for (int j = 0; j < videos.size(); j++) {
                        VideosBeanVo beanVo = videos.get(j);
                        objects.add(new Node(beanVo.getVideoid() + "", chapterid + "", beanVo.getVideoname(), beanVo));
                    }
                }
            }
        }
        addListData(objects);

    }


    @Override
    public void VideoInfomError(String msg) {
        L.e(msg);
        T.showToast(mContext,getStrWithId(mContext, R.string.net_error));

    }

    @Override
    public void VideoInfomMoreSuccess(String result) {
        Gson gson = new Gson();
        NetBookTableVo tableVo = gson.fromJson(result, NetBookTableVo.class);
        if (tableVo.getStatus().getCode() == 200) {
            NetBookTableVo.DataBean data = tableVo.getData();
            List<ChaptersBeanVo> chapters = data.getChapters();
            if (chapters == null || chapters.isEmpty()) {
                isLoading = false;
                return;
            } else {
                //判断是否能整除
                if (!mArrayData.isEmpty() && mArrayData.size() % DataMessageVo.CINT_PANGE_SIZE == 0) {
                    isLoading = false;
                } else {
                    isLoading = true;
                }
            }
            addListDataNumber(chapters);
            bindNodeList();
            adapter.notifyDataSetChanged();
        } else {
            L.e(tableVo.getStatus().getMessage());
        }


    }

    @Override
    public void VideoInfomMoreError(String msg) {
        T.showToast(mContext,getStrWithId(mContext, R.string.net_error));

    }
}
