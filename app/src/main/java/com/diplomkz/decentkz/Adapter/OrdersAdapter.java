package com.diplomkz.decentkz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.diplomkz.decentkz.Model.Order;
import com.android.decentkz.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> orderArrayList;
    private Order order;

    public OrdersAdapter(Context context, ArrayList<Order> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
        this.order = order;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Order order = orderArrayList.get(position);

        if(order.getStatus().equals("Processed")){
            holder.status.setText("В пути");
            holder.status.setTextColor(context.getResources().getColor(R.color.green));
        }
        else {
            holder.status.setText("Отправка...");
        }

        holder.address.setText(order.getAddress());
        holder.product_name.setText(order.getCartProductList().get(0).getName());
        Picasso.get().load(order.getCartProductList().get(0).getPhotoUrl()).placeholder(R.drawable.icon).into(holder.image);
        holder.date.setText(order.getDateOfOrder());
        holder.totalPrice.setText("₸ "+order.getTotalPrice());
        holder.product_size.setText("Размер:  " + order.getCartProductList().get(0).getSizeType());
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView status,date,totalPrice, address, product_name, product_size;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_size = itemView.findViewById(R.id.order_product_size);
            product_name = itemView.findViewById(R.id.order_product_name);
            address = itemView.findViewById(R.id.order_address);
            image = itemView.findViewById(R.id.p_img);
            status = itemView.findViewById(R.id.order_status);
            date = itemView.findViewById(R.id.date_of_order);
            totalPrice = itemView.findViewById(R.id.order_total_price);
        }
    }


}
