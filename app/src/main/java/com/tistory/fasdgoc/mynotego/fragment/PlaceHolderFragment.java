package com.tistory.fasdgoc.mynotego.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.event.CameraPermissionGranted;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by fasdg on 2016-10-26.
 */

public class PlaceHolderFragment extends Fragment {
    private final static int CAMERA_PERM = 9000;

    @OnClick(R.id.reqpermission)
    public void onPermButtonClicked() {

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            if(FragmentCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("카메라 권한 요청")
                        .setContentText("증강현실 기능을\n이용하기 위해서\n카메라 권한이\n필요합니다.")
                        .setCustomImage(R.drawable.camera)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                requestPermission();
                            }
                        })
                        .setConfirmText("확인")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelText("취소")
                        .show();
            } else {
                requestPermission();
            }
        } else {
            EventBus.getDefault().post(new CameraPermissionGranted());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    EventBus.getDefault().post(new CameraPermissionGranted());
                } else {
                    if(FragmentCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA) == false) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                                .setTitleText("카메라 권한 요청")
                                .setContentText("증강현실 기능을 이용하기 위해서 앱 설정에서 카메라 권한을 허용해주세요")
                                .setCustomImage(R.drawable.settings)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                })
                                .setConfirmText("확인")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                })
                                .setCancelText("취소")
                                .show();
                    }

                }
                break;

            default:
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.placeholderfragment, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    private void requestPermission() {
        FragmentCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 9000);
    }
}
