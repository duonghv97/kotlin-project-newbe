package com.example.duong.trackmysleepquality.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

// sẽ tạo ra 1 object để giao tiếp với csdl, sử dụng singleton pattern
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase(){
    abstract fun sleepDatabaseDAO() : SleepDatabaseDAO // sẽ tham chiếu tới DAO để thực hiện

    // cho pheps khách hàng truy cập các phương thức để tạo hoặc get database
    // mà không cần tạo class. tương tự static trong java
    // create databse như singleton parttern
    companion object {
        // biến instance sẽ giữ tham chiếu đến cơ sở dữ liệu một khi nó được khởi tạo.
        // tránh được việc mở kết nối liên tục đến CSDL,
        @Volatile
        private var INSTANCE : SleepDatabase? = null

        fun getIntance(context :Context):SleepDatabase {
            // thể hiện chỉ một luồng thực thi tại một thời điểm
            synchronized(this) {
                // gán sang biến cục bộ , tận dụng lợi thế smartcast chỉ giành cho biến cục bộ
                // tự động cast sang
                var instance = INSTANCE
                if (instance == null) {
                    // khỏi tạo đối tượng truyền context, class database và tên của database
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration() // tránh mất dữ liệu
                        .build()
                    INSTANCE = instance
                }
                // còn nếu đã có đối tượng rồi thì trả ra
                return instance
            }
        }
    }
}