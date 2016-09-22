package com.xxmassdeveloper.mpchartexample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by hmoussa on 25/05/2015.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    WebView webv = null;
    private Activity context;
    private Map<String, List<String>> laptopCollections;
    private List<String> laptops;

    public ExpandableListAdapter(Activity context, List<String> laptops,
                                 Map<String, List<String>> laptopCollections) {
        this.context = context;
        this.laptopCollections = laptopCollections;
        this.laptops = laptops;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();
        if(isLastChild){

                convertView = inflater.inflate(R.layout.web_child_item, null);

            webv = (WebView) convertView.findViewById(R.id.webViewChild);

            webv.setHttpAuthUsernamePassword("http://mol-alm-tfs1:8080/tfs", "CertificateAuthentication", Global_Values.domain+"\\"+ Global_Values.userName, Global_Values.password);
            webv.setWebViewClient(new WebViewClient() {

                boolean authComplete = false;
                //Intent resultIntent = new Intent();


                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }



                @Override
                public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                    handler.proceed(Global_Values.domain+"\\"+ Global_Values.userName,Global_Values.password);
                    super.onReceivedHttpAuthRequest(view, handler, host, realm);
                }
            });
            //webv.setWebChromeClient(new MyWebViewClient ());
            //textView.setText(child.get(childPosition));
            webv.getSettings().setJavaScriptEnabled(true);
            webv.clearCache(true);
            webv.getSettings().setLoadWithOverviewMode(true);
            webv.getSettings().setUseWideViewPort(true);
            webv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webv.setScrollbarFadingEnabled(true);
            webv.loadDataWithBaseURL("",laptop, "text/html", "UTF-8", "");
            webv.reload();
            //webv.setHttpAuthUsernamePassword();

        }else{

                convertView = inflater.inflate(R.layout.child_item, null);


            TextView item = (TextView) convertView.findViewById(R.id.laptop);

            //ImageView delete = (ImageView) convertView.findViewById(R.id.delete);


            item.setText(laptop);
        }
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return laptopCollections.get(laptops.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return laptops.get(groupPosition);
    }

    public int getGroupCount() {
        return laptops.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.laptop);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}