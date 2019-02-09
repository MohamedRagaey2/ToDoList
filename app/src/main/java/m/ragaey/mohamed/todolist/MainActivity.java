package m.ragaey.mohamed.todolist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import m.ragaey.mohamed.todolist.DataBase.TaskEntry;

public class MainActivity extends AppCompatActivity {

    public static final int Add_Task_Reguest = 1;
    public static final int Add_Task_Reguest2 = 2;


    private TaskViewModel taskViewModel;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DividerItemDecoration dividerItemDecoration;
    TaskAdapter adapter;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter = new TaskAdapter();
        recyclerView.setAdapter(adapter);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivityForResult(intent, Add_Task_Reguest);
            }
        });

        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTaskes().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(@Nullable List<TaskEntry> taskEntries) {
                adapter.setTaskEntries(taskEntries);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,4) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                taskViewModel.delete(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(TaskEntry taskEntry) {
                Intent intent = new Intent(MainActivity.this,AddTask.class);
                intent.putExtra(AddTask.Id,taskEntry.getId());
                intent.putExtra(AddTask.taskk,taskEntry.getTask());
                intent.putExtra(AddTask.desc,taskEntry.getDescription());
                intent.putExtra(AddTask.timee,taskEntry.getTime());
                intent.putExtra(AddTask.datee,taskEntry.getDate());

                startActivityForResult(intent,Add_Task_Reguest);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Add_Task_Reguest && resultCode == RESULT_OK)
        {
            String task = data.getStringExtra(AddTask.taskk);
            String desc = data.getStringExtra(AddTask.desc);
            String time = data.getStringExtra(AddTask.timee);
            String date = data.getStringExtra(AddTask.datee);

            TaskEntry taskEntry = new TaskEntry(task,desc,time,date);
            taskViewModel.insert(taskEntry);

            Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show();
        }else if (requestCode == Add_Task_Reguest2 && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddTask.Id,-1);
            if (id == -1)
            {
                Toast.makeText(this, "Task Can't be Updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String task = data.getStringExtra(AddTask.taskk);
            String desc = data.getStringExtra(AddTask.desc);
            String time = data.getStringExtra(AddTask.timee);
            String date = data.getStringExtra(AddTask.datee);

            TaskEntry taskEntry = new TaskEntry(task,desc,time,date);
            taskEntry.setId(id);
            taskViewModel.update(taskEntry);

            Toast.makeText(this, "Task Updated", Toast.LENGTH_SHORT).show();

        } else
            {
                Toast.makeText(this, "Task Not Saved", Toast.LENGTH_SHORT).show();
            }
    }
}
