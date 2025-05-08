package com.stfalcon.chatkit.sample.features.wiki;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.developer.filepicker.controller.DialogSelectionListener;
import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.BuildConfig;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.common.data.fixtures.MessagesFixtures;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.intent.Schema;
import com.stfalcon.chatkit.sample.features.demo.DemoMessagesActivity;
import com.stfalcon.chatkit.sample.features.demo.custom.media.holders.IncomingVoiceMessageViewHolder;
import com.stfalcon.chatkit.sample.features.demo.custom.media.holders.OutcomingVoiceMessageViewHolder;

import java.io.File;

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

    private String extraMessagePending = null;


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

        preHandleExtraMessage(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        preHandleExtraMessage(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        postHandleExtraMessage();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != filePickerDialog && filePickerDialog.isShowing()) {
            filePickerDialog.dismiss();
            extraMessagePending = null;
        }
    }

    private void preHandleExtraMessage(Intent intent) {
        extraMessagePending = intent.getStringExtra(EXTRA_MESSAGE);
    }

    private void postHandleExtraMessage() {
        // 使用 message
        if (Schema.TOOL_OCR.equalsIgnoreCase(extraMessagePending)) {
            showFilePickerDialog();
        } else if (Schema.TOOL_TTS.equalsIgnoreCase(extraMessagePending)) {
            Toast.makeText(this, "TTS文字转语音功能即将上线，敬请期待。", Toast.LENGTH_SHORT).show();
        } else {
            // nothing need to do.
        }
        extraMessagePending = null;
    }

    //...Your activity code goes here like onCreate() methods and all...

    private final static int APP_STORAGE_ACCESS_REQUEST_CODE = 501;
    private static final int REQUEST_STORAGE_PERMISSIONS = 123;
    private static final int REQUEST_MEDIA_PERMISSIONS = 456;
    private final String readPermission = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String writePermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //As the device is Android 13 and above so I want the permission of accessing Audio, Images, Videos
            //You can ask permission according to your requirements what you want to access.
            String audioPermission = android.Manifest.permission.READ_MEDIA_AUDIO;
            String imagesPermission = android.Manifest.permission.READ_MEDIA_IMAGES;
            String videoPermission = android.Manifest.permission.READ_MEDIA_VIDEO;
            // Check for permissions and request them if needed
            if (ContextCompat.checkSelfPermission(ChatMessagesActivity.this, audioPermission) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ChatMessagesActivity.this, imagesPermission) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ChatMessagesActivity.this, videoPermission) == PackageManager.PERMISSION_GRANTED) {
                // You have the permissions, you can proceed with your media file operations.
                //Showing dialog when Show Dialog button is clicked.
                filePickerDialog.show();
            } else {
                // You don't have the permissions. Request them.
                ActivityCompat.requestPermissions(ChatMessagesActivity.this, new String[]{audioPermission, imagesPermission, videoPermission}, REQUEST_MEDIA_PERMISSIONS);
            }
        } else {
            //Android version is below 13 so we are asking normal read and write storage permissions
            // Check for permissions and request them if needed
            if (ContextCompat.checkSelfPermission(ChatMessagesActivity.this, readPermission) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(ChatMessagesActivity.this, writePermission) == PackageManager.PERMISSION_GRANTED) {
                // You have the permissions, you can proceed with your file operations.
                // Show the file picker dialog when needed
                filePickerDialog.show();
            } else {
                // You don't have the permissions. Request them.
                ActivityCompat.requestPermissions(ChatMessagesActivity.this, new String[]{readPermission, writePermission}, REQUEST_STORAGE_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions were granted. You can proceed with your file operations.
                //Showing dialog when Show Dialog button is clicked.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    //Android version is 11 and above so to access all types of files we have to give
                    //special permission so show user a dialog..
                    accessAllFilesPermissionDialog();
                } else {
                    //Android version is 10 and below so need of special permission...
                    filePickerDialog.show();
                }
            } else {
                // Permissions were denied. Show a rationale dialog or inform the user about the importance of these permissions.
                showRationaleDialog();
            }
        }

        //This conditions only works on Android 13 and above versions
        if (requestCode == REQUEST_MEDIA_PERMISSIONS) {
            if (grantResults.length > 0 && areAllPermissionsGranted(grantResults)) {
                // Permissions were granted. You can proceed with your media file operations.
                //Showing dialog when Show Dialog button is clicked.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    //Android version is 11 and above so to access all types of files we have to give
                    //special permission so show user a dialog..
                    accessAllFilesPermissionDialog();
                }
            } else {
                // Permissions were denied. Show a rationale dialog or inform the user about the importance of these permissions.
                showRationaleDialog();
            }
        }
    }

    private boolean areAllPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void showRationaleDialog() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, readPermission) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, writePermission) ) {
            // Show a rationale dialog explaining why the permissions are necessary.
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This app needs storage permissions to read and write files.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Request permissions when the user clicks OK.
                        ActivityCompat.requestPermissions(ChatMessagesActivity.this, new String[]{readPermission, writePermission}, REQUEST_STORAGE_PERMISSIONS);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                        // Handle the case where the user cancels the permission request.
                    })
                    .show();
        } else {
            // Request permissions directly if no rationale is needed.
            ActivityCompat.requestPermissions(this, new String[]{readPermission, writePermission}, REQUEST_STORAGE_PERMISSIONS);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void accessAllFilesPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Needed")
                .setMessage("This app needs all files access permissions to view files from your storage. Clicking on OK will redirect you to new window were you have to enable the option.")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Request permissions when the user clicks OK.
                    Intent intent = new Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, Uri.parse("package:" + BuildConfig.APPLICATION_ID));
                    startActivityForResult(intent, APP_STORAGE_ACCESS_REQUEST_CODE);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    // Handle the case where the user cancels the permission request.
                    filePickerDialog.show();
                })
                .show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_STORAGE_ACCESS_REQUEST_CODE) {
            // Permission granted. Now resume your workflow.
            filePickerDialog.show();
        }
    }

    FilePickerDialog filePickerDialog;
    // show file picker
    private void showFilePickerDialog() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        //If you want to view files of all extensions then pass null to properties.extensions
        properties.extensions = null;
        //If you want to view files with specific type of extensions the pass string array to properties.extensions
        properties.extensions = new String[]{"pdf","jpg","png","doc", "docx", "xls","xlsx", "ppt","pptx", "csv", "txt", "c", "java", "kt"};
        properties.show_hidden_files = false;

        FilePickerDialog dialog = new FilePickerDialog(this, properties);
        dialog.setTitle("选择1个文件进行OCR识别");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                if (null == files || files.length != 1) {
                    //files is the array of the paths of files selected by the Application User.
                    Toast.makeText(ChatMessagesActivity.this, "已经选择文件数: " + files.length + String.join(", ", files), Toast.LENGTH_LONG).show();
                    return;
                }
                performOcr(files[0]);
            }
        });

        //dialog.show();
        filePickerDialog = dialog;
        checkPermissions();
    }

    private void performOcr(String file) {
        Toast.makeText(this, "OCR识别图文功能即将上线，敬请期待。" + file, Toast.LENGTH_LONG).show();
        messagesAdapter.addToStart(MessagesFixtures.getFileMessage(file), true);
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
            return message.getDoc() != null
                    && message.getDoc().getUrl() != null
                    && !message.getDoc().getUrl().isEmpty();
        }
        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        switch (i) {
            case 0:
                showFilePickerDialog();
                //messagesAdapter.addToStart(MessagesFixtures.getImageMessage(), true);
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
