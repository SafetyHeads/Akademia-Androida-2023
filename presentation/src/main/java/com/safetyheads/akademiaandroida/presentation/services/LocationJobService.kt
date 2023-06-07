import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import com.safetyheads.akademiaandroida.presentation.services.LocationService

class LocationJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Background job started")

        val intent = Intent(applicationContext, LocationService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.startForegroundService(intent)
        } else {
            applicationContext.startService(intent)
        }

        jobFinished(params, false)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Background job stopped")
        return false
    }

    companion object {
        private const val TAG = "LocationJobService"
    }
}
