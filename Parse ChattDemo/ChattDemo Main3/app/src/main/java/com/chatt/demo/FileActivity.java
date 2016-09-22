package com.chatt.demo;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import com.chatt.demo.custom.CustomActivity;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;


public class FileActivity extends CustomActivity {
    Uri mMediaUri;
    String mFileType, mReceiver, mSender,mFilePath;
    TextView lblBuddy;
    EditText textCaption;
    Bitmap bitmap;
    ImageView imgViewFile;
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_VIDEO = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_file);
        //setupActionBar();

        textCaption = (EditText) findViewById(R.id.txtCaption);
        textCaption.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        setTouchNClick(R.id.imgBtnFileSend);
        imgViewFile = (ImageView ) findViewById (R.id.imgViewFile);
        lblBuddy = (TextView) findViewById(R.id.labelReceiver);

        mFileType = getIntent().getExtras().getString("fileType");
        mSender = getIntent().getExtras().getString("sender");
        mReceiver = getIntent().getExtras().getString("receiver");
        lblBuddy.setText(mReceiver);
        mMediaUri = getIntent().getData();

        mFilePath=getPath(FileActivity.this,mMediaUri);

        File imgFile = new  File(mFilePath);
        if(imgFile.exists()) {
            if (mFileType.equals(TYPE_IMAGE)) {


                bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgViewFile.setBackgroundResource(R.color.transparent);
                imgViewFile.setImageBitmap(bitmap);
            } else if (mFileType.equals(TYPE_VIDEO)) {
                Toast.makeText(FileActivity.this, mFileType, Toast.LENGTH_LONG).show();
                bitmap = ThumbnailUtils.createVideoThumbnail(mFilePath, MediaStore.Images.Thumbnails.MINI_KIND);
                imgViewFile.setBackgroundResource(R.color.transparent);
                imgViewFile.setImageBitmap(bitmap);
            } else {
                Toast.makeText(FileActivity.this, R.string.error_selecting_file, Toast.LENGTH_LONG).show();
            }
        }
        }








    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
             return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.imgBtnFileSend) {
            String text = textCaption.getText().toString();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textCaption.getWindowToken(), 0);
            //Intent previousScreen = new Intent(getApplicationContext(), com.chatt.demo.Chat.class);
            //Sending the data to Activity_A
            this.getIntent().setData(mMediaUri);
            this.getIntent().putExtra("fileType",mFileType);
            this.getIntent().putExtra("filePath",mFilePath);
            this.getIntent().putExtra("caption",text);
            setResult(1000,this.getIntent());
            finish();
        }


    }
}
