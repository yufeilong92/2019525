package com.xuechuan.xcedu;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xuechuan.xcedu.XceuAppliciton.MyAppliction;
import com.xuechuan.xcedu.base.BaseActivity;
import com.xuechuan.xcedu.base.DataMessageVo;
import com.xuechuan.xcedu.db.DbHelp.DatabaseContext;
import com.xuechuan.xcedu.db.DbHelp.DbHelperAssist;
import com.xuechuan.xcedu.db.DbHelp.DbHelperDownAssist;
import com.xuechuan.xcedu.db.DownVideoDb;
import com.xuechuan.xcedu.db.UserInfomDb;
import com.xuechuan.xcedu.jg.RegisterTag;
import com.xuechuan.xcedu.mvp.model.RefreshTokenModelImpl;
import com.xuechuan.xcedu.mvp.presenter.RefreshTokenPresenter;
import com.xuechuan.xcedu.mvp.view.RefreshTokenView;
import com.xuechuan.xcedu.sqlitedb.UserInfomDbHelp;
import com.xuechuan.xcedu.sqlitedb.UserInfomOpenHelp;
import com.xuechuan.xcedu.ui.LoginActivity;
import com.xuechuan.xcedu.utils.CopyUitl;
import com.xuechuan.xcedu.utils.FileUtil;
import com.xuechuan.xcedu.utils.L;
import com.xuechuan.xcedu.utils.SaveIsDoneUtil;
import com.xuechuan.xcedu.utils.SaveUUidUtil;
import com.xuechuan.xcedu.utils.StringUtil;
import com.xuechuan.xcedu.utils.Utils;
import com.xuechuan.xcedu.vo.DownVideoDbVo;
import com.xuechuan.xcedu.vo.EncrypBaseVo;
import com.xuechuan.xcedu.vo.SqliteVo.UserInfomSqliteVo;
import com.xuechuan.xcedu.vo.TokenVo;
import com.xuechuan.xcedu.vo.UserBean;
import com.xuechuan.xcedu.vo.UserInfomVo;
import com.xuechuan.xcedu.vo.YouzanuserBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: PiloActivity
 * @Package com.xuechuan.xcedu
 * @Description: 引导页
 * @author: L-BackPacker
 * @date: 2018/5/14 8:59
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018/5/14
 */

public class PiloActivity extends BaseActivity implements RefreshTokenView, EasyPermissions.PermissionCallbacks {

    private Context mContext;
    private ImageView mIvPilo;
    private TextView mTvPilo;

/*    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilo);
        initView();
    }*/

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pilo);

        initView();
