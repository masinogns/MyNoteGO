package com.tistory.fasdgoc.mynotego.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fastaccess.permission.base.PermissionFragmentHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.helper.ViewModeHelper;
import com.tistory.fasdgoc.mynotego.util.ModeFragmentProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by fasdg on 2016-10-23.
 */

public class ViewModeFragment extends Fragment implements OnPermissionCallback {
    private ModeFragmentProvider mProvider;

    private FragmentManager fragmentManager;
    private Fragment fragment;

    private PermissionFragmentHelper helper;
    private int current_mode;

    @BindView(R.id.mode_button)
    ImageButton mModeButton;

    @OnClick(R.id.mode_button)
    public void changeMode() {

        if (current_mode == ViewModeHelper.CAMERA) {

            current_mode = ViewModeHelper.MAP;
            fragment = mProvider.provideFragment(ViewModeHelper.MAP);
            fragmentManager.beginTransaction()
                    .replace(R.id.mode, fragment)
                    .commit();
            mModeButton.setImageResource(R.drawable.camera);
        } else {

            if(PermissionFragmentHelper.isPermissionDeclined(this, Manifest.permission.CAMERA)) {
                helper.request(Manifest.permission.CAMERA);
            } else {
                current_mode = ViewModeHelper.CAMERA;
                fragment = mProvider.provideFragment(ViewModeHelper.CAMERA);
                fragmentManager.beginTransaction()
                        .replace(R.id.mode, fragment)
                        .commitAllowingStateLoss();
                mModeButton.setImageResource(R.drawable.map);
            }
        }

        YoYo.with(Techniques.RubberBand)
                .duration(500)
                .playOn(mModeButton);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.viewmodefragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();

        current_mode = ViewModeHelper.MAP;
        fragment = mProvider.provideFragment(ViewModeHelper.MAP);
        fragmentManager.beginTransaction()
                .add(R.id.mode, fragment)
                .commit();
        mModeButton.setImageResource(R.drawable.camera);
    }

    private void initialize() {
        this.fragmentManager = getFragmentManager();

        mProvider = new ModeFragmentProvider(getActivity());

        helper = PermissionFragmentHelper.getInstance(this);
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {
        changeMode();
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {
        current_mode = ViewModeHelper.CAMERA;
        fragment = mProvider.provideFragment(ViewModeHelper.CAMERA);
        fragmentManager.beginTransaction()
                .replace(R.id.mode, fragment)
                .commit();
        mModeButton.setImageResource(R.drawable.map);
    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("카메라 권한 요청")
                .setContentText("증강현실 기능을\n이용하기 위해서\n카메라 권한이\n필요합니다.")
                .setCustomImage(R.drawable.camera)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        helper.requestAfterExplanation(Manifest.permission.CAMERA);
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

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {
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

    @Override
    public void onNoPermissionNeeded() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        helper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
