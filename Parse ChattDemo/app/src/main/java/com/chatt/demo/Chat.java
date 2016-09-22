package com.chatt.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.VoicemailContract;
import android.text.InputType;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.chatt.demo.custom.CustomActivity;
import com.chatt.demo.model.Conversation;
import com.chatt.demo.utils.Const;
import com.chatt.demo.utils.Utils;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * The Class Chat is the Activity class that holds main chat screen. It shows
 * all the conversation messages between two users and also allows the user to
 * send and receive messages.
 */
public class Chat extends CustomActivity {

	/*
	* necessary variables and dependencies for attachment button*/

	public static final String TAG = Chat.class.getSimpleName();

	public static final int TAKE_PHOTO_REQUEST = 0;
	public static final int TAKE_VIDEO_REQUEST = 1;
	public static final int PICK_PHOTO_REQUEST = 2;
	public static final int PICK_VIDEO_REQUEST = 3;
	public static final int MEDIA_TYPE_IMAGE = 4;
	public static final int MEDIA_TYPE_VIDEO = 5;
	public static final String TYPE_IMAGE = "image";
	public static final String TYPE_VIDEO = "video";

	public static final int FILE_SIZE_LIMIT = 1024*1024*10; // 10 MB

	public Uri mMediaUri;
	String fileType,filePath,caption;

	/**
	 * The Conversation list.
	 */
	private ArrayList<Conversation> convList;

	/**
	 * The chat adapter.
	 */
	private ChatAdapter adp;

	/**
	 * The Editext to compose the message.
	 */
	private EditText txt;

	/**
	 * The user name of buddy.
	 */
	private String buddy,URLpath;
	private View listItem;

	/**
	 * The date of last message in conversation.
	 */
	private Date lastMsgDate;


	/**
	 * Flag to hold if the activity is running or not.
	 */
	private boolean isRunning;

