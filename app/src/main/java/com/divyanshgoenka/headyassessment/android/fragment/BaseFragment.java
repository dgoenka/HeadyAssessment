package com.divyanshgoenka.headyassessment.android.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.divyanshgoenka.headyassessment.log.Logger;

/**
 * Created by divyanshgoenka on 25/02/18.
 */

public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("Fragment LifeCycle onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("Fragment LifeCycle onResume");
    }

    @Override
    public void onStop() {
        Logger.d("Fragment LifeCycle onStop");
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("Fragment LifeCycle onSaveInstanceState");

    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("Fragement LifeCycle onPause");

    }
}
