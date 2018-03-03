package com.example.bsproperty.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.bsproperty.R;
import com.example.bsproperty.utils.LQRPhotoSelectUtils;

import java.io.File;
import java.text.SimpleDateFormat;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by yezi on 2018/1/27.
 */

public class Fragment03 extends BaseFragment {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    private ImageView iv_img;

    @Override
    protected void loadData() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(getActivity(), new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                Log.e("hdd", outputFile + "");
                Log.e("hdd1", outputUri + "");
                // 4、当拍照或从图库选取图片成功后回调  
//                mTvPath.setText(outputFile.getAbsolutePath());
//                mTvUri.setText(outputUri.toString());
//                Glide.with(MainActivity.this).load(outputUri).into(mIvPic);
            }
        }, false);//true裁剪，false不裁剪  


        iv_img = (ImageView) mRootView.findViewById(R.id.iv_img);
        mRootView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(new String[]{
                        "拍照选择", "本地相册选择", "取消"
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // 3、调用拍照方法
//                                PermissionGen.with(getActivity())
//                                        .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
//                                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
//                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                                Manifest.permission.CAMERA
//                                        ).request();
                                mLqrPhotoSelectUtils.takePhoto();
                                break;
                            case 1:
                                // 3、调用从图库选取图片方法  
                                PermissionGen.needPermission(getActivity(),
                                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                );
                                break;
                            case 2:
                                break;
                        }
                    }
                }).show();
            }
        });
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
//    }

    public void showDialog() {
        //创建对话框创建器  
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //设置对话框显示小图标  
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题  
        builder.setTitle("权限申请");
        //设置正文  
        builder.setMessage("在设置-应用-权限 中开启相机、存储权限，才能正常使用拍照或图片选择功能");

        //添加确定按钮点击事件  
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件  

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限  
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件  
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象  
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框  
    }

    @Override
    public int getRootViewId() {
        return R.layout.fragment_03;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联  
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }
}
