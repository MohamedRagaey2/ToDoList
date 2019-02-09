package m.ragaey.mohamed.todolist.DataBase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {TaskEntry.class},version = 1,exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    public static TaskDatabase mInstance;

    public static TaskDatabase getmInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = Room.databaseBuilder(context.getApplicationContext()
                    ,TaskDatabase.class,"TASK Database")
                    .addCallback(roomCallback)
                    .build();

        }
        return mInstance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(mInstance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(mInstance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask <Void, Void, Void>
    {
        private TaskDao taskDao;

        private PopulateDbAsync(TaskDatabase db)
        {
            taskDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            taskDao.insertTask(new TaskEntry("Task","Description","Date","Time"));
            return null;
        }
    }
}
