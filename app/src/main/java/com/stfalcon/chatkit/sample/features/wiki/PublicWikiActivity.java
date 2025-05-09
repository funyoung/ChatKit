package com.stfalcon.chatkit.sample.features.wiki;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.StringRes;

import com.stfalcon.chatkit.sample.R;

public class PublicWikiActivity extends BaseWikiActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, PublicWikiActivity.class));
    }

    @Override
    protected @StringRes int getTitleId() {
        return R.string.public_wiki;
    }

//    @Override
//    protected List<Dialog> getDialogs() {
//        return DialogsFixtures.getDialogs();
//    }

    @Override
    protected String getUserId() {
        return null;
    }
}
