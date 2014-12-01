package com.example.tomato;

import android.app.Application;
import android.content.res.AssetManager;

import com.example.FreeStuffChecker.R;

import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by mbruncic on 01.12.14..
 */
//TODO dignuti neki servis na koji ce se uploadati crash logovi
@ReportsCrashes(formKey = "", // will not be used
        mailTo = "",
        mode = ReportingInteractionMode.SILENT,
        resToastText = R.string.crash_toast_text)
public class TomatoApplication extends Application{

    @Override
    public void onCreate() {
        loadApplicationProperties();
        super.onCreate();
        ACRAConfiguration config = ACRA.getConfig();
        config.setMailTo(System.getProperty("email"));
        ACRA.init(this);
    }

    private void loadApplicationProperties() {
        AssetManager assets = getResources().getAssets();
        InputStream inputStream;
        try {
            inputStream = assets.open("local/app.properties");
        } catch (IOException e) {
            try {
                inputStream = assets.open("app.properties");
            } catch (IOException e1) {
                throw new RuntimeException("Missing app.properties");
            }
        }
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.setProperties(properties);
    }
}
