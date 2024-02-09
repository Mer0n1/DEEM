package com.example.deem.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deem.R;
import com.example.restful.models.curriculum.Class;
import com.example.restful.utils.DateTranslator;

import java.util.List;

public class CurriculumRecycleAdapter extends RecyclerView.Adapter<CurriculumRecycleAdapter.ItemEvent> {

    private List<Class> classes;

    public CurriculumRecycleAdapter(List<Class> classes) {
        this.classes = classes;
    }

    @NonNull
    @Override
    public ItemEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);

        return new CurriculumRecycleAdapter.ItemEvent(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemEvent holder, int position) {
        holder.setData(classes.get(position));
    }

    @Override
    public int getItemCount() {
        return classes.size();
    }

    class ItemEvent extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView place;
        private TextView time;
        private TextView type;

        public ItemEvent(@NonNull View itemView) {
            super(itemView);

            name  = itemView.findViewById(R.id.class_name_info);
            place = itemView.findViewById(R.id.class_place_info);
            time  = itemView.findViewById(R.id.class_time_info);
            type  = itemView.findViewById(R.id.class_type_info);

        }

        public void setData(Class cl) {
            name.setText(cl.getName());
            place.setText(cl.getPlace());
            time.setText(DateTranslator.getInstance().TimeToString(cl.getDate()));
            type.setText(cl.getType());
        }


    }
}
