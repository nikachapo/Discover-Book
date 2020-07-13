package com.example.fincar.layout_manager;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fincar.adapters.CenterZoomLayoutManager;

public abstract class LayoutManagerFactory {
    public static RecyclerView.LayoutManager create(Context context, int orientation, boolean isCenterZoomed){
        if(isCenterZoomed){
            return new CenterZoomLayoutManager(context, orientation, false);
        }else{
            return new LinearLayoutManager(context, orientation, false);
        }
    }

    public static RecyclerView.LayoutManager create(Context context, boolean isCenterZoomed){
        if(isCenterZoomed){
            return new CenterZoomLayoutManager(context);
        }else{
            return new LinearLayoutManager(context);
        }
    }
}
