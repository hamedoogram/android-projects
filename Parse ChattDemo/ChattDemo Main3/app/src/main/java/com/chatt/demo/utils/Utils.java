package com.chatt.demo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.apache.commons.io.IOUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * The Class Utils is a common class that hold many kind of different useful
 * utility methods.
 */
public class Utils
{

	public static final int SHORT_SIDE_TARGET = 1280;
	public static final String TYPE_IMAGE = "image";
	public static final String TYPE_VIDEO = "video";
	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param msg
	 *            the msg
	 * @param btn1
	 *            the btn1
	 * @param btn2
	 *            the btn2
	 * @param listener1
	 *            the listener1
	 * @param listener2
	 *            the listener2
	 * @return the alert dialog
	 */
	public static AlertDialog showDialog(Context ctx, String msg, String btn1,
			String btn2, DialogInterface.OnClickListener listener1,
			DialogInterface.OnClickListener listener2)
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		// builder.setTitle(R.string.app_name);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton(btn1, listener1);
		if (btn2 != null && listener2 != null)
			builder.setNegativeButton(btn2, listener2);

		AlertDialog alert = builder.create();
		alert.show();
		return alert;

	}

	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param msg
	 *            the msg
	 * @param btn1
	 *            the btn1
	 * @param btn2
	 *            the btn2
	 * @param listener1
	 *            the listener1
	 * @param listener2
	 *            the listener2
	 * @return the alert dialog
	 */
	public static AlertDialog showDialog(Context ctx, int msg, int btn1,
			int btn2, DialogInterface.OnClickListener listener1,
			DialogInterface.OnClickListener listener2)
	{

		return showDialog(ctx, ctx.getString(msg), ctx.getString(btn1),
				ctx.getString(btn2), listener1, listener2);

	}

	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param msg
	 *            the msg
	 * @param btn1
	 *            the btn1
	 * @param btn2
	 *            the btn2
	 * @param listener
	 *            the listener
	 * @return the alert dialog
	 */
	public static AlertDialog showDialog(Context ctx, String msg, String btn1,
			String btn2, DialogInterface.OnClickListener listener)
	{

		return showDialog(ctx, msg, btn1, btn2, listener,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id)
					{

						dialog.dismiss();
					}
				});

	}

	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param msg
	 *            the msg
	 * @param btn1
	 *            the btn1
	 * @param btn2
	 *            the btn2
	 * @param listener
	 *            the listener
	 * @return the alert dialog
	 */
	public static AlertDialog showDialog(Context ctx, int msg, int btn1,
			int btn2, DialogInterface.OnClickListener listener)
	{

		return showDialog(ctx, ctx.getString(msg), ctx.getString(btn1),
				ctx.getString(btn2), listener);

	}

	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param msg
	 *            the msg
	 * @param listener
	 *            the listener
	 * @return the alert dialog
	 */
	public static AlertDialog showDialog(Context ctx, String msg,
			DialogInterface.OnClickListener listener)
	{

		return showDialog(ctx, msg, ctx.getString(android.R.string.ok), null,
				listener, null);
	}

	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param msg
	 *            the msg
	 * @param listener
	 *            the listener
	 * @return the alert dialog
	 */
	public static AlertDialog showDialog(Context ctx, int msg,
			DialogInterface.OnClickListener listener)
	{

		return showDialog(ctx, ctx.getString(msg),
				ctx.getString(android.R.string.ok), null, listener, null);
	}

	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param msg
	 *            the msg
	 * @return the alert dialog
	 */
	public static AlertDialog showDialog(Context ctx, String msg)
	{

		return showDialog(ctx, msg, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id)
			{

				dialog.dismiss();
			}
		});

	}

	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param msg
	 *            the msg
	 * @return the alert dialog
	 */
	public static AlertDialog showDialog(Context ctx, int msg)
	{

		return showDialog(ctx, ctx.getString(msg));

	}

	/**
	 * Show dialog.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param title
	 *            the title
	 * @param msg
	 *            the msg
	 * @param listener
	 *            the listener
	 */
	public static void showDialog(Context ctx, int title, int msg,
			DialogInterface.OnClickListener listener)
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setMessage(msg).setCancelable(false)
				.setPositiveButton(android.R.string.ok, listener);
		builder.setTitle(title);
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Hide keyboard.
	 * 
	 * @param ctx
	 *            the ctx
	 */
	public static final void hideKeyboard(Activity ctx)
	{

		if (ctx.getCurrentFocus() != null)
		{
			InputMethodManager imm = (InputMethodManager) ctx
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(ctx.getCurrentFocus().getWindowToken(),
					0);
		}
	}

	/**
	 * Hide keyboard.
	 * 
	 * @param ctx
	 *            the ctx
	 * @param v
	 *            the v
	 */
	public static final void hideKeyboard(Activity ctx, View v)
	{

		try
		{
			InputMethodManager imm = (InputMethodManager) ctx
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static byte[] getByteArrayFromFile(Context context, Uri uri) {
		byte[] fileBytes = null;
		InputStream inStream = null;
		ByteArrayOutputStream outStream = null;

		if (uri.getScheme().equals("content")) {
			try {
				inStream = context.getContentResolver().openInputStream(uri);
				outStream = new ByteArrayOutputStream();

				byte[] bytesFromFile = new byte[1024*1024]; // buffer size (1 MB)
				int bytesRead = inStream.read(bytesFromFile);
				while (bytesRead != -1) {
					outStream.write(bytesFromFile, 0, bytesRead);
					bytesRead = inStream.read(bytesFromFile);
				}

				fileBytes = outStream.toByteArray();
			}
			catch (IOException e) {
				Log.e("Utils", e.getMessage());
			}
			finally {
				try {
					inStream.close();
					outStream.close();
				}
				catch (IOException e) { /*( Intentionally blank */ }
			}
		}
		else {
			try {
				File file = new File(uri.getPath());
				FileInputStream fileInput = new FileInputStream(file);
				fileBytes = IOUtils.toByteArray(fileInput);
			}
			catch (IOException e) {
				Log.e("Utils", e.getMessage());
			}
		}

		return fileBytes;
	}
	public static byte[] reduceImageForUpload(byte[] imageData) {
		Bitmap bitmap = resizeImageMaintainAspectRatio(imageData, SHORT_SIDE_TARGET);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
		byte[] reducedData = outputStream.toByteArray();
		try {
			outputStream.close();
		}
		catch (IOException e) {
			// Intentionally blank
		}

		return reducedData;
	}


	public static Bitmap resizeImageMaintainAspectRatio(byte[] imageData, int shorterSideTarget) {
		Pair<Integer, Integer> dimensions = getDimensions(imageData);

		// Determine the aspect ratio (width/height) of the image
		int imageWidth = dimensions.first;
		int imageHeight = dimensions.second;
		float ratio = (float) dimensions.first / dimensions.second;

		int targetWidth;
		int targetHeight;

		// Determine portrait or landscape
		if (imageWidth > imageHeight) {
			// Landscape image. ratio (width/height) is > 1
			targetHeight = shorterSideTarget;
			targetWidth = Math.round(shorterSideTarget * ratio);
		}
		else {
			// Portrait image. ratio (width/height) is < 1
			targetWidth = shorterSideTarget;
			targetHeight = Math.round(shorterSideTarget / ratio);
		}

		return resizeImage(imageData, targetWidth, targetHeight);
	}

	public static Pair<Integer, Integer> getDimensions(byte[] imageData) {
		// Use BitmapFactory to decode the image
		BitmapFactory.Options options = new BitmapFactory.Options();

		// Only decode the bounds of the image, not the whole image, to get the dimensions
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);

		return new Pair<Integer, Integer>(options.outWidth, options.outHeight);
	}

	/*
	 * Call this static method to resize an image to a specified width and height.
	 *
	 * @param targetWidth  The width to resize to.
	 * @param targetHeight The height to resize to.
	 * @returns 		   The resized image as a Bitmap.
	 */
	public static Bitmap resizeImage(byte[] imageData, int targetWidth, int targetHeight) {
		// Use BitmapFactory to decode the image
		BitmapFactory.Options options = new BitmapFactory.Options();

		// inSampleSize is used to sample smaller versions of the image
		options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);

		// Decode bitmap with inSampleSize and target dimensions set
		options.inJustDecodeBounds = false;

		Bitmap reducedBitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
		Bitmap resizedBitmap = Bitmap.createScaledBitmap(reducedBitmap, targetWidth, targetHeight, false);


		return resizedBitmap;
	}


	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
	public static String getFileName(Context context, Uri uri, String fileType) {
		String fileName = "uploaded_file.";

		if (fileType.equals(TYPE_IMAGE)) {
			fileName += "png";
		}
		else {
			// For video, we want to get the actual file extension
			if (uri.getScheme().equals("content")) {
				// do it using the mime type
				String mimeType = context.getContentResolver().getType(uri);
				int slashIndex = mimeType.indexOf("/");
				String fileExtension = mimeType.substring(slashIndex + 1);
				fileName += fileExtension;
			}
			else {
				fileName = uri.getLastPathSegment();
			}
		}

		return fileName;
	}


}
