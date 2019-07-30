package com.example.duong.repository.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.load.HttpException
import com.example.duong.repository.database.VideosDatabase
import com.example.duong.repository.repository.VideosRepository
import timber.log.Timber


class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        val database = VideosDatabase.getIntance(applicationContext)
        val repository = VideosRepository(database)

        try {
            repository.refreshVideos( )
            Timber.d("Work request for sync is run")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }

    companion object {
        const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
    }


}