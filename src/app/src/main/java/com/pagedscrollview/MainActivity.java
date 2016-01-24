package com.pagedscrollview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int[] COLORS = new int[] {
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.CYAN,
            Color.MAGENTA,
            Color.YELLOW
    };
    private static final int NUM_PAGES = 5;
    private PagedScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScrollView = ((PagedScrollView)findViewById(R.id.scrollView));

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < NUM_PAGES; i++) {
            View pageView = layoutInflater.inflate(R.layout.page_view, null);
            ImageView image = (ImageView) pageView.findViewById(R.id.imageView);
            Resources res = getResources();
            String drawableName = "img0"+(i+1);
            int resID = res.getIdentifier(drawableName , "drawable", getPackageName());
            image.setImageResource(resID);
            mScrollView.addPage(pageView);
        }
    }

}
