package com.diplomkz.decentkz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.decentkz.R;

public class MainActivity2 extends AppCompatActivity {

    private ImageView cartBackArrow, btn_magaz;
    CardView male, female, img_ban, img_ban2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initAll();
        clickListener();
    }

    private void clickListener() {

        cartBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_magaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        img_ban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        img_ban2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                intent = new Intent(getApplicationContext(), SearchFiltersActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initAll() {

        cartBackArrow=findViewById(R.id.cart_back_arrow);
        male=findViewById(R.id.img_card_male);
        female=findViewById(R.id.img_card_female);
        female=findViewById(R.id.img_card_female);
        btn_magaz=findViewById(R.id.btn_magaz);
        img_ban=findViewById(R.id.img_card_ban);
        img_ban2=findViewById(R.id.img_card_ban2);

    }
}