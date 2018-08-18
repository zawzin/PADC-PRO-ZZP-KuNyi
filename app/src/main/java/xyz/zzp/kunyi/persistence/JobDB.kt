package xyz.zzp.kunyi.persistence

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

abstract class JobDB : RoomDatabase() {

    companion object {
        private val DB_NAME = "Job.DB"
        private var INSTANCE: JobDB? = null

        fun getDatabase(context: Context): JobDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, JobDB::class.java, DB_NAME)
                        .allowMainThreadQueries() //Remove this after testing. Access to DB should always be from background thread.
                        .build()
            }
            val i = INSTANCE
            return i!!
        }
    }
}