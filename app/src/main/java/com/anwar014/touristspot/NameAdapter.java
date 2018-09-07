package com.anwar014.touristspot;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.anwar014.touristspot.Retrofit.tourist_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abc on 05-04-2018.
 */

public class NameAdapter extends RecyclerView.Adapter<NameAdapter.ViewHolder> {

    private Context mContext;
    private List<tourist_model> mList;
    private ArrayList<String> mlist;

    public NameAdapter(Context context, List<tourist_model> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.main_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String name = mList.get(position).getName();
        holder.mTextView.setText("" + name);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private Context mViewContext;

        public ViewHolder(View itemView) {
            super(itemView);
            mViewContext = itemView.getContext();
            mTextView = (TextView) itemView.findViewById(R.id.tv_row);
            mTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int i = getAdapterPosition();
            final String name = mList.get(i).getName();
            final String state = mList.get(i).getState();
            final String url = mList.get(i).getUrl();
            final String desc = mList.get(i).getDesc();
            mlist = new ArrayList<>();
            mlist.add(0, name);
            mlist.add(1, state);
            mlist.add(2, url);
            mlist.add(3, desc);
            mList.get(i);
            Intent intent = new Intent(mContext, DetailViewActivity.class);
            intent.putStringArrayListExtra("data", mlist);
            mViewContext.startActivity(intent);
        }
    }
}
