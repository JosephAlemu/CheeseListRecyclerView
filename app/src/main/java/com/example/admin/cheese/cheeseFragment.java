package com.example.admin.cheese;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class cheeseFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
       View view = inflater.inflate(R.layout.fragment_cheese, container, false);

        return view;
    }


    public static class CheeseListAdapter extends RecyclerView.Adapter<CheeseListAdapter.CheeseViewHolder>
    {
        private ArrayList<Cheese> mCheeses= new ArrayList<>();
        private Context mContext;

        public CheeseListAdapter(Context context, ArrayList<Cheese> cheese) {
            mContext = context;
            mCheeses = cheese;
        }


        @Override
        public CheeseListAdapter.CheeseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
            CheeseViewHolder viewHolder = new CheeseViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CheeseListAdapter.CheeseViewHolder holder, int position) {
            holder.bindData(mCheeses.get(position));
        }

        @Override
        public int getItemCount() {
            return mCheeses.size();
        }

        public class CheeseViewHolder extends RecyclerView.ViewHolder {

            TextView tvCheese ;

            private Context mContext;

            public CheeseViewHolder(View itemView) {
                super(itemView);

                tvCheese =  itemView.findViewById(R.id.tvCheese);
            }

            public void bindData(Cheese cheese) {
                tvCheese.setText(cheese.getName());

            }
        }
    }





}


