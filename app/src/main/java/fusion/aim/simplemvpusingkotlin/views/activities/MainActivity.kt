package fusion.aim.simplemvpusingkotlin.views.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fusion.aim.simplemvpusingkotlin.R
import fusion.aim.simplemvpusingkotlin.models.User
import fusion.aim.simplemvpusingkotlin.presenter.MainActivityPresenter
import fusion.aim.simplemvpusingkotlin.utils.TopSpacingItemDecoration
import fusion.aim.simplemvpusingkotlin.views.adapters.CustomAdapter

class MainActivity : AppCompatActivity(),MainActivityPresenter.MainActivityResponse {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initActivity()
    }

    private fun initActivity() {
        val mainActivityPresenter=MainActivityPresenter(this,this)
        mainActivityPresenter.getResponse()
    }

    @SuppressLint("WrongConstant")
    override fun success(users: List<User>) {
        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)
        val adapter = CustomAdapter(this,users as ArrayList<User>)
        recyclerView?.adapter = adapter
    }

    override fun fail(fail: String) {
        Toast.makeText(this,fail,Toast.LENGTH_SHORT).show()
    }

    override fun error(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}