//        startActivity(new Intent(PiloActivity.this, MainActivity.class));
        requesPermission();
    }

    private void requesPermission() {
        if (EasyPermissions.hasPermissions(this, DataMessageVo.Persmission)) {
            initData();
        } else {
            PermissionRequest build = new PermissionRequest.Builder(PiloActivity.this, 0, DataMessageVo.Persmission)
                    .setRationale("请允许使用该app申请的权限，否则，该APP无法正常使用")
                    .setNegativeButtonText(R.string.cancel)
                    .setPositiveButtonText(R.string.allow)
                    .build();
            EasyPermissions.requestPermissions(build);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initData() {
        doDbAddM();
        onreateMySqliteDb();
//        DBHelper.initDb(MyAppliction.getInstance());
        String newType = Utils.getNewType(mContext);
        UserInfomDbHelp dbHelp = UserInfomDbHelp.get_Instance(mContext);
        UserInfomSqliteVo infomVo = dbHelp.findUserInfomVo();
        if (StringUtil.isEmpty(newType)) {
     /*       String userId = SaveUUidUtil.getInstance().getUserId();
            UserInfomDb dbInfom = DbHelperAssist.getInstance(mContext).queryWithuuId(userId);
            if (dbInfom == null || dbInfom.getToken() == null || dbInfom.getToken().equals("")) {
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivity(intent1);
                finish();
                return;
            }*/
            //判断用户是否存在
            if (infomVo==null||StringUtil.isEmpty(infomVo.getUserinfome())) {
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivity(intent1);
                finish();
                return;
            }
            UserInfomVo vo = new UserInfomVo();
            UserInfomVo.DataBean bean = new UserInfomVo.DataBean();
            UserBean userBean = new UserBean();
            userBean.setToken(infomVo.getToken());
            userBean.setId(infomVo.getSaffid());
            bean.setUser(userBean);
            vo.setData(bean);
            MyAppliction.getInstance().setUserInfom(vo);
            HomeActivity.newInstance(mContext, HomeActivity.LOGIN_HOME);
            return;
        }
      /*  String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb userInfomDb = DbHelperAssist.getInstance(mContext).queryWithuuId(userId);
        if (userInfomDb != null && userInfomDb.getVo() != null) {
            MyAppliction.getInstance().setUserInfom(userInfomDb.getVo());
            RefreshTokenPresenter presenter = new RefreshTokenPresenter(new RefreshTokenModelImpl(), this);
            presenter.refreshToken(mContext, userInfomDb.getToken());
        } else {
            String id = SaveIsDoneUtil.getInstance().getUserId();
            if (!StringUtil.isEmpty(id)) {
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivity(intent1);
                finish();
            } else {
                Intent intent1 = new Intent(mContext, GuideActivity.class);
                startActivity(intent1);
                finish();
            }
        }*/
        if (infomVo != null && infomVo.getUserData() != null) {//刷新token
            UserInfomVo userInfomVo = new UserInfomVo();
            userInfomVo.setData(infomVo.getUserData());
            MyAppliction.getInstance().setUserInfom(userInfomVo);
            RefreshTokenPresenter presenter = new RefreshTokenPresenter(new RefreshTokenModelImpl(), this);
            presenter.refreshToken(mContext, infomVo.getToken());
        } else {
            String id = SaveIsDoneUtil.getInstance().getUserId();
            if (!StringUtil.isEmpty(id)) {
                Intent intent1 = new Intent(mContext, LoginActivity.class);
                startActivity(intent1);
                finish();
            } else {
                Intent intent1 = new Intent(mContext, GuideActivity.class);
                startActivity(intent1);
                finish();
            }
        }

    }

    /**
     * 拷贝数据库
     */
    private void onreateMySqliteDb() {
        String userId = SaveUUidUtil.getInstance().getUserId();
        UserInfomDb dbInfom = DbHelperAssist.getInstance(mContext).queryWithuuId(userId);
        if (dbInfom == null) {
            return;
        }
        UserInfomSqliteVo userInfomSqliteVo = CopyUitl.get_Instance().setCopyUserInfom(dbInfom);
        UserInfomDbHelp dbHelp = UserInfomDbHelp.get_Instance(mContext);
        UserInfomSqliteVo vo = dbHelp.findUserInfomVo();
        if (vo == null) {
            dbHelp.addUserInfom(userInfomSqliteVo);
            return;
        }
        if (vo.getCopy() == 1) {//是需要否拷贝数据
            return;
        }

    }

    @Override
    public void TokenSuccess(String con) {
        L.d(con);
        Gson gson = new Gson();
        TokenVo tokenVo = gson.fromJson(con, TokenVo.class);
        if (tokenVo.getStatus().getCode() == 200) {
            int statusX = tokenVo.getData().getStatusX();
            TokenVo.DataBean data = tokenVo.getData();
            switch (statusX) {
                case -1:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case -2:
                    SaveUUidUtil.getInstance().delectUUid();
                    Intent intent1 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case 1:
                    updataToken(data);
                    break;
                default:
            }
        } else {
            SaveUUidUtil.getInstance().delectUUid();
            Intent intent1 = new Intent(mContext, LoginActivity.class);
            startActivity(intent1);
            finish();
            L.e(tokenVo.getStatus().getMessage());
        }
    }

    @Override
    public void TokenError(String con) {
        String newType = Utils.getNewType(mContext);
        if (StringUtil.isEmpty(newType)) {
            HomeActivity.newInstance(mContext, HomeActivity.LOGIN_HOME);
            finish();
            return;
        }
        SaveUUidUtil.getInstance().delectUUid();
        Intent intent1 = new Intent(mContext, LoginActivity.class);
        startActivity(intent1);
        finish();
    }

    // 拷贝用户下载数据
    private void doDbAddM() {
        EncrypBaseVo tag = FileUtil.getEncrypEncryTag();
        CopyUitl copyUitl = CopyUitl.get_Instance();
        if (tag == null) {
            DbHelperDownAssist db = DbHelperDownAssist.getInstance(mContext);
            List<DownVideoDb> videoDbs = db.queryUserDownInfom();
            ArrayList<DownVideoDbVo> copyVoLists = new ArrayList<>();
            if (videoDbs != null && !videoDbs.isEmpty()) {
                for (int i = 0; i < videoDbs.size(); i++) {
                    DownVideoDb vo = videoDbs.get(i);
                    DownVideoDbVo dbVo = new DownVideoDbVo();
                    copyUitl.copy(vo, dbVo);
                    copyVoLists.add(dbVo);
                }
            }
            EncrypBaseVo vo = new EncrypBaseVo();
            vo.setDelete("no");
            vo.setIsdo("no");
            vo.setDownVideoDbs(copyVoLists);
            FileUtil.saveEncrypContent(vo);
        } else {
            if (tag.getDelete().equals("delete") && tag.getIsdo().equals("do")) {
                tag.setDownVideoDbs(null);
            } else {
                tag.setDelete("delete");
                tag.setIsdo("do");
                DbHelperDownAssist db = DbHelperDownAssist.getInstance(mContext);
                ArrayList<DownVideoDbVo> dbVos = tag.getDownVideoDbs();
                if (dbVos != null && !dbVos.isEmpty()) {
                    for (int i = 0; i < dbVos.size(); i++) {
                        DownVideoDbVo vo = dbVos.get(i);
                        DownVideoDb dbvo = new DownVideoDb();
                        copyUitl.copy(vo, dbvo);
                        db.addDownItem(dbvo);
                    }
                }
                tag.setDownVideoDbs(null);
            }
            FileUtil.saveEncrypContent(tag);
        }


    }

    private void updataToken(TokenVo.DataBean data) {
        TokenVo.DataBean.TokenBean token = data.getToken();
        YouzanuserBean dataYouZanUser = data.getYouZanUser();
        YouzanuserBean youZanUser = new YouzanuserBean();
        if (dataYouZanUser != null) {
            youZanUser.setAccess_token(dataYouZanUser.getAccess_token());
            youZanUser.setCookie_key(dataYouZanUser.getCookie_key());
            youZanUser.setCookie_value(dataYouZanUser.getCookie_value());
        }

        UserInfomVo userInfom = new UserInfomVo();

        UserBean bean = new UserBean();
        bean.setId(token.getStaffid());
        bean.setToken(token.getSigntoken());
        bean.setTokenexpire(token.getExpiretime());

        UserInfomVo.DataBean dataBean = new UserInfomVo.DataBean();
        dataBean.setUser(bean);
        dataBean.setYouzanuser(youZanUser);


        userInfom.setData(dataBean);
        MyAppliction.getInstance().setUserInfom(userInfom);
        UserInfomDbHelp.get_Instance(mContext).UpDataUserToken(dataBean, token.getSigntoken());
//        DbHelperAssist.getInstance(mContext).saveUserInfom(userInfom);
        HomeActivity.newInstance(mContext, HomeActivity.LOGIN_HOME);
        //注册激光
        RegisterTag tag = RegisterTag.getInstance(getApplicationContext());
        tag.registJG();
        tag.setTagAndAlias(String.valueOf(data.getToken().getStaffid()));
        finish();
    }

    private void initView() {
        mContext = this;
        // TODO: 2018/5/30 激光
//        JPushInterface.stopPush(getApplicationContext());
        mIvPilo = (ImageView) findViewById(R.id.iv_pilo);
        mTvPilo = (TextView) findViewById(R.id.tv_pilo);
        mTvPilo.setText("版本：" + Utils.getVersionName(mContext));
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {//某些权限已被授予
        List<String> mDATA = Arrays.asList(DataMessageVo.Persmission);
        if (perms.contains(mDATA)) {
            initData();
        } else {
            requesPermission();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {//某些权限已被拒绝
        if (requestCode == 0) {
            List<String> mDATA = Arrays.asList(DataMessageVo.Persmission);
            if (perms.size() != 0) {
                StringBuilder builder = new StringBuilder();
//                if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                for (String perm : perms) {
                    if (perm.equals(DataMessageVo.Persmission[0])) {
                        builder.append("写内存卡、");
                    }
                    if (perm.equals(DataMessageVo.Persmission[1])) {
                        builder.append("读取内存卡、");
                    }
                    if (perm.equals(DataMessageVo.Persmission[2])) {
                        builder.append("定位、");
                    }
                    if (perm.equals(DataMessageVo.Persmission[3])) {
                        builder.append("相机、");
                    }
                    if (perm.equals(DataMessageVo.Persmission[4])) {
                        builder.append("设置");
                    }

                }
                new AppSettingsDialog.Builder(this)
                        .setPositiveButton(R.string.allow)
                        .setTitle("权限申请")
                        .setNegativeButton(R.string.cancel)
                        .setRationale("请允许使用该app申请" + builder.toString() + "等权限,\n否则，该APP无法正常使用\n")
                        .build()
                        .show();
//                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(mContext, DataMessageVo.Persmission)) {
                initData();
            } else {
                requesPermission();
            }
        }
    }

    public void showPermission(String persion, String cons) {
        PermissionRequest build = new PermissionRequest.Builder(PiloActivity.this,
                0, persion)
                .setRationale("请允许使用该app申请的" + cons + "+权限，否则，该APP无法正常使用")
                .setNegativeButtonText(R.string.cancel)
                .setPositiveButtonText(R.string.allow)
                .build();
        EasyPermissions.requestPermissions(build);
    }

}
