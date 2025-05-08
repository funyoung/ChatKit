package com.stfalcon.chatkit.sample.features.wiki;

import android.os.Bundle;

import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.fixtures.DialogsFixtures;
import com.stfalcon.chatkit.sample.common.data.model.Dialog;
import com.stfalcon.chatkit.sample.features.chat.ChatMessagesActivity;
import com.stfalcon.chatkit.sample.features.demo.DemoDialogsActivity;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWikiActivity extends DemoDialogsActivity {
    private DialogsList dialogsList;

    protected abstract List<Dialog> getDialogs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_dialogs);

        dialogsList = findViewById(R.id.dialogsList);
        initAdapter();
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        ChatMessagesActivity.open(this);
    }

    private void initAdapter() {
        super.dialogsAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog, super.imageLoader);
        super.dialogsAdapter.setItems(getDialogs());

        super.dialogsAdapter.setOnDialogClickListener(this);
        super.dialogsAdapter.setOnDialogLongClickListener(this);

        dialogsList.setAdapter(super.dialogsAdapter);
    }
}
