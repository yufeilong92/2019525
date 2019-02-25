package com.xuechuan.xcedu.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.felipecsl.gifimageview.library.GifImageView;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.live.ac.PolyvLivePlayerActivity;
import com.xuechuan.xcedu.mvp.view.TimeShowView;
import com.xuechuan.xcedu.ui.AgreementActivity;
import com.xuechuan.xcedu.ui.InfomDetailActivity;
import com.xuechuan.xcedu.ui.home.AdvisoryListActivity;
import com.xuechuan.xcedu.ui.home.ArticleListActivity;
import com.xuechuan.xcedu.ui.home.AtirlceListActivity;
import com.xuechuan.xcedu.ui.home.BookActivity;
import com.xuechuan.xcedu.ui.home.SpecasListActivity;
import com.xuechuan.xcedu.ui.home.YouZanActivity;
import com.xuechuan.xcedu.ui.net.VideoInfomActivity;
import com.xuechuan.xcedu.utils.DialogUtil;
import com.xuechuan.xcedu.utils.GifDataDownloader;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.MyTimeUitl;
import com.xuechuan.xcedu.utils.PushXmlUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.T;
import com.xuechuan.xcedu.utils.TimeUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.AdvisoryBean;
import com.xuechuan.xcedu.vo.ArticleBean;
import com.xuechuan.xcedu.vo.BannerBean;
import com.xuechuan.xcedu.vo.BookBean;
import com.xuechuan.xcedu.vo.ClassBean;
import com.xuechuan.xcedu.vo.HomePageVo;
import com.xuechuan.xcedu.vo.PolyvliveBean;
import com.xuechuan.xcedu.weight.AddressTextView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.adapter
 * @Description: 首页
 * @author: L-BackPacker
 * @date: 2018/6/2 16:35
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class HomsAdapter extends RecyclerView.Adapter {

    private String code;
    private Context mContext;
    private HomePageVo mData;
    private final LayoutInflater mInflater;
    private BannerViewHolder mBanner;

    public HomsAdapter(Context mContext, HomePageVo mData, String code) {
        this.mContext = mContext;
        this.mData = mData;
        this.code = code;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setData(HomePageVo vo, String codes) {
        mData = vo;
        code = codes;
        notifyDataSetChanged();
    }

    //轮播图
    private int BANNER_VIEW_LAYOUT = 100;
    //教材
    private int MENU_VIEW_LAYOUT = 110;
    //资讯
    private int INFOM_VIEW_LAYOUT = 111;
    //文章
    private int WEN_VIEW_LAYOUT = 112;
    //直播
    private int WEN_VIEW_LIVE = 113;
    //网课精品
    private int WEN_VIEW_NET = 114;
    //精品教辅
    private int TEACHING_ASSISTANTS_VIEW = 115;
    private int MDATA = BANNER_VIEW_LAYOUT;
    // TODO: 2018.10.27 视频直播 true 为隐藏，false 为显示
    private boolean isShowLive = true;
    private int itemNubmer = 4;
    private boolean ploview = false, book = false, net = false;

    /**
     * 设置扫码监听
     */
    private OnScanClickListener onScanClickListener;

    public interface OnScanClickListener {
        public void onClickScan();
    }

    public void setOnScanClickListener(OnScanClickListener onScanClickListener) {
        this.onScanClickListener = onScanClickListener;
    }

    /**
     * 设置地址监听
     */
    private OnAddressClickListener onAddressClickListener;

    public interface OnAddressClickListener {
        public void onClickAddress(String address);
    }

    public void setOnAddressClickListener(OnAddressClickListener onAddressClickListener) {
        this.onAddressClickListener = onAddressClickListener;
    }

    public void doChangerItem(boolean ployview, boolean book, boolean net) {
        this.ploview = ployview;
        this.book = book;
        this.net = net;
        notifyDataSetChanged();
    }

    public void doChangerItem(int nubmer) {
        this.itemNubmer = nubmer;
        notifyDataSetChanged();
    }

    /**
     * 设置搜索监听
     */
    private OnSearchClickListener onSearchClickListener;

    public interface OnSearchClickListener {
        public void onClickSearch();
    }

    public void setOnSearchClickListener(OnSearchClickListener onSearchClickListener) {
        this.onSearchClickListener = onSearchClickListener;
    }

    public void setAddressContent(String address) {
        if (mBanner != null) {
            mBanner.mTvHomeAddressView.setText(address);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER_VIEW_LAYOUT) {
            return new BannerViewHolder(mInflater.inflate(R.layout.home_banner, null));
        } else if (viewType == MENU_VIEW_LAYOUT) {
            return new MenuViewHolder(mInflater.inflate(R.layout.home_meun, null));
        } else if (viewType == WEN_VIEW_LIVE) {
            return new WenViewliveHolder(mInflater.inflate(R.layout.home_live, null));
        } else if (viewType == WEN_VIEW_NET) {
            return new WenViewNetHolder(mInflater.inflate(R.layout.home_net, null));
        } else if (viewType == TEACHING_ASSISTANTS_VIEW) {
            return new TeachingAssistantsViewholder(mInflater.inflate(R.layout.home_teaching, null));
        } else if (viewType == INFOM_VIEW_LAYOUT) {
            return new InfomViewHolder(mInflater.inflate(R.layout.home_infom, null));
        } else if (viewType == WEN_VIEW_LAYOUT) {
            return new WenvIewHolder(mInflater.inflate(R.layout.home_wen, null));
        }


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
            mBanner = (BannerViewHolder) holder;
            setBanner(mBanner);
        } else if (holder instanceof MenuViewHolder) {
            MenuViewHolder meun = (MenuViewHolder) holder;
            setMenu(meun);
        } else if (holder instanceof WenViewliveHolder) {
            WenViewliveHolder live = (WenViewliveHolder) holder;
            setLive(live);
        } else if (holder instanceof WenViewNetHolder) {
            WenViewNetHolder net = (WenViewNetHolder) holder;
            setNet(net);
        } else if (holder instanceof TeachingAssistantsViewholder) {
            TeachingAssistantsViewholder teachingAssistantsViewholder = (TeachingAssistantsViewholder) holder;
            setTeacher(teachingAssistantsViewholder);
        } else if (holder instanceof InfomViewHolder) {
            InfomViewHolder infomViewHolder = (InfomViewHolder) holder;
            setInfom(infomViewHolder);
        } else if (holder instanceof WenvIewHolder) {
            WenvIewHolder wenvIewHolder = (WenvIewHolder) holder;
            setWen(wenvIewHolder);
        }

    }

    /**
     * 精品教辅
     *
     * @param teacher
     */
    private void setTeacher(TeachingAssistantsViewholder teacher) {
        if (mData == null) return;
        setGridlManage(teacher.mRlvHomeTeacherContent);
        HomeTeacherAdapter teacherAdapter = new HomeTeacherAdapter(mContext, mData.getData().getBook());
        teacher.mRlvHomeTeacherContent.setAdapter(teacherAdapter);
        teacherAdapter.setOnItemClickListener(new HomeTeacherAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(BookBean vo) {//网课
                Intent intent = YouZanActivity.newInstance(mContext, vo.getYouzanurl());
                intent.putExtra(YouZanActivity.CSTR_EXTRA_TITLE_STR, mContext.getResources().getString(R.string.home_tacher_book));
                mContext.startActivity(intent);
            }
        });
        teacher.mTvTeacherBookMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//更多
                if (MyAppliction.getInstance().getYouZanSet() != null && MyAppliction.getInstance().getYouZanSet().getHomeurl() != null && !StringUtil.isEmpty(MyAppliction.getInstance().getYouZanSet().getHomeurl())) {
                    Intent intent = YouZanActivity.newInstance(mContext, MyAppliction.getInstance().getYouZanSet().getHomeurl());
                    intent.putExtra(YouZanActivity.CSTR_EXTRA_TITLE_STR, mContext.getResources().getString(R.string.shaop));
                    mContext.startActivity(intent);
                } else {
                    T.showToast(mContext, "商城初始化失败，稍后再试");
                }
            }
        });
    }


    /**
     * 文章
     *
     * @param wenvIewHolder
     */
    private void setWen(WenvIewHolder wenvIewHolder) {
        if (mData == null)
            return;
        List<ArticleBean> article = mData.getData().getArticle();
        HomeAllAdapter allAdapter = new HomeAllAdapter(mContext, article);
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        wenvIewHolder.mRlvRecommendAll.setLayoutManager(manager);
        wenvIewHolder.mRlvRecommendAll.setAdapter(allAdapter);
        allAdapter.setClickListener(new HomeAllAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                ArticleBean vo = (ArticleBean) obj;
                Intent intent = InfomDetailActivity.startInstance(mContext, vo.getGourl(),
                        String.valueOf(vo.getId()), vo.getTitle(), DataMessageVo.USERTYOEARTICLE);
                mContext.startActivity(intent);
            }

        });
        wenvIewHolder.mTvArticleMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instance = AtirlceListActivity.newInstance(mContext, "");
                instance.putExtra(ArticleListActivity.CSTR_EXTRA_TITLE_STR, mContext.getResources().getString(R.string.Allarticle));
                mContext.startActivity(instance);
            }
        });
    }

    /**
     * 资讯
     *
     * @param infomViewHolder
     */
    private void setInfom(InfomViewHolder infomViewHolder) {
        if (mData == null)
            return;
        List<AdvisoryBean> advisory = mData.getData().getAdvisory();
        HomeContentAdapter allAdapter = new HomeContentAdapter(mContext, advisory);
        setGridlManage(infomViewHolder.mRlvRecommendContent);
        infomViewHolder.mRlvRecommendContent.setAdapter(allAdapter);
        allAdapter.setClickListener(new HomeContentAdapter.onItemClickListener() {
            @Override
            public void onClickListener(Object obj, int position) {
                AdvisoryBean vo = (AdvisoryBean) obj;
                Intent intent = AgreementActivity.newInstance(mContext, vo.getGourl(),
                        AgreementActivity.SHAREMARK, vo.getTitle(), vo.getShareurl());
                mContext.startActivity(intent);
            }
        });
        infomViewHolder.mTvInfomMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = PushXmlUtil.getInstance().getLocationProvice(mContext, code);
                Intent intent1 = AdvisoryListActivity.newInstance(mContext, code, str);
                mContext.startActivity(intent1);
            }
        });
    }

    private void setGridlManage(RecyclerView view) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 1);
        manager.setOrientation(GridLayoutManager.VERTICAL);
        view.setLayoutManager(manager);
    }

    /**
     * 菜单
     *
     * @param meun
     */
    private void setMenu(MenuViewHolder meun) {
        meun.mTvHomeBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String string = mContext.getResources().getString(R.string.app_content_heat);
//                String string1 = mContext.getResources().getString(R.string.jiaocai);
//                String concat = string.concat(string1);
           /*     AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_input, null);
                final EditText et = view.findViewById(R.id.et);
                final TextView tv = view.findViewById(R.id.tv);
                Button btn = view.findViewById(R.id.btn_start);
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        tv.setText(et.getText().toString().trim());
                    }
                });
                builder.setView(view);
                builder.setCancelable(true);
                builder.create();
                final AlertDialog show = builder.show();
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show.dismiss();
                        Intent intent3 = AgreementActivity.newInstance(mContext, et.getText().toString().trim(),
                                AgreementActivity.NOSHAREMARK, mContext.getResources().getString(R.string.home_teacherMateri), null);
                        intent3.putExtra(BookActivity.CSTR_EXTRA_TITLE_STR,
                                mContext.getResources().getString(R.string.home_teacherMateri));
                        mContext.startActivity(intent3);
                    }
                });*/
                Intent intent3 = AgreementActivity.newInstance(mContext, DataMessageVo.jiaocao,
                        AgreementActivity.NOSHAREMARK, mContext.getResources().getString(R.string.home_teacherMateri), null);
                intent3.putExtra(BookActivity.CSTR_EXTRA_TITLE_STR,
                        mContext.getResources().getString(R.string.home_teacherMateri));
                mContext.startActivity(intent3);
            }
        });

        meun.mTvHomeStandard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String string = mContext.getResources().getString(R.string.app_content_heat);
