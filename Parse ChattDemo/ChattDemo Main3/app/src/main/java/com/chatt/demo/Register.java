package com.chatt.demo;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chatt.demo.custom.CustomActivity;
import com.chatt.demo.utils.Utils;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

/**
 * The Class Register is the Activity class that shows user registration screen
 * that allows user to register itself on Parse server for this Chat app.
 */
public class Register extends CustomActivity
{

	/** The username EditText. */
	private EditText user;

	/** The password EditText. */
	private EditText pwd;

	/** The email EditText. */
	private EditText email;

	/**for  The Profile image imageview. */
	int REQUEST_CAMERA = 0, SELECT_FILE = 1;
	boolean IMAGE_CHANGE=false;
	ImageButton btnSelect;
	ImageView ivImage;
	Bitmap bitmap;

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		btnSelect = (ImageButton) findViewById(R.id.imgBtn);
		btnSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectImage();
			}
		});
		setTouchNClick(R.id.btnReg);

		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
		email = (EditText) findViewById(R.id.email);
		ivImage = (ImageView) findViewById(R.id.ViewPic);

	}




	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };

		AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (items[item].equals("Take Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					startActivityForResult(intent, REQUEST_CAMERA);
				} else if (items[item].equals("Choose from Library")) {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					intent.setType("image/*");
					startActivityForResult(intent,SELECT_FILE);
				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == SELECT_FILE) {
				onSelectFromGalleryResult(data);
				IMAGE_CHANGE=true;
			}
			else if (requestCode == REQUEST_CAMERA) {
				onCaptureImageResult(data);
				IMAGE_CHANGE=true;
			}
		}else{
			Utils.showDialog(this,"requested file code invalid");
		}

	}

	private void onCaptureImageResult(Intent data) {
		bitmap =(Bitmap) data.getExtras().get("data");
        bitmap=Bitmap.createScaledBitmap(bitmap,450,450,true);

        /*ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		//thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

		File destination = new File(Environment.getExternalStorageDirectory(),
				System.currentTimeMillis() + ".jpg");

		FileOutputStream fo;
		try {
			destination.createNewFile();
			fo = new FileOutputStream(destination);
			fo.write(bytes.toByteArray());
			fo.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		ivImage.setBackgroundResource(R.color.transparent);
        ivImage.setMinimumHeight(350);
        ivImage.setMinimumWidth(350);
		ivImage.setImageBitmap(bitmap);

	}

	@SuppressWarnings("deprecation")
	private void onSelectFromGalleryResult(Intent data) {
		Uri selectedImageUri = data.getData();
		String[] projection = {MediaStore.Images.Media.DATA};
				//String[] projection = { MediaColumns.DATA };
		//Cursor cursor = managedQuery(selectedImageUri, projection, null, null,null);
		Cursor cursor = getContentResolver().query(selectedImageUri,
				projection, null, null, null);
		cursor.moveToFirst();
		int column_index = cursor.getColumnIndexOrThrow(projection[0]);
		cursor.moveToFirst();

		String selectedImagePath = cursor.getString(column_index);
		//ivImage.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath));
		//Bitmap bitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(selectedImagePath, options);
		final int REQUIRED_SIZE = 200;
		int scale = 1;
		while (options.outWidth / scale / 2 >= REQUIRED_SIZE
				&& options.outHeight / scale / 2 >= REQUIRED_SIZE)
			scale *= 2;
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		 bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

		ivImage.setBackgroundResource(R.color.transparent);
		ivImage.setImageBitmap(bitmap);
	}






	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
			final String u = user.getText().toString();
			final String p = pwd.getText().toString();
			final String e = email.getText().toString();
//      	  if(IMAGE_CHANGE==true){
//            Utils.showDialog(this,"trgd");
//            int bitHeight=bitmap.getHeight();
//            int bitWidth=bitmap.getWidth();
//            Utils.showDialog(this,bitHeight);
//            Utils.showDialog(this,bitWidth);
//            return;
//        }
			if (u.length() == 0 || p.length() == 0 || e.length() == 0) {
				Utils.showDialog(this, R.string.err_fields_empty);
				return;
			}else if(IMAGE_CHANGE==false){
				new AlertDialog.Builder(Register.this)
						.setTitle("Warning!!!")
						.setMessage(R.string.RegisterDia)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int whichButton) {


								final ProgressDialog dia = ProgressDialog.show(Register.this, null,
										getString(R.string.alert_wait));

								final ParseUser pu = new ParseUser();
								pu.setEmail(e);
								pu.setPassword(p);
								pu.setUsername(u);
								pu.setUsername(u);

								ivImage.setDrawingCacheEnabled(true);
								Bitmap bitmap = ivImage.getDrawingCache();

								// Convert it to byte
								ByteArrayOutputStream stream = new ByteArrayOutputStream();
								// Compress image to lower quality scale 1 - 100
								bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
								byte[] image = stream.toByteArray();

								// Create the ParseFile
								//ParseFile file = new ParseFile(e, image);
								// Upload the image into Parse Cloud
								//file.signUpInBackground();


								if (ivImage!=null) {
									ParseFile file = new ParseFile("profile_pic.jpg", image);

									pu.put("picture", file);

									file.saveInBackground(new SaveCallback() {
										public void done(ParseException e) {
											// If successful add file to user and signUpInBackground
											if (null == e) {
												pu.signUpInBackground(new SignUpCallback() {

													@Override
													public void done(ParseException e) {
														dia.dismiss();
														if (e == null) {
															
															UserList.user = pu;
															startActivity(new Intent(Register.this, UserList.class));
															setResult(RESULT_OK);
															finish();
														} else {
															Utils.showDialog(
																	Register.this,
																	getString(R.string.err_singup) + "ebola"
																			+ e.getMessage());
															e.printStackTrace();
														}
													}
												});
											} else {
												dia.dismiss();
												Utils.showDialog(
														Register.this,
														getString(R.string.err_singup) + "gfdebola"
																+ e.getMessage());
												e.printStackTrace();
											}

										}
									});




								}

							}
						})
						.setNegativeButton(android.R.string.no,new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int whichButton) {


							}
						}).show();
		}else{

				final ProgressDialog dia = ProgressDialog.show(Register.this, null,
						getString(R.string.alert_wait));

				final ParseUser pu = new ParseUser();
				pu.setEmail(e);
				pu.setPassword(p);
				pu.setUsername(u);
				pu.setUsername(u);

				ivImage.setDrawingCacheEnabled(true);
				Bitmap bitmap = ivImage.getDrawingCache();

				// Convert it to byte
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				// Compress image to lower quality scale 1 - 100
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] image = stream.toByteArray();

				// Create the ParseFile
				//ParseFile file = new ParseFile(e, image);
				// Upload the image into Parse Cloud
				//file.signUpInBackground();


				if (ivImage!=null) {
					ParseFile file = new ParseFile("profile_pic.jpg", image);

					pu.put("picture", file);

					file.saveInBackground(new SaveCallback() {
						public void done(ParseException e) {
							// If successful add file to user and signUpInBackground
							if (null == e) {
								pu.signUpInBackground(new SignUpCallback() {

									@Override
									public void done(ParseException e) {
										dia.dismiss();
										if (e == null) {
											
											UserList.user = pu;
											startActivity(new Intent(Register.this, UserList.class));
											setResult(RESULT_OK);
											finish();
										} else {
											Utils.showDialog(
													Register.this,
													getString(R.string.err_singup) + "ebola"
															+ e.getMessage());
											e.printStackTrace();
										}
									}
								});
							} else {
								dia.dismiss();
								Utils.showDialog(
										Register.this,
										getString(R.string.err_singup) + "gfdebola"
												+ e.getMessage());
								e.printStackTrace();
							}

						}
					});




				}

			}


	}
}
