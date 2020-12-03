import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.room.DatabaseDao
import com.example.myapplication.room.addition
import com.example.myapplication.room.depence

@Database(entities = [addition::class,depence::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    companion object{
        fun get(application: Application) : AppDatabase{
            return Room.databaseBuilder(application, AppDatabase::class.java, "Database").allowMainThreadQueries().build()
        }
    }
    abstract fun playerDao() : DatabaseDao
}