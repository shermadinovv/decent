package com.diplomkz.decentkz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.diplomkz.decentkz.Adapter.ProductsAdapter;
import com.diplomkz.decentkz.Model.Order;
import com.diplomkz.decentkz.Model.Product;
import com.diplomkz.decentkz.Model.Utils;
import com.android.decentkz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.paperdb.Paper;

public class ProductDetailsActivity extends AppCompatActivity {

    private CardView addToCartBtn;
    private ImageView productImg;
    private TextView plusBTn,minusBtn,quantityTV;
    private TextView productName,productDescription,price,size,color,category;
    Product product;



    private ProductsAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Product> productArrayList;

    DatabaseReference myRootRef;
    private ProgressBar progressBar;
    private TextView noJokeText;
    private EditText nameInput;


    int quantity=1;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initAll();
        ClickListeners();

        product = (Product) getIntent().getSerializableExtra("product");


        if(product.getPhotoUrl()!=null){
            if(!product.getPhotoUrl().equals("")){
                Picasso.get().load(product.getPhotoUrl()).placeholder(R.drawable.icon).into(productImg);


            }
        }


        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        price.setText("₸ "+product.getPrice());
        size.setText(product.getSizeType());
        color.setText(product.getColor());
        category.setText(product.getCategory());

        productArrayList =new ArrayList<Product>();
        recyclerView =findViewById(R.id.product_list2);
        progressBar = findViewById(R.id.spin_progress_bar2);
        noJokeText = findViewById(R.id.no_product2);

        myRootRef = FirebaseDatabase.getInstance().getReference();
        Utils.statusBarColor(ProductDetailsActivity.this);

        mAdapter = new ProductsAdapter(productArrayList, ProductDetailsActivity.this,false);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        getDataFromFirebase();



    }

    private void ClickListeners() {
        plusBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity+=1;
                quantityTV.setText(String.valueOf(quantity));
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity>1){
                    quantity-=1;
                    quantityTV.setText(String.valueOf(quantity));
                }
            }
        });

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInCart=false;
                for(int i=0;i<order.getCartProductList().size();i++){
                    if(product.getProductId().equals(order.getCartProductList().get(i).getProductId())){
                        isInCart=true;
                        break;
                    }
                }
                if(!isInCart){
                    product.setQuantityInCart(quantity);
                    order.addProduct(product);
                    Log.d("testorder",order.getTotalPrice()+ "");
                    Paper.book().delete("order");
                    Paper.book().write("order",order);
                    Toast.makeText(ProductDetailsActivity.this,"Added to cart",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(ProductDetailsActivity.this,"Already in cart",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void searchFunc() {
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() == 0) {
                    if(productArrayList.size()!=0){
                        recyclerView.setVisibility(View.VISIBLE);
                        noJokeText.setVisibility(View.GONE);
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        noJokeText.setVisibility(View.VISIBLE);
                    }

                    mAdapter = new ProductsAdapter(productArrayList,ProductDetailsActivity.this,true);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                } else {
                    ArrayList<Product> clone = new ArrayList<>();
                    for (Product element : productArrayList) {
                        if (element.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                            clone.add(element);
                        }
                    }
                    if(clone.size()!=0){
                        recyclerView.setVisibility(View.VISIBLE);
                        noJokeText.setVisibility(View.GONE);
                    }
                    else{
                        recyclerView.setVisibility(View.GONE);
                        noJokeText.setVisibility(View.VISIBLE);
                    }

                    mAdapter = new ProductsAdapter(clone,ProductDetailsActivity.this,true);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void getDataFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        final int[] counter = {0};
        myRootRef.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Product product2 = new Product();
                        product2 = child.getValue(Product.class);
                        productArrayList.add(product2);
                        counter[0]++;
                        if (counter[0] == dataSnapshot.getChildrenCount()) {
                            setData();
                            progressBar.setVisibility(View.GONE);
                        }
                        Log.d("ShowEventInfo:", product2.toString());
                    }
                }
                else{
                    noJokeText.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void setData() {
        if(productArrayList.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            noJokeText.setVisibility(View.GONE);
        }
        else{
            recyclerView.setVisibility(View.GONE);
            noJokeText.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void initAll() {
        addToCartBtn=findViewById(R.id.add_to_cart_btn);
        productImg=findViewById(R.id.product_img);
        plusBTn=findViewById(R.id.plus_btn);
        minusBtn=findViewById(R.id.minus_btn);
        quantityTV=findViewById(R.id.quantity_tv);
        productName=findViewById(R.id.product_name);
        price=findViewById(R.id.product_price);
        size=findViewById(R.id.tvsize);
        color=findViewById(R.id.tvcolor);
        category=findViewById(R.id.tvcategory);
        productDescription=findViewById(R.id.product_description);

        product=new Product();

        order=new Order();

        if(Paper.book().read("order")!=null){
            order=Paper.book().read("order");
        }

        Utils.statusBarColor(ProductDetailsActivity.this);
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Paper.book().read("order")!=null){
            order=Paper.book().read("order");
        }
    }
}