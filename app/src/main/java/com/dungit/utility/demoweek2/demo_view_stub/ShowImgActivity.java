package com.dungit.utility.demoweek2.demo_view_stub;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.dungit.utility.demoweek2.R;

/**
 * Created by nahuy on 5/12/18.
 */

public class ShowImgActivity extends AppCompatActivity {
    private Button btnShowImg;
    private Button btnHideImg;
    private ViewStub vsImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_img);

        btnHideImg = findViewById(R.id.btn_hide_img);
        btnShowImg = findViewById(R.id.btn_show_img);
        vsImg = findViewById(R.id.vs_img);

        btnShowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vsImg == null){
                    vsImg.inflate();
                }
                vsImg.setVisibility(View.VISIBLE);
            }
        });

        btnHideImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vsImg != null){
                    vsImg.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
