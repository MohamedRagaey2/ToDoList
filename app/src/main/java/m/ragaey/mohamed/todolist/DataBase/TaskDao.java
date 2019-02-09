package m.ragaey.mohamed.todolist.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface TaskDao {

    @Query("SELECT * FROM Task ORDER BY date")
    LiveData<List<TaskEntry>> getAllTask();

    @Insert
    void insertTask(TaskEntry taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void uptadeTask(TaskEntry taskEntry);

    @Delete
    void deleteTask(TaskEntry taskEntry);
}
