package com.nutrition.calculator.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Background sync service placeholder for periodic nutrition data updates. */
@AndroidEntryPoint
class NutritionSyncService : Service() {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var syncManager: NutritionSyncManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        scope.launch { syncManager.performSync() }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}

/** Very small placeholder syncing logic. Replace with real repositories / WorkManager. */
class NutritionSyncManager @Inject constructor() {
    suspend fun performSync() {
        // TODO: call repositories to pull remote changes and update local cache
    }
}
