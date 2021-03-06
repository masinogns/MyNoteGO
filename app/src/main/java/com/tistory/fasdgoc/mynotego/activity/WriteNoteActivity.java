package com.tistory.fasdgoc.mynotego.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tistory.fasdgoc.mynotego.R;
import com.tistory.fasdgoc.mynotego.domain.Note;
import com.tistory.fasdgoc.mynotego.domain.Orientation;
import com.tistory.fasdgoc.mynotego.domain.Position;
import com.tistory.fasdgoc.mynotego.fragment.WriteNoteFragment;
import com.tistory.fasdgoc.mynotego.helper.DatabaseHelper;

import java.util.Date;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class WriteNoteActivity extends AppCompatActivity implements WriteNoteFragment.OnWriteClickedListener {
    private final static String TAG = "WriteNoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);

        ButterKnife.bind(this);

        setTitle("쪽지 쓰기");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showOnBackDialog();
            return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        showOnBackDialog();
    }

    private void showOnBackDialog() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("나가기")
                .setContentText("정말 취소하시겠습니까?")
                .setConfirmText("예")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                    }
                })
                .showCancelButton(true)
                .setCancelText("아니오")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismissWithAnimation();
                    }
                }).show();
    }

    @Override
    public void OnWriteClicked(String title, String content) {
        String uid = getIntent().getStringExtra("uid");
        Position position = (Position) getIntent().getSerializableExtra("position");
        Orientation orientation = (Orientation) getIntent().getSerializableExtra("orientation");

        DatabaseHelper.addNote(
                new Note(uid, title, content, position, orientation, new Date())
        );
        setResult(RESULT_OK);
        finish();
    }
}
