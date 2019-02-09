package m.ragaey.mohamed.todolist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import m.ragaey.mohamed.todolist.DataBase.TaskDao;
import m.ragaey.mohamed.todolist.DataBase.TaskDatabase;
import m.ragaey.mohamed.todolist.DataBase.TaskEntry;

public class TaskRepository {

    private TaskDao taskDao;
    private LiveData<List<TaskEntry>> allTask;

    public TaskRepository (Application application)
    {
        TaskDatabase database =TaskDatabase.getmInstance(application);
        taskDao = database.taskDao();
        allTask = taskDao.getAllTask();
    }

    public void insert (TaskEntry taskEntry)
    {
        new InsertTaskAsyncTask(taskDao).execute(taskEntry);
    }
    public void update (TaskEntry taskEntry)
    {
        new DeleteTaskAsyncTask(taskDao).execute(taskEntry);
    }
    public void delete (TaskEntry taskEntry)
    {
        new UpdateTaskAsyncTask(taskDao).execute(taskEntry);
    }
    public LiveData<List<TaskEntry>> getAllTask()
    {
        return allTask;
    }

    private static class InsertTaskAsyncTask extends AsyncTask<TaskEntry,Void,Void>
    {
        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao)
        {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntry... taskEntries)
        {
            taskDao.insertTask(taskEntries[0]);
            return null;
        }
    }
    private static class UpdateTaskAsyncTask extends AsyncTask<TaskEntry,Void,Void>
    {
        private TaskDao taskDao;

        private UpdateTaskAsyncTask(TaskDao taskDao)
        {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntry... taskEntries)
        {
            taskDao.uptadeTask(taskEntries[0]);
            return null;
        }
    }
    private static class DeleteTaskAsyncTask extends AsyncTask<TaskEntry,Void,Void>
    {
        private TaskDao taskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao)
        {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(TaskEntry... taskEntries)
        {
            taskDao.deleteTask(taskEntries[0]);
            return null;
        }
    }
}
