package com.stfalcon.chatkit.sample.features.wiki;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.fixtures.MessagesFixtures;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.intent.Schema;
import com.stfalcon.chatkit.sample.features.demo.DemoMessagesActivity;
import com.stfalcon.chatkit.sample.features.demo.custom.media.holders.IncomingVoiceMessageViewHolder;
import com.stfalcon.chatkit.sample.features.demo.custom.media.holders.OutcomingVoiceMessageViewHolder;

public class ChatMessagesActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        MessageHolders.ContentChecker<Message>,
        DialogInterface.OnClickListener {
    private static final String EXTRA_MESSAGE = "phos.fri.aiassistant.MESSAGE";
    private static void openWithMessage(Context context, String message) {
        Intent intent = new Intent(context, ChatMessagesActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.startActivity(intent);
    }

    private static final byte CONTENT_TYPE_VOICE = 1;


    public static void open(Context context) {
        context.startActivity(new Intent(context, ChatMessagesActivity.class));
    }
    public static void openOcr(Context context) {
        openWithMessage(context, Schema.TOOL_OCR);
    }
    public static void openTts(Context context) {
        openWithMessage(context, Schema.TOOL_TTS);
    }

    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_media_messages);

        this.messagesList = findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = findViewById(R.id.input);
        input.setInputListener(this);
        input.setAttachmentsListener(this);

        handleExtraMessage(getIntent());
    }

    private void handleExtraMessage(Intent intent) {
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        // 使用 message
        if (Schema.TOOL_OCR.equalsIgnoreCase(message)) {
            // show file picker
            Toast.makeText(this, "OCR识别图文功能即将上线，敬请期待。", Toast.LENGTH_SHORT).show();
        } else if (Schema.TOOL_TTS.equalsIgnoreCase(message)) {
            Toast.makeText(this, "TTS文字转语音功能即将上线，敬请期待。", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        super.messagesAdapter.addToStart(
                MessagesFixtures.getTextMessage(input.toString()), true);
        return true;
    }

    @Override
    public void onAddAttachments() {
        new AlertDialog.Builder(this)
                .setItems(R.array.view_types_dialog, this)
                .show();
    }

    @Override
    public boolean hasContentFor(Message message, byte type) {
        if (type == CONTENT_TYPE_VOICE) {
            return message.getVoice() != null
                    && message.getVoice().getUrl() != null
                    && !message.getVoice().getUrl().isEmpty();
        }
        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case 0:
                messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
                break;
            case 1:
                messagesAdapter.addToStart(MessagesFixtures.getVoiceMessage(), true);
                break;
        }
    }

    private void initAdapter() {
        MessageHolders holders = new MessageHolders()
                .registerContentType(
                        CONTENT_TYPE_VOICE,
                        IncomingVoiceMessageViewHolder.class,
                        R.layout.item_custom_incoming_voice_message,
                        OutcomingVoiceMessageViewHolder.class,
                        R.layout.item_custom_outcoming_voice_message,
                        this);


        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, holders, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        this.messagesList.setAdapter(super.messagesAdapter);
    }
}
