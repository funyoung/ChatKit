package com.stfalcon.chatkit.sample.features.wiki;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.StringRes;

import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.fixtures.DialogsFixtures;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;

import java.util.List;

public class MyWikiActivity extends AbstractWikiActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, MyWikiActivity.class));
    }

    @Override
    protected @StringRes int getTitleId() {
        return R.string.my_wiki;
    }

    @Override
    protected List<Dialog> getDialogs() {
        return DialogsFixtures.getDialogs();
    }
}
