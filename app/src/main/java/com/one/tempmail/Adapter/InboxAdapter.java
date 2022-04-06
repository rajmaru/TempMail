package com.one.tempmail.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.one.tempmail.Models.InboxData;
import com.one.tempmail.R;
import com.one.tempmail.UI.MainActivity;
import com.one.tempmail.UI.OpenMail;

import java.util.ArrayList;
import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder>{

    ArrayList<InboxData> inboxDataList;
    MainActivity activity;

    public InboxAdapter(MainActivity activity, ArrayList<InboxData> inboxDataList) {
        this.inboxDataList = inboxDataList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.inbox_layout,parent,false);
        return new InboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxViewHolder holder, int position) {
        InboxData inboxData = inboxDataList.get(position);
        holder.title.setText(inboxData.getFrom());
        holder.subject.setText(inboxData.getSubject());
        holder.date.setText(inboxData.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, OpenMail.class);
                intent.putExtra("id", inboxData.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inboxDataList.size();
    }

    public static class InboxViewHolder extends RecyclerView.ViewHolder {

        TextView title, subject, date;

        public InboxViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.senderEmailInbox);
            subject = itemView.findViewById(R.id.subjectInbox);
            date = itemView.findViewById(R.id.dateInbox);

        }
    }
}
