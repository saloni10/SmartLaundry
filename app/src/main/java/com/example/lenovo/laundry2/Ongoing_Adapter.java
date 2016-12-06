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

/**
 * Created by Lenovo on 11/28/2016.
 */

//Adapter accepts the View & Model
//View is the layout that is to be inflated in recycler view rows.
//Model is passed to the adapter from the calleing activity.
//Here Model is the arraylist of ongoing orders.
public class Ongoing_Adapter extends RecyclerView.Adapter<Ongoing_Adapter.ListHolder> {



    private List<OrderItems> listdata;
    private LayoutInflater inflater;
    private ItemClickCallback itemclickcallback;
    Context c;

    //Constructor
    public Ongoing_Adapter( List< OrderItems > listdata,Context c)
    {
        this.inflater=LayoutInflater.from(c);
        this.listdata=listdata;
        this.c=c;

    }

    public interface ItemClickCallback
    {
        void onItemClick(int p);
    }

    public void setItemClickCallback(final ItemClickCallback itemclickcallback)
    {
        this.itemclickcallback=itemclickcallback;

    }
    //Gets the view & data
    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ongoing_order, parent, false);
        return new ListHolder(view);
    }


    //Binds data to the view.
    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        OrderItems item=listdata.get(position);

        holder.topQty.setText(String.valueOf(item.getShirts()));
        holder.lowerQty.setText(String.valueOf(item.getLowers()));
        holder.bedsheetQty.setText(String.valueOf(item.getBedsheets()));
        holder.otherQty.setText(String.valueOf(item.getOthers()));
        holder.totalQ.setText(String.valueOf(item.getTotalQTY()));
        holder.totalP.setText(String.valueOf(item.getTotalPrice()));
        holder.whenPlaced.setText(item.getWhenPlaced());
        holder.pickupdate.setText(item.getPickupDate());
        holder.pickuptime.setText(item.getPickupTime());
        holder.typeoforder.setText(item.getTypeOfOrders());

    }

    //Returns the total count of OrderItems objets in the arraylist
    @Override
    public int getItemCount()
    {
        return listdata.size();
    }


    public class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView topQty;
        private TextView lowerQty;
        private TextView bedsheetQty;
        private TextView otherQty;
        private TextView totalQ;
        private TextView totalP;
        private TextView whenPlaced;
        private TextView pickupdate;
        private TextView pickuptime;
        private TextView typeoforder;

        private View container;

        public ListHolder(View iView) {
            super(iView);

            topQty = (TextView) iView.findViewById(R.id.topQty);
            lowerQty = (TextView) iView.findViewById(R.id.lowerQty);
            bedsheetQty = (TextView) iView.findViewById(R.id.bedsheetQty);
            otherQty = (TextView) iView.findViewById(R.id.otherQty);
            totalQ = (TextView) iView.findViewById(R.id.totalQ);
            totalP = (TextView) iView.findViewById(R.id.totalP);
            whenPlaced = (TextView) iView.findViewById(R.id.whenPlaced);
            pickupdate = (TextView) iView.findViewById(R.id.pickupdate);
            pickuptime = (TextView) iView.findViewById(R.id.pickuptime);
            typeoforder = (TextView) iView.findViewById(R.id.typeoforder);

            container = iView.findViewById(R.id.container_item_ongoing);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {



        }
    }

}
