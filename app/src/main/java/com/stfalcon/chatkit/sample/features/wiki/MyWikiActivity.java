package com.stfalcon.chatkit.sample.features.wiki;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.StringRes;

import com.stfalcon.chatkit.sample.R;

import io.reactivex.Observable;
import phos.fri.aiassistant.entity.ApiResponse;
import phos.fri.aiassistant.entity.AssignListData;
import phos.fri.aiassistant.net.ApiService;
import phos.fri.aiassistant.settings.Profile;

public class MyWikiActivity extends BaseWikiActivity {

    public static void open(Context context) {
        context.startActivity(new Intent(context, MyWikiActivity.class));
    }

    @Override
    protected @StringRes int getTitleId() {
        return R.string.my_wiki;
    }

//    @Override
//    protected List<Dialog> getDialogs() {
//        return DialogsFixtures.getDialogs();
//    }

    @Override
    protected Observable<ApiResponse<AssignListData>> getWikiList(ApiService api) {
        return api.getUserAssignmentList(Profile.userId, 1, 20);
    }
}
