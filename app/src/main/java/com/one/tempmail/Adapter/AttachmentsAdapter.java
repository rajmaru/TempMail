package com.one.tempmail.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.one.tempmail.Models.AttachmentsData;
import com.one.tempmail.R;
import com.one.tempmail.UI.OpenMail;

import java.util.ArrayList;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.AttachmentsViewHolder>{

    ArrayList<AttachmentsData> attachmentsDataList;
    OpenMail activity;

    public AttachmentsAdapter(OpenMail activity, ArrayList<AttachmentsData> attachmentsDataList) {
        this.attachmentsDataList = attachmentsDataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AttachmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.attachments_layout, parent, false);
        return new AttachmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttachmentsViewHolder holder, int position) {
        AttachmentsData attachmentsData = attachmentsDataList.get(position);
        holder.title.setText(attachmentsData.getFilename());
        String size = attachmentsData.getSize() + " mb";
        holder.size.setText(size);
        holder.icon.setImageResource(R.drawable.ic_document);
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity,"Download successfully!!!",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return attachmentsDataList.size();
    }

    public static class AttachmentsViewHolder extends RecyclerView.ViewHolder {

        TextView title, size;
        ImageView icon, download;

        public AttachmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleAttachments);
            size = itemView.findViewById(R.id.sizeAttachments);
            icon = itemView.findViewById(R.id.iconAttachments);
            download = itemView.findViewById(R.id.downloadAttachments);
        }
    }
}
