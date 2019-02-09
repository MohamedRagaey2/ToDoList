package m.ragaey.mohamed.todolist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import m.ragaey.mohamed.todolist.DataBase.TaskEntry;
import m.ragaey.mohamed.todolist.TaskAdapter.TaskViewHolder;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder>
{
    private List<TaskEntry> taskEntries = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position)
    {
        TaskEntry entry = taskEntries.get(position);

        holder.task.setText(entry.getTask());
        holder.date.setText(String.valueOf(entry.getDate()));
        holder.time.setText(String.valueOf(entry.getTime()));

    }

    @Override
    public int getItemCount()
    {
        return taskEntries.size();
    }

    public void setTaskEntries (List<TaskEntry> entries)
    {
        this.taskEntries= entries;
        notifyDataSetChanged();
    }

    public TaskEntry getTaskAt (int position)
    {
        return taskEntries.get(position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder
    {
       private TextView task;
       private TextView date;
       private TextView time;

        public TaskViewHolder(View itemView) {
            super(itemView);

            task = itemView.findViewById(R.id.task_tv);
            date = itemView.findViewById(R.id.date_tv);
            time = itemView.findViewById(R.id.time_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int position =getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                    listener.OnItemClick(taskEntries.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener
    {
        void OnItemClick (TaskEntry taskEntry);
    }
    public void setOnItemClickListener (OnItemClickListener listener)
    {
        this.listener=listener;
    }

}
