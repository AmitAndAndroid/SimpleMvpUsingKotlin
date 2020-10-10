package fusion.aim.simplemvpusingkotlin.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import fusion.aim.simplemvpusingkotlin.R
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.concurrent.thread

class SplashScreenActivity : AppCompatActivity() {

    private val PROGRESS_START=0
    private val PROGRESS_MAX=100
    private val JOB_TIME=4000

    private lateinit var job:CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startJob.setOnClickListener {
            if(!::job.isInitialized){
                initJob()
            }
            progress_bar.startOrCancelJob(job)
        }
    }

    fun resetjob(){
        if(job.isActive || job.isCompleted){
            job.cancel(CancellationException("Resetting job"))
        }
        initJob()
    }

    fun ProgressBar.startOrCancelJob(job: Job){
        if (this.progress>0){
            resetjob()
        }else{
            startJob.setText("Cancel Job #1")
            CoroutineScope(IO + job).launch {
                Log.d("MSG", "coroutine ${this} is activated with job ${job}.")

                for (i in PROGRESS_START..PROGRESS_MAX) {
                    delay((JOB_TIME / PROGRESS_MAX).toLong())
                    this@startOrCancelJob.progress = i
                }
                updateJobCompleteTextView("Job is complete!")
            }
        }
    }

    fun initJob(){
        startJob.setText("JOB 1 Started")
        updateJobCompleteTextView("")
        job= Job()

        job.invokeOnCompletion {
            it?.message.let {
                var msg=it
                if (msg.isNullOrBlank()){
                    msg="Unknown Error"
                }
                showToast(msg)
            }
        }
        progress_bar.max=PROGRESS_MAX
        progress_bar.progress=PROGRESS_START
    }

    private fun updateJobCompleteTextView(text: String){
        GlobalScope.launch (Main){
            statusText.setText(text)
        }
    }

    private fun showToast(text: String){
        GlobalScope.launch (Main){
            Toast.makeText(this@SplashScreenActivity, text, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
