package com.example.dartsglobal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dartsglobal.R;
import com.example.dartsglobal.databinding.CustomRequestLayoutBinding;
import com.example.dartsglobal.models.Requests;

import java.util.ArrayList;
import java.util.Random;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private Context context;
    ArrayList<Requests> requestsArrayList;
    RequestAdapter.OnItemClickListener mListener;


    public RequestAdapter(Context context, ArrayList<Requests> homeArrayList) {

        this.context = context;
        this.requestsArrayList = homeArrayList;
    }

    public interface OnItemClickListener {
        void onPlayClick(Requests requests);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomRequestLayoutBinding binding = CustomRequestLayoutBinding
                .inflate(
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false);


        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Requests requests = requestsArrayList.get(position);
        holder.bind(requests);

    }


    @Override
    public int getItemCount() {
        return requestsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomRequestLayoutBinding binding;

        public ViewHolder(CustomRequestLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


            binding.playBtn.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Requests requests = requestsArrayList.get(position);
                    mListener.onPlayClick(requests);
                }
            });


        }

        public void bind(Requests requests) {
            binding.userNameTv.setText(requests.getPlayerOneName());
            binding.userNameWordTv.setText(requests.getPlayerOneName().substring(0, 1).toUpperCase());
        }
    }




}
