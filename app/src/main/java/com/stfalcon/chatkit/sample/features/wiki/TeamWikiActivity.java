package com.stfalcon.chatkit.sample.features.wiki;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.StringRes;

import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.fixtures.DialogsFixtures;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.util.Date;
import java.util.List;

public class TeamWikiActivity extends AbstractWikiActivity
        implements DateFormatter.Formatter {

    public static void open(Context context) {
        context.startActivity(new Intent(context, TeamWikiActivity.class));
    }

    @Override
    protected @StringRes int getTitleId() {
        return R.string.team_wiki;
    }

    @Override
    protected List<Dialog> getDialogs() {
        return DialogsFixtures.getDialogs();
    }

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.date_header_yesterday);
        } else if (DateFormatter.isCurrentYear(date)) {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }
}
