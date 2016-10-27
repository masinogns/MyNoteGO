package com.tistory.fasdgoc.mynotego.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.tistory.fasdgoc.mynotego.R;

import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * Created by fasdg on 2016-10-27.
 */

public class WriteNoteFragment extends Fragment {
    private static final String TAG = "WriteNoteFragment";

    private OnWriteClickedListener context;

    public interface OnWriteClickedListener {
        void OnWriteClicked();
    }

    @OnLongClick(R.id.title)
    public boolean onTitleLongClicked(View v) {
        ((EditText)v).selectAll();
        return false;
    }
    @OnLongClick(R.id.content)
    public boolean onContentLongClicked(View v) {
        ((EditText)v).selectAll();
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.writenotefragment, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.context = (OnWriteClickedListener) context;
        } catch (ClassCastException e) {
            Log.d(TAG, "onActivityCreated - Activity is not implement OnWriteClickedListener");
            throw new ClassCastException("Activity is not implement OnWriteClickedListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.write_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.write) {
            context.OnWriteClicked();
            return true;
        }

        return false;
    }
}
