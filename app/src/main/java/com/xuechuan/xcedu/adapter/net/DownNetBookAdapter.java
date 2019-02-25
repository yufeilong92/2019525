package com.xuechuan.xcedu.adapter.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easefun.polyvsdk.PolyvDownloadProgressListener;
import com.easefun.polyvsdk.PolyvDownloader;
import com.easefun.polyvsdk.PolyvDownloaderErrorReason;
import com.easefun.polyvsdk.PolyvDownloaderManager;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderProgressListener;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderSpeedListener;
import com.easefun.polyvsdk.download.listener.IPolyvDownloaderStartListener;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.player.util.PolyvErrorMessageUtils;
import com.xuechuan.xcedu.service.UpdataLogSevice;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.Db.DownVideoVo;
import com.xuechuan.xcedu.vo.DownInfomSelectVo;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DownNetBookAdapter extends BaseAdapter {
    private static final String TAG = DownNetBookAdapter.class.getSimpleName();
    private static final String DOWNLOADED = "已下载";
    private static final String DOWNLOADING = "正在下载";
    private static final String PAUSEED = "暂停下载";
    private static final String WAITED = "等待下载";
    private static final String SPEED = "0.00B/S";


    private static Context appContext;
    private final Activity mActivity;
    private DownVideoDb mDownVideo;
    /**
     * 用户选中集合
     */
    private final List<DownInfomSelectVo> mSelectList;
    private Context mContext;
    private ListView lv_download;
    private List<DownVideoVo> mDatalists;
    private LayoutInflater mInflater;
    private final DbHelperDownAssist mDao;

    private onDownClickListener downOverListener;
    private DownVideoDb refreshData;

    public void setRefreshData(DownVideoDb refreshData) {
        if (refreshData != null && refreshData.getDownlist() != null) {
            this.mDatalists = refreshData.getDownlist();
            this.mDownVideo = refreshData;
        }
    }

    public interface onDownClickListener {
        public void onDownClickListener(String videOid, int position);
    }

    public void setDownClickListener(onDownClickListener clickListener) {
        this.downOverListener = clickListener;
    }

    /**
     * @param context
     * @param mDatalists      课目下的数据
     * @param lv_download     显示布局
     * @param mDataSelectList 选中的布局
     */

    public DownNetBookAdapter(Activity activity, Context context, DownVideoDb mDatalists, ListView lv_download, List<DownInfomSelectVo> mDataSelectList) {
        this.mDatalists = mDatalists.getDownlist();
        this.mDownVideo = mDatalists;
        this.mContext = context;
        this.mActivity = activity;
        this.mSelectList = mDataSelectList;
        appContext = context.getApplicationContext();
        this.mInflater = LayoutInflater.from(this.mContext);
        this.lv_download = lv_download;
        mDao = MyAppliction.getInstance().getDownDao();
//        mDao = DbHelperDownAssist.getInstance();
        init();
    }

    /**
     * 初始化下载
     */
    private void init() {
        for (int i = 0; i < mDatalists.size(); i++) {
            DownVideoVo downloadInfo = mDatalists.get(i);
            String vid = downloadInfo.getVid();
            int bitrate = Integer.parseInt(downloadInfo.getBitRate());
            PolyvDownloaderManager.getPolyvDownloader(vid, bitrate);
        }
    }

    private onItemChbClickListener chbClickListener;

    public interface onItemChbClickListener {
        public void onChecaListener(DownVideoVo db, boolean isCheck, int position);
    }

    public void setChbClickListener(onItemChbClickListener clickListener) {
        this.chbClickListener = clickListener;
    }

    /**
     * 暂停下载
     */
    public void pauseAll() {
        PolyvDownloaderManager.stopAll();
        upDataButtonStatus(true);
        for (int i = 0; i < mDatalists.size(); i++) {
            View child = null;
            if ((child = lv_download.getChildAt(i - lv_download.getFirstVisiblePosition())) != null) {
                TextView tv_speed = (TextView) child.findViewById(R.id.tv_item_down_infom_speed);
                showPauseSpeedView(mDatalists.get(i), tv_speed);
            }
        }

    }

    /**
     * 下载全部任务
     */
    public void downloadAll() {
        // 已完成的任务key集合
        List<String> finishKey = new ArrayList<>();
        for (int i = 0; i < mDatalists.size(); i++) {
            DownVideoVo vo = mDatalists.get(i);
            long percent = vo.getPercent();
            long total = vo.getTotal();
            int progress = 0;
            if (total != 0)
                progress = (int) (percent * 100 / total);
            if (progress == 100)
                finishKey.add(PolyvDownloaderManager.getKey(vo.getVid(), Integer.parseInt(vo.getBitRate())));
        }
        upDataButtonStatus(false);
        PolyvDownloaderManager.startUnfinished(finishKey, mContext);
    }

    /**
     * 删除任务
     */
    public void deleteTask(int position) {
        DownVideoVo vo = mDatalists.remove(position);
        //移除任务
        PolyvDownloader downloader = PolyvDownloaderManager.clearPolyvDownload(vo.getVid(), Integer.parseInt(vo.getBitRate()));
        //删除文件
        downloader.deleteVideo();
        //移除数据库的下载信息
        mDao.delectItem(mDownVideo, vo);
        notifyDataSetChanged();
    }

    /**
     * 更新按钮状态
     *
     * @param isPause 之后的状态是否是暂停状态
     */
    private void upDataButtonStatus(boolean isPause) {
        for (int i = 0; i < lv_download.getChildCount(); i++) {
            TextView tv_status = lv_download.getChildAt(i).findViewById(R.id.tv_item_down_status);
            RelativeLayout mRlSpeed = lv_download.getChildAt(i).findViewById(R.id.rl_item_down_speed);
            ProgressBar mprgp = lv_download.getChildAt(i).findViewById(R.id.progress_item_down_infom);
            String str = getStrWithView(tv_status);
            if (!str.equals(DOWNLOADED)) {
                if (isPause) {
                    tv_status.setText(PAUSEED);
                    mRlSpeed.setVisibility(View.GONE);
                    mprgp.setVisibility(View.GONE);
                } else {
                    if (!tv_status.getText().equals(DOWNLOADING)) {
                        tv_status.setText(WAITED);
                        mRlSpeed.setVisibility(View.GONE);
                        mprgp.setVisibility(View.GONE);
                        mRlSpeed.setVisibility(View.VISIBLE);
                        mprgp.setVisibility(View.VISIBLE);
                    }
                }
            }

        }


    }


    @Override
    public int getCount() {
        return mDatalists == null ? 0 : mDatalists.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatalists == null ? 0 : mDatalists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDatalists == null ? 0 : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_net_down_infom, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final DownVideoVo vo = mDatalists.get(position);

        holder.mChbItemDownInfom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!holder.mChbItemDownInfom.isPressed()) return;
                if (chbClickListener != null) {
                    chbClickListener.onChecaListener(vo, isChecked, position);
                }
            }
        });
        if (mSelectList != null && !mSelectList.isEmpty())
            for (DownInfomSelectVo selectVo : mSelectList) {
                if (selectVo.getPid().equals(vo.getPid()) &&
                        selectVo.getZid().equals(vo.getZid())) {
                    if (selectVo.isShowChb()) {//选择按钮
                        holder.mChbItemDownInfom.setVisibility(View.VISIBLE);
                        if (selectVo.isChbSelect()) {//显示选中
                            holder.mChbItemDownInfom.setChecked(true);
                        } else {
                            holder.mChbItemDownInfom.setChecked(false);
                        }
                    } else {
                        holder.mChbItemDownInfom.setVisibility(View.GONE);
                    }
                    if (selectVo.isShowPlay()) {//播放按钮
                        holder.mIvItemDownInfomPlay.setVisibility(View.VISIBLE);
                        if (selectVo.isPlaySelect()) {//显示选中
//                            holder.mChbItemDownInfomPlay.setChecked(true);
                        } else {
//                            holder.mChbItemDownInfomPlay.setChecked(false);
                        }
                    } else {
                        holder.mIvItemDownInfomPlay.setVisibility(View.GONE);
                    }
                }
            }

        String vid = vo.getVid();
        //获取下载清晰度
        int bitRate = Integer.parseInt(vo.getBitRate());
        //是否缓存过
        long percent = vo.getPercent();
        //总大小
        long total = vo.getTotal();
        holder.mTvItemCount.setText(Utils.convertFileSizeB(vo.getFileSize()));
        String title = vo.getTitle();
        holder.mTvItemDownInfomTitle.setText(title);
        //文件大小
        long fileSize = vo.getFileSize();
        //已下载的百分比
        int progerss = 0;
        if (total != 0) {
            progerss = (int) (percent * 100 / total);
        }


        //下载器
        PolyvDownloader downloader = PolyvDownloaderManager.getPolyvDownloader(vid, bitRate);
        holder.mProgressItemDownInfom.setVisibility(View.VISIBLE);
        if (progerss == 100) {//已经下载完成
            holder.mTvItemDownStatus.setText(DOWNLOADED);
            shoSpeed(holder, false);
            if (downOverListener != null) {
                downOverListener.onDownClickListener(vo.getVideoOid(), position);
            }

        } else if (downloader.isDownloading()) {//是否开始下载
            holder.mTvItemDownStatus.setText(DOWNLOADING);
            holder.mTvItemDownInfomSpeed.setText(SPEED);
            shoSpeed(holder, true);
        } else if (PolyvDownloaderManager.isWaitingDownload(vid, bitRate)) {//是否等待下载
            shoSpeed(holder, false);
            holder.mTvItemDownStatus.setText(WAITED);
            holder.mTvItemDownInfomSpeed.setText(Utils.convertFileSizeB(fileSize * progerss / 100));
        } else {//其他的
            holder.mTvItemDownStatus.setText(PAUSEED);
            holder.mTvItemDownInfomSpeed.setText(Utils.convertFileSizeB(fileSize * progerss / 100));
        }
        holder.mProgressItemDownInfom.setProgress(progerss);
        holder.rootView.setOnClickListener(new DownloadOnclickListener(vo, holder,
                holder.mTvItemDownStatus, holder.mTvItemDownInfomSpeed));

        holder.setDownLoadListenter(downloader, vo, position);
        return convertView;
    }

    private void shoSpeed(ViewHolder holder, boolean isShow) {
        holder.mRlItemDownSpeed.setVisibility(isShow ? View.VISIBLE : View.GONE);
        holder.mProgressItemDownInfom.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public class ViewHolder {
        public View rootView;
        public ImageView mIvItemDownInfomPlay;
        public TextView mTvItemDownInfomTitle;
        public TextView mTvItemDownStatus;
        public TextView mTvItemDownInfomSpeed;
        public TextView mTvItemCount;
        public RelativeLayout mRlItemDownSpeed;
        public ProgressBar mProgressItemDownInfom;
        public CheckBox mChbItemDownInfom;

        public void setDownLoadListenter(PolyvDownloader downloader, final DownVideoVo downloadInfo, final int position) {
            downloader.setPolyvDownloadSpeedListener(new MyDownloadSpeedListener(lv_download, this, downloader, position));
            downloader.setPolyvDownloadProressListener(new MyDownloadListener(mContext, lv_download, this, downloadInfo, position));
            downloader.setPolyvDownloadStartListener(new MyDownloaderStartListener(lv_download, this, position));
        }

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.mIvItemDownInfomPlay = (ImageView) rootView.findViewById(R.id.iv_item_down_infom_play);
            this.mTvItemDownInfomTitle = (TextView) rootView.findViewById(R.id.tv_item_down_infom_title);
            this.mTvItemDownStatus = (TextView) rootView.findViewById(R.id.tv_item_down_status);
            this.mTvItemDownInfomSpeed = (TextView) rootView.findViewById(R.id.tv_item_down_infom_speed);
            this.mTvItemCount = (TextView) rootView.findViewById(R.id.tv_item_count);
            this.mRlItemDownSpeed = (RelativeLayout) rootView.findViewById(R.id.rl_item_down_speed);
            this.mProgressItemDownInfom = (ProgressBar) rootView.findViewById(R.id.progress_item_down_infom);
            this.mChbItemDownInfom = (CheckBox) rootView.findViewById(R.id.chb_item_net_down_infom);
        }

        private class MyDownloadSpeedListener implements IPolyvDownloaderSpeedListener {
            private WeakReference<ListView> wr_lv_download;
            private WeakReference<ViewHolder> viewHolder;
            private PolyvDownloader downloader;
            private int position;

            public MyDownloadSpeedListener(ListView lv_download, ViewHolder viewHolder, PolyvDownloader downloader, int position) {
                this.wr_lv_download = new WeakReference<ListView>(lv_download);
                this.viewHolder = new WeakReference<ViewHolder>(viewHolder);
                this.downloader = downloader;
                this.position = position;
            }

            private boolean canUpdateView() {
                ListView lv_download = wr_lv_download.get();
                return lv_download != null && viewHolder.get() != null && lv_download.getChildAt(position - lv_download.getFirstVisiblePosition()) != null;
            }

            @Override
            public void onSpeed(int speed) {
                if (canUpdateView() && downloader.isDownloading()) {
                    viewHolder.get().mTvItemDownInfomSpeed.setText(Utils.convertFileSizeB(speed) + "/s");
                }
            }
        }


        private class MyDownloadListener implements PolyvDownloadProgressListener, IPolyvDownloaderProgressListener {
            private WeakReference<Context> contextWeakReference;
            private WeakReference<ListView> wr_lv_download;
            private WeakReference<ViewHolder> viewHolder;
            private DownVideoVo downloadInfo;
            private int position;
            private long total;

            public MyDownloadListener(Context mContext, ListView lv_download, ViewHolder viewHolder, DownVideoVo downloadInfo, int position) {
                this.contextWeakReference = new WeakReference<>(mContext);
                this.wr_lv_download = new WeakReference<>(lv_download);
                this.viewHolder = new WeakReference<>(viewHolder);
                this.downloadInfo = downloadInfo;
                this.position = position;
            }

            private boolean canUpdateView() {
                ListView lv_download = wr_lv_download.get();
                return lv_download != null && viewHolder.get() != null && lv_download.getChildAt(position - lv_download.getFirstVisiblePosition()) != null;
            }

            @Override
            public void onDownload(long current, long total) {
                this.total = total;
                downloadInfo.setPercent(current);
                downloadInfo.setTotal(total);
                mDao.addUpDataItem(mDownVideo, downloadInfo, current, total);
                if (canUpdateView()) {
                    // 已下载的百分比
                    int progress = (int) (current * 100 / total);
                    viewHolder.get().mProgressItemDownInfom.setProgress(progress);
                }
            }

            @Override
            public void onDownloadSuccess() {
                if (total == 0)
                    total = 1;
                downloadInfo.setPercent(total);
                downloadInfo.setTotal(total);
                mDao.addUpDataItem(mDownVideo, downloadInfo, total, total);
                if (canUpdateView()) {
                    viewHolder.get().mTvItemDownStatus.setText(DOWNLOADED);
                    viewHolder.get().mRlItemDownSpeed.setVisibility(View.GONE);
                    viewHolder.get().mProgressItemDownInfom.setVisibility(View.GONE);
                    if (downOverListener != null) {
                        downOverListener.onDownClickListener(downloadInfo.getVideoOid(), position);
                    }
                    // TODO: 2018/5/20  上传服务器
//                    Toast.makeText(appContext, "第" + (position + 1) + "个任务下载成功", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onDownloadFail(@NonNull PolyvDownloaderErrorReason errorReason) {
                if (canUpdateView()) {
                    viewHolder.get().mTvItemDownStatus.setText(PAUSEED);
                    UpdataLogSevice.startActionFoo(mContext, downloadInfo.getVid(),
                            DataMessageVo.Boli, downloadInfo.getVideoOid(), downloadInfo.getBitRate(),PolyvErrorMessageUtils.getDownloaderErrorMessage(errorReason.getType()),errorReason.getType().getCode());
                    showPauseSpeedView(downloadInfo, viewHolder.get().mTvItemDownInfomSpeed);
                    String message = "第" + (position + 1) + "个任务";
                    message += PolyvErrorMessageUtils.getDownloaderErrorMessage(errorReason.getType());
                    message += "(error code " + errorReason.getType().getCode() + ")";
//                Toast.makeText(appContext, message, Toast.LENGTH_LONG).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(contextWeakReference.get());
                    builder.setTitle("错误");
                    builder.setMessage(message);
                    if (mActivity != null && !mActivity.isFinishing())
                        builder.show();

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (mActivity != null && !mActivity.isFinishing())
                                dialog.dismiss();
                        }
                    });

                }
            }
        }

        private class MyDownloaderStartListener implements IPolyvDownloaderStartListener {
            private WeakReference<ListView> wr_lv_download;
            private WeakReference<ViewHolder> viewHolder;
            private int position;

            public MyDownloaderStartListener(ListView lv_download, ViewHolder viewHolder, int position) {
                this.wr_lv_download = new WeakReference<ListView>(lv_download);
                this.viewHolder = new WeakReference<ViewHolder>(viewHolder);
                this.position = position;
            }

            private boolean canUpdateView() {
                ListView lv_download = wr_lv_download.get();
                return lv_download != null && viewHolder.get() != null && lv_download.getChildAt(position - lv_download.getFirstVisiblePosition()) != null;
            }

            @Override
            public void onStart() {
                if (canUpdateView()) {
                    viewHolder.get().mTvItemDownStatus.setText(DOWNLOADING);
                    viewHolder.get().mRlItemDownSpeed.setVisibility(View.VISIBLE);
                    viewHolder.get().mProgressItemDownInfom.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * 整个item点击监听
     */
    private class DownloadOnclickListener implements View.OnClickListener {

        private final ViewHolder holder;
        private DownVideoVo vo;
        private TextView tv_status;
        private TextView tv_speed;

        public DownloadOnclickListener(DownVideoVo vo, ViewHolder holder, TextView tv_status, TextView tv_speed) {
            this.vo = vo;
            this.holder = holder;
            this.tv_status = tv_status;
            this.tv_speed = tv_speed;
        }

        @Override
        public void onClick(View v) {
            String vid = vo.getVid();
            int bitRate = Integer.parseInt(vo.getBitRate());
            PolyvDownloader downloader = PolyvDownloaderManager.getPolyvDownloader(vid, bitRate);
            String status = getStrWithView(tv_status);
            if (status.equals(DOWNLOADED)) {//已下载跳转播放界面
//                Intent intent = NetBookPlayActivity.newIntent(mContext, NetBookPlayActivity.PlayMode.portrait, vid, bitRate, true, true);
                // 在线视频和下载的视频播放的时候只显示播放器窗口，用该参数来控制
//                mContext.startActivity(intent);
            } else if (status.equals(DOWNLOADING) || status.equals(WAITED)) {//正在和等待下载状态
                tv_status.setText(PAUSEED);
                downloader.stop();
                shoSpeed(holder, false);
                showPauseSpeedView(vo, tv_speed);
            } else {
                tv_status.setText(DOWNLOADING);
                shoSpeed(holder, true);
                downloader.start(mContext);
                if (!downloader.isDownloading() && PolyvDownloaderManager.isWaitingDownload(vid, bitRate)) {
                    tv_status.setText(WAITED);
                    shoSpeed(holder, false);
                }
            }

        }
    }

    /***
     * 显示速度界面
     * @param vo
     * @param tv_speed
     */
    private void showPauseSpeedView(DownVideoVo vo, TextView tv_speed) {
        long percent = vo.getPercent();
        long total = vo.getTotal();
        int progress = 0;
        if (total != 0) {
            progress = (int) (percent * 100 / total);
        }
        long downloaded = vo.getFileSize() * progress / 100;
        tv_speed.setText(Utils.convertFileSizeB(downloaded));
    }

    private String getStrWithView(TextView tv) {
        return tv.getText().toString().trim();
    }

}
