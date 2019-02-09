package m.ragaey.mohamed.todolist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import m.ragaey.mohamed.todolist.DataBase.TaskEntry;

public class TaskViewModel extends AndroidViewModel
{
    private TaskRepository repository;
    private LiveData<List<TaskEntry>> allTaskes;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        repository = new TaskRepository(application);
        allTaskes = repository.getAllTask();
    }

    public void insert (TaskEntry taskEntry)
    {
        repository.insert(taskEntry);
    }
    public void update (TaskEntry taskEntry)
    {
        repository.update(taskEntry);
    }
    public void delete (TaskEntry taskEntry)
    {
        repository.delete(taskEntry);
    }
     public LiveData<List<TaskEntry>> getAllTaskes()
     {
         return allTaskes;
     }
}
