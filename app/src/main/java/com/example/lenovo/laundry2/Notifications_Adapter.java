package com.example.lenovo.laundry2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


//Adapter accepts the View & Model
//View is the layout that is to be inflated in recycler view rows.
//Model is passed to the adapter from the calleing activity.
//Here Model is the arraylist of ongoing & completed orders.
public class Notifications_Adapter extends RecyclerView.Adapter<Notifications_Adapter.ListHolder> {


    private List<OrderItems> listdata;
    private LayoutInflater inflater;
    private Notifications_Adapter.ItemClickCallback itemclickcallback;
    Context c;


    public Notifications_Adapter( List< OrderItems > listdata,Context c)
    {
        this.inflater=LayoutInflater.from(c);
        this.listdata=listdata;
        this.c=c;

    }

    public interface ItemClickCallback
    {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final Notifications_Adapter.ItemClickCallback itemclickcallback)
    {
        this.itemclickcallback=itemclickcallback;

    }

    @Override
    public Notifications_Adapter.ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notification_item, parent, false);
        return new Notifications_Adapter.ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        OrderItems item=listdata.get(position);

        String msg=null;

        if(!item.getWhenConfirmed().equals("#1") && item.getWhenCompleted().equals("#2") )
        {
            msg="Your Order placed on "+item.getWhenPlaced()+" billing to Rs."+item.getTotalPrice()+" was Confirmed on "+item.getWhenConfirmed();
        }
        else
        if(!item.getWhenConfirmed().equals("#1") && !item.getWhenCompleted().equals("#2") )
        {
            msg="Your Order placed on "+item.getWhenPlaced()+" billing to Rs."+item.getTotalPrice()+" was Delivered on "+item.getWhenCompleted();
        }


        holder.notification_tv.setText(msg);

    }

    //Returns the total count of OrderItems objets in the arraylist
    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView notification_tv;
        private View container;

        public ListHolder(View iView) {
            super(iView);
            notification_tv = (TextView) iView.findViewById(R.id.notification);
            container = iView.findViewById(R.id.container_notification);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.container_item_ongoing) {
                itemclickcallback.onItemClick(getAdapterPosition());
            }


        }
    }

}
