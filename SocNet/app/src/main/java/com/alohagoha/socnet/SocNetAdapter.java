package com.alohagoha.socnet;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SocNetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Soc> data;
    private OnItemClickListener listener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public ImageView picture;
        public CheckBox like;

        public ViewHolder(View v) {
            super(v);
            this.description = v.findViewById(R.id.description);
            this.picture = v.findViewById(R.id.picture);
            this.like = v.findViewById(R.id.like);
            this.picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                        listener.onItemClick(view,getAdapterPosition());
                }
            });
            this.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.get(getAdapterPosition()).setLike(((CheckBox)view).isChecked());
                }
            });
        }
    }

    public SocNetAdapter(List<Soc> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        Log.d("SocnetAdapter", "OnCreateViewHolder");
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Soc item = data.get(position);
        ((ViewHolder)holder).description.setText(item.getDescription());
        ((ViewHolder)holder).picture.setImageResource(item.getPictures());
        ((ViewHolder)holder).like.setChecked(item.getLike());
        Log.d("SocnetAdapter","OnBindViewHolder");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}
