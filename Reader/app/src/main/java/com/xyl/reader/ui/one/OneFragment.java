package com.xyl.reader.ui.one;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xyl.reader.R;

/**
 * User: ShaudXiao
 * Date: 2017-01-12
 * Time: 15:31
 * Company: zx
 * Description:
 * FIXME
 */


public class OneFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one_layout, container, false);
    }
}
