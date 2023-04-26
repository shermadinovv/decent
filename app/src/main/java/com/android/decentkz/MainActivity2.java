package com.android.decentkz;

import static com.android.decentkz.Model.Utils.TAG_medicine_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.decentkz.Adapter.CartCustomAdapter;
import com.android.decentkz.Model.Order;
import com.android.decentkz.Model.Product;
import com.android.decentkz.Model.Utils;
import com.android.decentkz.R;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity2 extends AppCompatActivity {

    private ImageView cartBackArrow, btn_magaz;
    CardView male, female, img_ban;

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
    }

    private void initAll() {

        cartBackArrow=findViewById(R.id.cart_back_arrow);
        male=findViewById(R.id.img_card_male);
        female=findViewById(R.id.img_card_female);
        female=findViewById(R.id.img_card_female);
        btn_magaz=findViewById(R.id.btn_magaz);
        img_ban=findViewById(R.id.img_card_ban);

    }
}