//                String string1 = mContext.getResources().getString(R.string.guifan);
//                String concat = string.concat(string1);
                Intent intent2 = AgreementActivity.newInstance(mContext, DataMessageVo.guifan,
                        AgreementActivity.NOSHAREMARK, mContext.getResources().getString(R.string.home_specs), null);
                intent2.putExtra(SpecasListActivity.CSTR_EXTRA_TITLE_STR,
                        mContext.getResources().getString(R.string.home_specs));
                mContext.startActivity(intent2);
            }
        });
        meun.mTvHomeStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNet(mContext)) {
                    T.showToast(mContext, mContext.getResources().getString(R.string.net_error));
                    return;
                }
                if (MyAppliction.getInstance().getYouZanSet() != null && MyAppliction.getInstance().getYouZanSet().getHomeurl() != null && !StringUtil.isEmpty(MyAppliction.getInstance().getYouZanSet().getHomeurl())) {
                    Intent intent = YouZanActivity.newInstance(mContext, MyAppliction.getInstance().getYouZanSet().getHomeurl());
                    intent.putExtra(YouZanActivity.CSTR_EXTRA_TITLE_STR, mContext.getResources().getString(R.string.shaop));
                    mContext.startActivity(intent);
                } else {
                    T.showToast(mContext, "商城初始化失败，稍后再试");
                }
            }
        });
    }

    private void setBanner(final BannerViewHolder banner) {
        if (mData == null)
            return;
        final List<BannerBean> beanList = mData.getData().getBanner();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < beanList.size(); i++) {
            list.add(beanList.get(i).getImageurl());
        }
        banner.mBanHome.setBannerStyle(BannerConfig.NOT_INDICATOR);
        banner.mBanHome.setIndicatorGravity(BannerConfig.CENTER);
        banner.mBanHome.setDelayTime(2000);
        banner.mBanHome.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                MyAppliction.getInstance().displayImages(imageView, (String) path, false);
            }
        });
        banner.mBanHome.setImages(list);
        banner.mBanHome.start();
        banner.mBanHome.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bean;
                if (position <= 0) {
                    bean = beanList.get(position);
                } else
                    bean = beanList.get(position - 1);
                if (!StringUtil.isEmpty(bean.getGourl()))
                    mContext.startActivity(AgreementActivity.newInstance(mContext,
                            bean.getGourl(), AgreementActivity.NOSHAREMARK, "", ""));

            }
        });

        banner.mIvHomeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//扫码
                if (onScanClickListener != null) {
                    onScanClickListener.onClickScan();
                }
            }
        });
        banner.mTvHomeAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//地址
                if (onAddressClickListener != null) {
                    onAddressClickListener.onClickAddress(banner.mTvHomeAddressView.getText().toString().trim());
                }
            }
        });
        banner.mLlHomeSerach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//搜素
                if (onSearchClickListener != null) {
                    onSearchClickListener.onClickSearch();
                }
            }
        });
    }


    /**
     * 直播
     * TODO: 2018.10.15
     *
     * @param live
     */
    private void setLive(final WenViewliveHolder live) {
        if (mData == null) return;
        if (mData.getData().getPolyvlive() == null || mData.getData().getPolyvlive().isEmpty()) {
            return;
        }
        final PolyvliveBean polyvliveBean = mData.getData().getPolyvlive().get(0);
/*        if (!StringUtil.isEmpty(polyvliveBean.getIndexurl())) {
            MyAppliction.getInstance().displayImages(live.mIvHomeLive, polyvliveBean.getIndexurl(), false);
        } else {
            live.mIvHomeLive.setImageResource(R.mipmap.s_n);
        }*/
        MyTimeUitl timeUitl = MyTimeUitl.getInstance(mContext);
        long time = TimeUtil.intervalNow(polyvliveBean.getBegintime());
        final boolean[] go = {true};
        if (time >= 0) {
            go[0] = true;
//            String url="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550287778601&di=4916d2b545d7783c85870bb55831bfc8&imgtype=0&src=http%3A%2F%2Fs9.rr.itc.cn%2Fr%2FwapChange%2F201612_23_9%2Fa15tbh4708957136855.gif";
            showLiveImg(live, true);
            new GifDataDownloader() {
                @Override
                protected void onPostExecute(final byte[] bytes) {
                    live.mGifContentGiftOne.setBytes(bytes);
                    live.mGifContentGiftOne.startAnimation();
                }
            }.execute(polyvliveBean.getGifurl());
        } else {
            go[0] = false;
            showLiveImg(live, false);
            if (!StringUtil.isEmpty(polyvliveBean.getIndexurl())) {
                MyAppliction.getInstance().displayImages(live.mIvHomeLive, polyvliveBean.getIndexurl(), false);
            } else {
                live.mIvHomeLive.setImageResource(R.mipmap.s_n);
            }
            Drawable drawable = mContext.getResources().getDrawable(R.mipmap.c_play_icon_his);
            live.mIcHomeLiveLogon.setImageDrawable(drawable);
            timeUitl.initTime(Math.abs(time), live.mTvHomeLiveTime, new TimeShowView() {
                @Override
                public void TimeFinish() {
                    showLiveImg(live, true);
                    go[0] = true;
                    new GifDataDownloader() {
                        @Override
                        protected void onPostExecute(final byte[] bytes) {
                            live.mGifContentGiftOne.setBytes(bytes);
                            live.mGifContentGiftOne.startAnimation();
                        }
                    }.execute(polyvliveBean.getGifurl());
                }
            });
        }
        live.mRlLayoutRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!go[0]) {
                    Intent intent = AgreementActivity.newInstance(mContext, polyvliveBean.getDescripturl(), AgreementActivity.NOSHAREMARK,
                            "", "");
                    intent.putExtra(AgreementActivity.CSTR_EXTRA_TITLE_STR, polyvliveBean.getName());
                    mContext.startActivity(intent);
                    return;
                }
                DataMessageVo vo = DataMessageVo.get_Instance();
                String nickname = null, headicon = null;
                if (MyAppliction.getInstance().getUserData() != null) {
                    nickname = MyAppliction.getInstance().getUserData().getData().getNickname();
                    if (StringUtil.isEmpty(nickname))
                        nickname = "游客" + vo.getChatUserId();
                    headicon = MyAppliction.getInstance().getUserData().getData().getHeadicon();
                    if (StringUtil.isEmpty(headicon)) {
                        headicon = "null";
                    }
                }
                mContext.startActivity(PolyvLivePlayerActivity.
                        newIntent(mContext, vo.getTESTUSERID(),
                                polyvliveBean.getChannelnum(), vo.getChatUserId(),
                                nickname, headicon, false, false));

            }
        });
    }

    private void showLiveImg(WenViewliveHolder live, boolean show) {
        live.mGifContentGiftOne.setVisibility(show ? View.VISIBLE : View.GONE);
        live.mLlLiveTime.setVisibility(show ? View.GONE : View.VISIBLE);
        live.mIvHomeLive.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * 设置监听
     */
    private OnNetMoreClickListener onNetMoreClickListener;

    public interface OnNetMoreClickListener {
        public void onClickNetMore();
    }

    public void setOnNetMoreClickListener(OnNetMoreClickListener onItemClickListener) {
        this.onNetMoreClickListener = onItemClickListener;
    }

    /**
     * 网课模块
     *
     * @param net
     */
    public void setNet(WenViewNetHolder net) {
        if (mData == null) return;
        List<ClassBean> classX = mData.getData().getClassX();
        net.mTvNetMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNetMoreClickListener != null) {
                    onNetMoreClickListener.onClickNetMore();
                }
            }
        });
        HomeItemNetAdapter adapter = new HomeItemNetAdapter(mContext, classX);
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        net.mRlvRecommendContent.setLayoutManager(layoutManager);
        net.mRlvRecommendContent.setAdapter(adapter);
        adapter.setOnItemClickListener(new HomeItemNetAdapter.OnItemClickListener() {
            @Override
            public void onClickItem(ClassBean vo) {
                Intent intent = VideoInfomActivity.start_Intent(mContext, String.valueOf(vo.getId()));
                intent.putExtra(VideoInfomActivity.CSTR_EXTRA_TITLE_STR_HOME, vo.getName());
                mContext.startActivity(intent);
            }
        });


    }

    /*    @Override
        public int getItemCount() {
            return isShowLive ? 7 : 4;
        }*/
    @Override
    public int getItemCount() {
        return itemNubmer;
    }

    @Override
    public int getItemViewType(int position) {
        if (ploview && book && net) {//1
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = WEN_VIEW_LIVE;
            } else if (position == 3) {
                MDATA = WEN_VIEW_NET;
            } else if (position == 4) {
                MDATA = TEACHING_ASSISTANTS_VIEW;
            } else if (position == 5) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 6) {
                MDATA = WEN_VIEW_LAYOUT;
            }
        } else if (ploview && !net && book) {//2
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = WEN_VIEW_LIVE;
            } else if (position == 3) {
                MDATA = TEACHING_ASSISTANTS_VIEW;
            } else if (position == 4) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 5) {
                MDATA = WEN_VIEW_LAYOUT;
            }
        } else if (ploview && !net && !book) {//3
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = WEN_VIEW_LIVE;
            } else if (position == 3) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 4) {
                MDATA = WEN_VIEW_LAYOUT;
            }
        } else if (ploview && net && !book) {//4
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = WEN_VIEW_LIVE;
            } else if (position == 3) {
                MDATA = WEN_VIEW_NET;
            } else if (position == 4) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 5) {
                MDATA = WEN_VIEW_LAYOUT;
            }
        } else if (!ploview && net && book) {//5
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = WEN_VIEW_NET;
            } else if (position == 3) {
                MDATA = TEACHING_ASSISTANTS_VIEW;
            } else if (position == 4) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 5) {
                MDATA = WEN_VIEW_LAYOUT;
            }
        } else if (!ploview && net && !book) {//6
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = WEN_VIEW_NET;
            } else if (position == 3) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 4) {
                MDATA = WEN_VIEW_LAYOUT;
            }
        } else if (!ploview && !net && book) {//7
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = TEACHING_ASSISTANTS_VIEW;
            } else if (position == 3) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 4) {
                MDATA = WEN_VIEW_LAYOUT;
            }

        } else if (!ploview && !net && !book) {//8
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 3) {
                MDATA = WEN_VIEW_LAYOUT;
            }

        }


      /*  if (isShowLive) {
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = WEN_VIEW_LIVE;
            } else if (position == 3) {
                MDATA = WEN_VIEW_NET;
            } else if (position == 4) {
                MDATA = TEACHING_ASSISTANTS_VIEW;
            } else if (position == 5) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 6) {
                MDATA = WEN_VIEW_LAYOUT;
            }
        } else {
            if (position == 0) {
                MDATA = BANNER_VIEW_LAYOUT;
            } else if (position == 1) {
                MDATA = MENU_VIEW_LAYOUT;
            } else if (position == 2) {
                MDATA = INFOM_VIEW_LAYOUT;
            } else if (position == 3) {
                MDATA = WEN_VIEW_LAYOUT;
            }
        }*/

        return MDATA;

    }

    /**
     * 轮播图
     */
    public class BannerViewHolder extends RecyclerView.ViewHolder {
        public Banner mBanHome;
        public AddressTextView mTvHomeAddressView;
        public LinearLayout mLlHomeSerach;
        public ImageView mIvHomeScan;


        public BannerViewHolder(View itemView) {
            super(itemView);
            this.mBanHome = (Banner) itemView.findViewById(R.id.ban_home);
            this.mTvHomeAddressView = (AddressTextView) itemView.findViewById(R.id.tv_home_address_view);
            this.mLlHomeSerach = (LinearLayout) itemView.findViewById(R.id.ll_home_serach);
            this.mIvHomeScan = (ImageView) itemView.findViewById(R.id.iv_home_scan);
        }
    }

    /**
     * 教材 规范  商城
     */
    public class MenuViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvHomeBook;
        public TextView mTvHomeStandard;
        public TextView mTvHomeStore;

        public MenuViewHolder(View itemView) {
            super(itemView);
            this.mTvHomeBook = (TextView) itemView.findViewById(R.id.tv_home_book);
            this.mTvHomeStandard = (TextView) itemView.findViewById(R.id.tv_home_standard);
            this.mTvHomeStore = (TextView) itemView.findViewById(R.id.tv_home_store);
        }
    }

    /**
     * 资讯模式
     */
    public class InfomViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvInfomMore;
        public RecyclerView mRlvRecommendContent;

        public InfomViewHolder(View itemView) {
            super(itemView);
            this.mTvInfomMore = (TextView) itemView.findViewById(R.id.tv_infom_more);
            this.mRlvRecommendContent = (RecyclerView) itemView.findViewById(R.id.rlv_recommend_content);
        }
    }

    /**
     * 文章模式
     */
    public class WenvIewHolder extends RecyclerView.ViewHolder {
        public TextView mTvArticleMore;
        public RecyclerView mRlvRecommendAll;

        public WenvIewHolder(View itemView) {
            super(itemView);
            this.mTvArticleMore = (TextView) itemView.findViewById(R.id.tv_article_more);
            this.mRlvRecommendAll = (RecyclerView) itemView.findViewById(R.id.rlv_recommend_all);
        }
    }

    /**
     * 直播模式
     */
    public class WenViewliveHolder extends RecyclerView.ViewHolder {
        public ImageView mIvHomeLive;
        public ImageView mIcHomeLiveLogon;
        public TextView mTvHomeLiveCaption;
        public TextView mTvHomeLiveTime;
        public GifImageView mGifContentGiftOne;
        public RelativeLayout mRlLayoutRoot;
        public LinearLayout mLlLiveRoot;
        public LinearLayout mLlLiveTime;

        public WenViewliveHolder(View itemView) {
            super(itemView);
            this.mIvHomeLive = (ImageView) itemView.findViewById(R.id.iv_home_live);
            this.mGifContentGiftOne = (GifImageView) itemView.findViewById(R.id.gifImageView);
            this.mIcHomeLiveLogon = (ImageView) itemView.findViewById(R.id.ic_home_live_logon);
            this.mTvHomeLiveCaption = (TextView) itemView.findViewById(R.id.tv_home_live_caption);
            this.mTvHomeLiveTime = (TextView) itemView.findViewById(R.id.tv_home_live_time);
            this.mRlLayoutRoot = (RelativeLayout) itemView.findViewById(R.id.rl_layout_root);
            this.mLlLiveRoot = (LinearLayout) itemView.findViewById(R.id.ll_live_root);
            this.mLlLiveTime = (LinearLayout) itemView.findViewById(R.id.li_live_time);
        }
    }

    /**
     * 网课模式
     */
    public class WenViewNetHolder extends RecyclerView.ViewHolder {
        public TextView mTvNetMore;
        public RecyclerView mRlvRecommendContent;

        public WenViewNetHolder(View itemView) {
            super(itemView);
            this.mTvNetMore = (TextView) itemView.findViewById(R.id.tv_home_net_more);
            this.mRlvRecommendContent = (RecyclerView) itemView.findViewById(R.id.rlv_home_net);
        }
    }

    public class TeachingAssistantsViewholder extends RecyclerView.ViewHolder {
        public RecyclerView mRlvHomeTeacherContent;
        public TextView mTvTeacherBookMore;

        public TeachingAssistantsViewholder(View itemView) {
            super(itemView);
            this.mRlvHomeTeacherContent = (RecyclerView) itemView.findViewById(R.id.rlv_home_teacher_content);
            this.mTvTeacherBookMore = (TextView) itemView.findViewById(R.id.tv_teacher_book_more);

        }
    }

}
