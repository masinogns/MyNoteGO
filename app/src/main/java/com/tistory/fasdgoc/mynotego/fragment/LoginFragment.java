package com.tistory.fasdgoc.mynotego.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.event.SignDialogClose;
import com.tistory.fasdgoc.mynotego.event.SignInJoin;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import dmax.dialog.SpotsDialog;

/**
 * Created by fasdg on 2016-10-24.
 */

public class LoginFragment extends Fragment {
    private OnSignInTryListener activity;
    private AlertDialog signingDialog;

    private boolean signInJoin = false;

    public interface OnSignInTryListener {
        public void onSignInTry(View v);
    }

    @Optional
    @OnClick({R.id.google_sign_in})
    public void onSignClick(View v) {
        EventBus.getDefault().post(new SignDialogClose(true));

        activity.onSignInTry(v);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            activity = (OnSignInTryListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity Must implement OnSignInTryListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_page, container, false);

        ButterKnife.bind(this, root);

        return root;
    }

    @Subscribe
    public void onSigned(SignDialogClose e) {
        if(e.showing) {
            signingDialog = new SpotsDialog(getActivity());
            signingDialog.setCancelable(false);
            signingDialog.show();

        } else {
            if(signingDialog == null)
                return;
            signingDialog.dismiss();
            signingDialog = null;
        }
    }

    @Subscribe
    public void isSignin(SignInJoin signInJoin) {
        this.signInJoin = signInJoin.status;
        if(signInJoin.status == false) {
            Toast.makeText(getActivity(), "잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show();
            signingDialog.dismiss();
            signingDialog = null;
        }
    }
}