	/**
	 * The handler.
	 */
	private static Handler handler;
	private static Context mContext;
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.chat);
		mContext = Chat.this;
		convList = new ArrayList<Conversation>();
		ListView list = (ListView) findViewById(R.id.list);
		adp = new ChatAdapter();
		list.setAdapter(adp);
		list.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		list.setStackFromBottom(true);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView adapterView, View view, int position, long id) {
					//Do some more stuff here and launch new activity
				 listItem=view;
				String word=view.getResources().getResourceName(view.getId());


				if (word.length() >= 8) {
					word=word.substring(word.length() - 8);

					if(!word.equals("notImage")){

						final TextView path = (TextView) listItem.findViewById(R.id.textPath);
						URLpath=path.getText().toString();
						try {
							new AlertDialog.Builder(mContext)
									.setTitle("Downloading FIle")
									.setMessage(R.string.Download_file)
									.setIcon(android.R.drawable.ic_dialog_alert)
									.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

										public void onClick(DialogInterface dialog, int whichButton) {



												new MyTask().execute();

										}
									})
									.setNegativeButton(android.R.string.no, null).show();
						}catch (NoSuchElementException e){return;}
						catch(NullPointerException e){return;}


					}else {
						// whatever is appropriate in this case

					}
				}else {
						// whatever is appropriate in this case

					}






					//Toast.makeText(mContext,view.getResources().getResourceName(view.getId()),Toast.LENGTH_LONG).show();

			}
		});

		txt = (EditText) findViewById(R.id.txt);
		txt.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_FLAG_MULTI_LINE);

		setTouchNClick(R.id.btnSend);
		setTouchNClick(R.id.action_camera);
		 buddy = getIntent().getStringExtra(Const.EXTRA_DATA);
		getActionBar().setTitle(buddy);
		handler = new Handler();
		freeMemory();
	}

	private class MyTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			String appName = Chat.this.getString(R.string.app_name);
			File mediaStorageDir = new File(
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					appName);
			File mediaFile;
			Date now = new Date();
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

			String path = mediaStorageDir.getPath() + File.separator;
			String [] datatype=URLpath.split("\\.");
			datatype[0]=datatype[(datatype.length)-1];

				mediaFile = new File(path + "FILE_" + timestamp +"."+ datatype[0]);

			Log.d(TAG, "doInBackground: mediaStorageDir"+mediaFile.toString());

			downloadFile(URLpath,mediaFile);
			return null;
		}

		@Override
		protected void onPostExecute(Void aVoid) {
			super.onPostExecute(aVoid);
			Toast.makeText(mContext,"File saved in the Gallery",Toast.LENGTH_LONG).show();
		}
	}





	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.attachment, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		isRunning = true;
		loadConversationList();
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		isRunning = false;
	}

	/* (non-Javadoc)
	 * @see com.socialshare.custom.CustomFragment#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		super.onClick(v);
		if (v.getId() == R.id.btnSend) {
			sendMessage();
		}


	}

	/**
	 * Call this method to Send message to opponent. It does nothing if the text
	 * is empty otherwise it creates a Parse object for Chat message and send it
	 * to Parse server.
	 */
	private void sendMessage() {

		if (txt.length() == 0)
			return;

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

		String s = txt.getText().toString();
		final Conversation c = new Conversation(s, new Date(),
				UserList.user.getUsername());
		c.setStatus(Conversation.STATUS_SENDING);
		convList.add(c);
		adp.notifyDataSetChanged();
		txt.setText(null);

		ParseObject po = new ParseObject("Chat");
		po.put("sender", UserList.user.getUsername());
		po.put("receiver", buddy);
		po.put("fileType", "text");
		po.put("message", s);
		po.saveEventually(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if (e == null)
					c.setStatus(Conversation.STATUS_SENT);
				else
					c.setStatus(Conversation.STATUS_FAILED);
				adp.notifyDataSetChanged();
			}
		});

	}
	/*
	*createMessage() is the method which makes a parse object using all the needful
	* files and texts in case of a image/video transfer
	*/
	protected ParseObject createMessage() {
		ParseObject message = new ParseObject("Chat");
		message.put("sender", UserList.user.getUsername());
//		message.put(ParseConstants.KEY_SENDER_NAME, ParseUser.getCurrentUser().getUsername());
		message.put("receiver", buddy);
		message.put("fileType", fileType);
		message.put("message", caption);

		byte[] fileBytes = Utils.getByteArrayFromFile(this, mMediaUri);

		if (fileBytes == null) {
			return null;
		}
		else {

			String fileName = Utils.getFileName(this, mMediaUri, fileType);

			ParseFile file = new ParseFile(fileName, fileBytes);

			message.put("File", file);
			mMediaUri=null;
			final Conversation c = new Conversation(caption, new Date(),
					UserList.user.getUsername(),fileType,file);
			c.setStatus(Conversation.STATUS_SENDING);
			convList.add(c);
			adp.notifyDataSetChanged();
			return message;

		}
	}



	protected void send(ParseObject message) {
		message.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e == null) {
					// success!
					Toast.makeText(Chat.this, R.string.success_message, Toast.LENGTH_LONG).show();
					//sendPushNotifications();
				}
				else {
					AlertDialog.Builder builder = new AlertDialog.Builder(Chat.this);
					builder.setMessage(R.string.error_sending_message)
							.setTitle(R.string.error_selecting_file_title)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});
	}

	/**
	 * Load the conversation list from Parse server and save the date of last
	 * message that will be used to load only recent new messages
	 */
	private void loadConversationList() {
		ParseQuery<ParseObject> q = ParseQuery.getQuery("Chat");
		if (convList.size() == 0) {
			// load all messages...
			ArrayList<String> al = new ArrayList<String>();
			al.add(buddy);
			al.add(UserList.user.getUsername());
			q.whereContainedIn("sender", al);
			q.whereContainedIn("receiver", al);
		} else {
			// load only newly received message..
			if (lastMsgDate != null)
				q.whereGreaterThan("createdAt", lastMsgDate);
			q.whereEqualTo("sender", buddy);
			q.whereEqualTo("receiver", UserList.user.getUsername());
		}
		q.orderByDescending("createdAt");
		q.setLimit(30);
		q.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> li, ParseException e) {
				Conversation c;
				if (li != null && li.size() > 0) {
					for (int i = li.size() - 1; i >= 0; i--) {
						ParseObject po = li.get(i);
						if(po.getString("fileType").equals(TYPE_IMAGE) || po.getString("fileType").equals(TYPE_VIDEO)){
						c = new Conversation(po
								.getString("message"), po.getCreatedAt(), po
								.getString("sender"),po.getString("fileType"),po.getParseFile("File"));
						}
						else{
						c = new Conversation(po
									.getString("message"), po.getCreatedAt(), po
									.getString("sender"));
						}
						convList.add(c);
						if (lastMsgDate == null
								|| lastMsgDate.before(c.getDate()))
							lastMsgDate = c.getDate();
						adp.notifyDataSetChanged();
					}
				}
				handler.postDelayed(new Runnable() {

					@Override
					public void run() {
						if (isRunning)
							loadConversationList();
					}
				}, 1000);
			}
		});

	}

	/**
	 * The Class ChatAdapter is the adapter class for Chat ListView. This
	 * adapter shows the Sent or Receieved Chat message in each list item.
	 */
	private class ChatAdapter extends BaseAdapter {

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			return convList.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Conversation getItem(int arg0) {
			return convList.get(arg0);
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int pos, View v, ViewGroup arg2) {
			Conversation c = getItem(pos) ;
			if (c.isSent()) {
				if(c.getFileType().equals(TYPE_IMAGE)||c.getFileType().equals(TYPE_VIDEO)) {
					try{
						v = getLayoutInflater().inflate(R.layout.chat_item_sent_image, null);
						TextView textPath=(TextView) v.findViewById(R.id.textPath);
						ParseFile parseobj=c.getFile();

						textPath.setText(parseobj.getUrl());
						parseobj=null;

					}catch(Exception e){Toast.makeText(Chat.this,"aaa",
							Toast.LENGTH_LONG).show();
					}

				}else {
					v = getLayoutInflater().inflate(R.layout.chat_item_sent, null);
				}
			}
			else{
				if(c.getFileType().equals(TYPE_IMAGE)||c.getFileType().equals(TYPE_VIDEO)) {
					try{
						v = getLayoutInflater().inflate(R.layout.chat_item_rcv_image, null);
						TextView textPath=(TextView) v.findViewById(R.id.textPath);
						ParseFile parseobj=c.getFile();

						textPath.setText(parseobj.getUrl());
						parseobj=null;
					}catch(Exception e){
						Toast.makeText(Chat.this,"bbb",
								Toast.LENGTH_LONG).show();
					}
				}else {
					v = getLayoutInflater().inflate(R.layout.chat_item_rcv, null);
				}
			}

			TextView lbl = (TextView) v.findViewById(R.id.lbl1);
			lbl.setText(DateUtils.getRelativeDateTimeString(Chat.this, c
							.getDate().getTime(), DateUtils.SECOND_IN_MILLIS,
					DateUtils.DAY_IN_MILLIS, 0));

			lbl = (TextView) v.findViewById(R.id.lbl2);
			lbl.setText(c.getMsg());

			lbl = (TextView) v.findViewById(R.id.lbl3);
			if (c.isSent()) {
				if (c.getStatus() == Conversation.STATUS_SENT)
					lbl.setText("Delivered");
				else if (c.getStatus() == Conversation.STATUS_SENDING)
					lbl.setText("Sending...");
				else
					lbl.setText("Failed");
			} else
				lbl.setText("");
			c=null;
			freeMemory();
			return v;
		}

	}
	/*
	* starting to write the necessary functions to use the image/video picker
	*this will create a dialogue box containing the options and then will do as per the selected actions
	*/
	protected DialogInterface.OnClickListener mDialogListener =
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(which) {
						case 0: // Take picture
							Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
							if (mMediaUri == null) {
								// display an error
								Toast.makeText(Chat.this, R.string.error_external_storage,
										Toast.LENGTH_LONG).show();
							}
							else {
								takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
								startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
							}
							break;
						case 1: // Take video
							Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
							mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
							if (mMediaUri == null) {
								// display an error
								Toast.makeText(Chat.this, R.string.error_external_storage,
										Toast.LENGTH_LONG).show();
							}
							else {
								videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);
								videoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
								videoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // 0 = lowest res
								startActivityForResult(videoIntent, TAKE_VIDEO_REQUEST);
							}
							break;
						case 2: // Choose picture
							Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
							choosePhotoIntent.setType("image/*");
							startActivityForResult(choosePhotoIntent, PICK_PHOTO_REQUEST);
							break;
						case 3: // Choose video
							Intent chooseVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
							chooseVideoIntent.setType("video/*");
							Toast.makeText(Chat.this, R.string.video_file_size_warning, Toast.LENGTH_LONG).show();
							startActivityForResult(chooseVideoIntent, PICK_VIDEO_REQUEST);
							break;
					}
				}
				private Uri getOutputMediaFileUri(int mediaType) {
					// To be safe, you should check that the SDCard is mounted
					// using Environment.getExternalStorageState() before doing this.
					if (isExternalStorageAvailable()) {
						// get the URI

						// 1. Get the external storage directory
						String appName = Chat.this.getString(R.string.app_name);
						File mediaStorageDir = new File(
								Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
								appName);

						// 2. Create our subdirectory
						if (! mediaStorageDir.exists()) {
							if (! mediaStorageDir.mkdirs()) {
								Log.e(TAG, "Failed to create directory.");
								return null;
							}
						}

						// 3. Create a file name
						// 4. Create the file
						File mediaFile;
						Date now = new Date();
						String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

						String path = mediaStorageDir.getPath() + File.separator;
						if (mediaType == MEDIA_TYPE_IMAGE) {
							mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
						}
						else if (mediaType == MEDIA_TYPE_VIDEO) {
							mediaFile = new File(path + "VID_" + timestamp + ".mp4");
						}
						else {
							return null;
						}

						Log.d(TAG, "File: " + Uri.fromFile(mediaFile));

						// 5. Return the file's URI
						return Uri.fromFile(mediaFile);
					}
					else {
						return null;
					}
				}

				private boolean isExternalStorageAvailable() {
					String state = Environment.getExternalStorageState();

					if (state.equals(Environment.MEDIA_MOUNTED)) {
						return true;
					}
					else {
						return false;
					}
				}
			};

	/*starting OnActivityResult funtion for all the attachment menu options*/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == PICK_PHOTO_REQUEST || requestCode == PICK_VIDEO_REQUEST) {
				if (data == null) {
					Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_LONG).show();
				}
				else {
					mMediaUri = data.getData();
				}

				Log.i(TAG, "Media URI: " + mMediaUri);

				if (requestCode == PICK_VIDEO_REQUEST) {
					// make sure the file is less than 10 MB
					int fileSize = 0;
					InputStream inputStream = null;

					try {
						inputStream = getContentResolver().openInputStream(mMediaUri);
						fileSize = inputStream.available();
					}
					catch (FileNotFoundException e) {
						Toast.makeText(this, R.string.error_opening_file, Toast.LENGTH_LONG).show();
						return;
					}
					catch (IOException e) {
						Toast.makeText(this, R.string.error_opening_file, Toast.LENGTH_LONG).show();
						return;
					}
					finally {
						try {
							inputStream.close();
						} catch (IOException e) { /* Intentionally blank */ }
					}

					if (fileSize >= FILE_SIZE_LIMIT) {
						Toast.makeText(this, R.string.error_file_size_too_large, Toast.LENGTH_LONG).show();
						return;
					}
				}
			}
			else {
				Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				mediaScanIntent.setData(mMediaUri);
				sendBroadcast(mediaScanIntent);
			}
				/*code to send the data to the recipient*/
				String fileType;
				if (requestCode == PICK_PHOTO_REQUEST || requestCode == TAKE_PHOTO_REQUEST ) {
						fileType = Chat.TYPE_IMAGE;
					}
					else{
					fileType = Chat.TYPE_VIDEO;
					}

			Intent recipientsIntent = new Intent(this, FileActivity.class);
			recipientsIntent.setData(mMediaUri);
			recipientsIntent.putExtra("fileType", fileType);
			recipientsIntent.putExtra("sender", UserList.user.getUsername());
			recipientsIntent.putExtra("receiver", buddy);
			mMediaUri=null;fileType=null;
			startActivityForResult(recipientsIntent,1000);
				}
		/*
		* creating the option to work with the intent result we get back from
		* reciepents intent which shows the selected image
		* Here we get all the details of previous intent and send the image /video by calling the modified
		* sendMessage() method
		*/
		else if (resultCode == 1000) {

			super.onActivityResult(requestCode, resultCode, data);
			mMediaUri=null;
			freeMemory();
			mMediaUri=data.getData();
			fileType=data.getExtras().getString("fileType");
			filePath=data.getExtras().getString("filePath");
			caption=data.getExtras().getString("caption");

			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

			freeMemory();
			ParseObject message = createMessage();
			if (message == null) {
				// error
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage(R.string.error_selecting_file)
						.setTitle("ERROR")
						.setPositiveButton(android.R.string.ok, null);
				AlertDialog dialog = builder.create();
				dialog.show();
			}
			else {
				send(message);
				//Toast.makeText(this,filePath, Toast.LENGTH_LONG).show();

			}
		}

		else if (resultCode != RESULT_CANCELED) {
			Toast.makeText(this, R.string.general_error, Toast.LENGTH_LONG).show();
		}
	}

	/* (non-Javadoc)
      * writing the code to handle attachment button
      * basically the UI portion of the button
      * where one can choose to send picture or video fom the action bar menu option
     */
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();

		switch (itemId) {
			case R.id.action_camera:
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setItems(R.array.camera_choices, mDialogListener);
				AlertDialog dialog = builder.create();
				dialog.show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	public void freeMemory(){
		System.runFinalization();
		Runtime.getRuntime().gc();
		System.gc();
	}

	private static void downloadFile(String url, File outputFile) {
		try {
			URL u = new URL(url);
			URLConnection conn = u.openConnection();
			int contentLength = conn.getContentLength();

			DataInputStream stream = new DataInputStream(u.openStream());

			byte[] buffer = new byte[contentLength];
			stream.readFully(buffer);
			stream.close();

			DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
			fos.write(buffer);
			fos.flush();
			fos.close();
			Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			mediaScanIntent.setData(Uri.fromFile(outputFile));
			mContext.sendBroadcast(mediaScanIntent);
		} catch(FileNotFoundException e) {
			return; // swallow a 404
		} catch (IOException e) {
			return; // swallow a 404
		}
	}
}

