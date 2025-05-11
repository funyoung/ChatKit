package com.stfalcon.chatkit.sample.features.chat;

import android.view.View;
import android.widget.TextView;

import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.sample.common.data.model.Message;

import io.noties.markwon.Markwon;
import io.noties.markwon.ext.tables.TableAwareMovementMethod;
import io.noties.markwon.ext.tables.TablePlugin;
import io.noties.markwon.linkify.LinkifyPlugin;
import io.noties.markwon.movement.MovementMethodPlugin;

/*
 * Created by troy379 on 05.04.17.
 */
public class MarkdownIncomingTextMessageViewHolder
        extends MessageHolders.IncomingTextMessageViewHolder<Message> {
    public MarkdownIncomingTextMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    protected void onBindText(TextView textView, String content) {
//        textView.setText(content);

        Markwon markwon = Markwon.builder(text.getContext().getApplicationContext())
                .usePlugin(LinkifyPlugin.create())
                .usePlugin(TablePlugin.create(text.getContext().getApplicationContext()))
                .usePlugin(MovementMethodPlugin.create(TableAwareMovementMethod.create()))
                .build();

        markwon.setMarkdown(textView, content);
    }
}