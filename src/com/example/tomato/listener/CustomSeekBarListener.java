package com.example.tomato.listener;

import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by mbruncic on 11.10.2014
 */
public class CustomSeekBarListener implements SeekBar.OnSeekBarChangeListener {
    private Toast toast;

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (toast == null) {
            createToast(seekBar);
        } else {
            refreshToast(seekBar);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        destroyToast();
    }

    private void destroyToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

    private void createToast(SeekBar seekBar) {
        toast = Toast.makeText(seekBar.getContext(), String.valueOf(seekBar.getProgress()), Toast.LENGTH_LONG);
        toast.show();
    }

    private void refreshToast(SeekBar seekBar) {
        toast.setText(String.valueOf(seekBar.getProgress()));
    }
}
