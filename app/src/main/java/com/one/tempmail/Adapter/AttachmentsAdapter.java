package com.one.tempmail.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.one.tempmail.Constants.Constants;
import com.one.tempmail.Models.AttachmentsData;
import com.one.tempmail.R;
import com.one.tempmail.UI.OpenMail;
import com.one.tempmail.ViewModel.ApiViewModel;

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
        int attachSize = attachmentsData.getSize();
        String StringAttachSize = attachSize+"";
        if(StringAttachSize.length() >= 4 && StringAttachSize.length() <= 6){
            attachSize = attachSize / 1024;
            StringAttachSize = attachSize + " kb";
        }else if(StringAttachSize.length() >= 7 && StringAttachSize.length() <= 9){
            attachSize = attachSize / 1048576;
            StringAttachSize = attachSize + " mb";
        } else if(StringAttachSize.length() >= 10 && StringAttachSize.length() <= 12){
            attachSize = attachSize / 1073741824;
            StringAttachSize = attachSize + " gb";
        }else{
            StringAttachSize = attachSize + " b";
        }
        holder.size.setText(StringAttachSize);
        if(attachmentsData.getContentType().contains("pdf")){
            holder.icon.setImageResource(R.drawable.ic_pdf);
            String attachName = attachmentsData.getFilename();
            attachName = attachName.replace(".PDF", ".pdf");
            holder.title.setText(attachName);
        }else if(attachmentsData.getContentType().contains("image")){
            holder.icon.setImageResource(R.drawable.ic_image);
            holder.title.setText(attachmentsData.getFilename());
        }else if(attachmentsData.getContentType().contains("audio")){
            holder.icon.setImageResource(R.drawable.ic_audio);
            holder.title.setText(attachmentsData.getFilename());
        }else if(attachmentsData.getContentType().contains("video")){
            holder.icon.setImageResource(R.drawable.ic_video);
            holder.title.setText(attachmentsData.getFilename());
        }else if(attachmentsData.getContentType().contains("text")){
            holder.icon.setImageResource(R.drawable.ic_text);
            holder.title.setText(attachmentsData.getFilename());
        }else{
            holder.icon.setImageResource(R.drawable.ic_document);
            holder.title.setText(attachmentsData.getFilename());
        }


        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.downloadAttachements(attachmentsData.getFilename());
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
