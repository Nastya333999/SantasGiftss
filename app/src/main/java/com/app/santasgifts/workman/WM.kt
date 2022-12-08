package com.app.santasgifts.workman

import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.work.Data
import androidx.work.ListenableWorker.Result.success
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class WM(context: Context, workerParams: WorkerParameters)
    : Worker(context, workerParams) {

    override fun doWork(): Result {
        val isNotADB = load()
        Log.e("Loading", "Work Success?")
        val output: Data = workDataOf(KEY_RESULT to isNotADB)

        return success(output)
    }

    private fun load(): Boolean {
        fun isNotADB(): Boolean =
            Settings.Global.getString(
                applicationContext.contentResolver,
                Settings.Global.ADB_ENABLED
            ) != "1"
        return isNotADB()
    }


    companion object {
        const val KEY_RESULT = "KEY_RESULT"
    }

}