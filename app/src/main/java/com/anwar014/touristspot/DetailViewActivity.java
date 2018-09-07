package com.anwar014.touristspot;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailViewActivity extends AppCompatActivity {

    private CardView mCardView;
    private ImageView mImageView;
    private TextView mTextName;
    private TextView mTextState;
    private TextView mTextDesc;

    private Palette.Swatch lightMutedSwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        mCardView = (CardView) findViewById(R.id.root_card_view);
        mImageView = (ImageView) findViewById(R.id.img_detail_view);
        mTextName = (TextView) findViewById(R.id.tv_detail_name);
        mTextState = (TextView) findViewById(R.id.tv_detail_state);
        mTextDesc = (TextView) findViewById(R.id.tv_detail_desc);

        mCardView.setPreventCornerOverlap(false);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mImageView.setClipToOutline(true);
            mCardView.setElevation(10);
        }
        mImageView.setBackgroundResource(R.drawable.img_bg);

        ArrayList<String> list = getIntent().getStringArrayListExtra("data");

        String name = list.get(0);
        String state = list.get(1);
        String url = list.get(2);
        String desc = list.get(3);

        if (!url.isEmpty()) {
            Picasso.get().load(url).placeholder(R.drawable.default_loading).into(mImageView, new Callback() {
                @Override
                public void onSuccess() {
                    mImageView.setBackgroundResource(R.drawable.img_bg);
                    Bitmap bitmap = ((BitmapDrawable) mImageView.getDrawable()).getBitmap();
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            lightMutedSwatch = palette.getLightMutedSwatch();
                            mCardView.setCardBackgroundColor(lightMutedSwatch.getRgb());
                        }
                    });
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
        mTextName.setText(name);
        mTextState.setText("in " + state);
        mTextDesc.setText(desc);

    }
}
