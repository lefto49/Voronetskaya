package tinkoff.test

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar;
    lateinit var recyclerView: RecyclerView;
    lateinit var failureText: TextView;
    lateinit var failureButton: Button;
    lateinit var searchIcon: ImageView;
    lateinit var chosenButton: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.pb_main_screen);
        recyclerView = findViewById(R.id.rv_popular);
        failureText = findViewById(R.id.tv_failure)
        failureButton = findViewById(R.id.b_failure)
        searchIcon = findViewById(R.id.iv_search_icon)
        chosenButton = findViewById(R.id.b_chosen)
        FilmsRequest(this).execute()

        failureButton.setOnClickListener {
            if (failureButton.visibility != View.VISIBLE) {
                return@setOnClickListener
            }

            FilmsRequest(this).execute()
        }

        searchIcon.setOnClickListener {
            if (searchIcon.visibility != View.VISIBLE) {
                return@setOnClickListener
            }

            val intent = Intent(this, SearchActivity::class.java)
            this.startActivity(intent)
        }

        chosenButton.setOnClickListener {

        }
    }

    class FilmsRequest(private var activity: MainActivity) : AsyncTask<Unit, Unit, Array<Film>>() {
        override fun onPreExecute() {
            activity.recyclerView.visibility = View.INVISIBLE
            activity.progressBar.visibility = View.VISIBLE
            activity.failureText.visibility = View.INVISIBLE
            activity.failureButton.visibility = View.INVISIBLE
        }

        override fun doInBackground(vararg p0: Unit?): Array<Film>? {
            try {
                return Utils().getTopFilms()
            } catch (e: java.lang.Exception) {
                return null
            }
        }

        override fun onPostExecute(result: Array<Film>?) {
            activity.progressBar.visibility = View.INVISIBLE

            if (result == null) {
                activity.failureText.visibility = View.VISIBLE
                activity.failureButton.visibility = View.VISIBLE
                activity.recyclerView.visibility = View.INVISIBLE
            } else {
                activity.failureText.visibility = View.INVISIBLE
                activity.failureButton.visibility = View.INVISIBLE
                activity.recyclerView.layoutManager = LinearLayoutManager(activity)
                activity.recyclerView.adapter = FilmsAdapter(result!!, activity)
                activity.recyclerView.visibility = View.VISIBLE
            }
        }
    }
}