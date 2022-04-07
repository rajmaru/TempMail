package com.one.tempmail.Adapter;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttachDecoration extends RecyclerView.ItemDecoration {
    int last;

    public AttachDecoration(int last) {
        this.last = last;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if(parent.getChildLayoutPosition(view) == 0){
            outRect.left = 40;
        }else{
            outRect.left = 24;
        }

        if(parent.getChildLayoutPosition(view) == last-1){
            outRect.right = 40;
        }
        outRect.bottom = 40;
        outRect.top = 16;
    }
}
