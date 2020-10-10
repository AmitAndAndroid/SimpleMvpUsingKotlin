package fusion.aim.simplemvpusingkotlin.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import fusion.aim.simplemvpusingkotlin.R
import kotlinx.android.synthetic.main.activity_demo.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class DemoActivity : AppCompatActivity() {
    private val PROGRESS_START=0
    private val PROGRESS_MAX=100
    private val JOB_TIME=4000
    private lateinit var job:CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        startJob.setOnClickListener {
            if(!::job.isInitialized){
                initJob()
            }
            progress_bar.startOrCancelJob(job)
        }
    }

    fun initJob(){
        startJob.setText("Job Started")
        updateJobCompleteTextView("")
        job.invokeOnCompletion {
            it?.message.let {
                var  msg=it
                if(msg.isNullOrBlank()){
                    msg="Unknown Error"
                }
                showToast(msg)
            }
        }

        progress_bar.max=PROGRESS_MAX
        progress_bar.progress=PROGRESS_START
    }

    private fun updateJobCompleteTextView(text: String) {

        GlobalScope.launch(Main){
            statusText.setText(text)
        }
    }

    private fun showToast(text: String){
        GlobalScope.launch (Main){
            Toast.makeText(this@DemoActivity, text, Toast.LENGTH_SHORT).show()
        }
    }

}

private fun ProgressBar.startOrCancelJob(job: Job) {

}
