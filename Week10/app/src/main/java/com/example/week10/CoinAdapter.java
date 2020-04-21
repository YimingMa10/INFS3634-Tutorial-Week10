package com.example.week10;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.week10.Entities.Coin;

import java.text.NumberFormat;
import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {
    private MainActivity mParentActivity;
    private List<Coin> mCoins;
    private boolean mTwoPane;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Coin coin = (Coin) v.getTag();
            if(mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(DetailFragment.ARG_ITEM_ID, coin.getId());
                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment).commit();
            } else {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailFragment.ARG_ITEM_ID, coin.getId());
                context.startActivity(intent);
            }
        }
    };

    public CoinAdapter(MainActivity parent, List<Coin> coins, boolean twoPane) {
        mParentActivity = parent;
        mCoins = coins;
        mTwoPane = twoPane;
    }

    public static class CoinViewHolder extends RecyclerView.ViewHolder  {
        public TextView name, value, change;
        public ImageView logo;

        public CoinViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            value = v.findViewById(R.id.tvValue);
            change = v.findViewById(R.id.tvChange);
            logo = v.findViewById(R.id.ivArt);
        }
    }

    @Override
    public CoinViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_list_row, parent, false);
        return new CoinViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CoinViewHolder holder, int position) {
        Coin coin = mCoins.get(position);
        holder.name.setText(coin.getName());
        holder.value.setText(NumberFormat.getCurrencyInstance().format(Double.valueOf(coin.getPriceUsd())));
        holder.change.setText(coin.getPercentChange24h() + " %");
        if(Double.parseDouble(coin.getPercentChange24h()) < 0){
            holder.change.setTextColor(mParentActivity.getResources().getColor(R.color.colorRed));
        } else if(Double.parseDouble(coin.getPercentChange24h()) > 0){
            holder.change.setTextColor(mParentActivity.getResources().getColor(R.color.colorGreen));
        }
        holder.itemView.setTag(coin);
        holder.itemView.setOnClickListener(mOnClickListener);
        String imageUrl = "https://c1.coinlore.com/img/25x25/" + coin.getNameid() + ".png";
        Glide.with(mParentActivity).load(imageUrl).into(holder.logo);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCoins.size();
    }

    public void setCoins(List<Coin> coins) {
        mCoins.clear();
        mCoins.addAll(coins);
        notifyDataSetChanged();
    }

}
