package com.xuechuan.xcedu.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.xuechuan.xcedu.PiloActivity;
import com.xuechuan.xcedu.R;
import com.xuechuan.xcedu.base.DataMessageVo;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.utils
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/5/24 8:34
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class EasyPermissionsUtils {
    private Activity mContext;
    private static EasyPermissionsUtils service;

    public EasyPermissionsUtils(Activity mContext) {
        this.mContext = mContext;
    }

    public static EasyPermissionsUtils getInstance(Activity context) {
        if (service == null) {
            service = new EasyPermissionsUtils(context);
        }
        return service;
    }

    public boolean havsCamePermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean havsCALL_PHONEPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.CALL_PHONE)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean havsACCESS_COARSE_LOCATIONPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean havsACCESS_FINE_LOCATIONPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean havsAREAD_EXTERNAL_STORAGEPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean havsWRITE_EXTERNAL_STORAGEPermission() {
        if (EasyPermissions.hasPermissions(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return true;
        } else {
            return false;
        }
    }

    public void showPermission(String persion, String cons) {

        PermissionRequest build = new PermissionRequest.Builder(mContext,
                0, persion)
                .setRationale("请允许使用该app申请的" + cons + "+权限，否则，该APP无法正常使用")
                .setNegativeButtonText(R.string.cancel)
                .setPositiveButtonText(R.string.allow)
                .build();
        EasyPermissions.requestPermissions(build);
    }

    public AppSettingsDialog showPersimDimis(List<String> con) {
        AppSettingsDialog build = null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < con.size(); i++) {
            builder.append(con.get(i).toString().trim() + "\n");
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(mContext, con)) {
            AppSettingsDialog.Builder builder1 = new AppSettingsDialog.Builder(mContext);
            builder1
                    .setPositiveButton(R.string.allow)
                    .setTitle("权限申请")
                    .setNegativeButton(R.string.cancel)
                    .setRationale("请允许使用该app申请的权限，否则，该APP无法正常使用\n" + builder.toString())
                    .build();
            build = builder1.build();

        }
        return build;
    }

    public void showDailog(Activity cla, String per) {
        PermissionRequest build = new PermissionRequest.Builder(cla, 0, per)
                .setRationale("请允许使用该app申请的权限，否则，该APP无法正常使用")
                .setNegativeButtonText(R.string.cancel)
                .setPositiveButtonText(R.string.allow)
                .build();
        EasyPermissions.requestPermissions(build);
    }
}
