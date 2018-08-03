package com.hikki.sergey_natalenko.testapp.ui.components;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hikki.sergey_natalenko.testapp.R;

public class AttachedCardView extends CardView implements View.OnClickListener{

    private static final int DOCUMENT_TYPE_IMAGE = 0;
    private static final int DOCUMENT_TYPE_TEXT_FILE = 1;

    private Uri mUri;
    private String mText;

    private TextView mDocName;
    private ImageView mDocImage;

    public AttachedCardView(Context context, int type, String link, String text) {
        super(context);
        mDocName = new TextView(context);
        mDocName.setMaxLines(1);
        mDocName.setTextColor(getResources().getColor(R.color.colorIndigo));
        mDocName.setPadding(0,getResources().getDimensionPixelOffset(R.dimen.padding_icons_attached_files),getResources().getDimensionPixelOffset(R.dimen.padding_icons_attached_files),0);
        mDocImage = new ImageView(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(mDocImage);
        linearLayout.addView(mDocName);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        createCard(type,link,text);

        this.setElevation(0);
        this.addView(linearLayout);
    }

    private void createCard(int type, String link, String text) {
        mDocName.setText(text);

        mDocImage.setPadding(getResources().getDimensionPixelOffset(R.dimen.padding_icons_attached_files),getResources().getDimensionPixelOffset(R.dimen.padding_icons_attached_files),getResources().getDimensionPixelOffset(R.dimen.padding_icons_attached_files),0);

        switch (type){
            case DOCUMENT_TYPE_IMAGE:
                mDocImage.setImageResource(R.drawable.doc_image);
                break;
            default:
                mDocImage.setImageResource(R.drawable.doc_file);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
    }

    public Uri getUri() {
        return mUri;
    }

    public String getText() {
        return mText;
    }
}
