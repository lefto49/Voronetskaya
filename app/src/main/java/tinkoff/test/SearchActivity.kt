package tinkoff.test

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar;
    lateinit var searchIcon: ImageView;
    lateinit var recyclerView: RecyclerView;
    lateinit var failureText: TextView;
    lateinit var failureButton: Button;
    lateinit var chosenButton: Button;
    lateinit var keywordEntered: EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity)

        searchIcon = findViewById(R.id.iv_begin_search_icon)
        recyclerView = findViewById(R.id.rv_popular_search)
        failureText = findViewById(R.id.tv_failure_search)
        failureButton = findViewById(R.id.b_failure_search)
        chosenButton = findViewById(R.id.b_chosen_search)
        keywordEntered = findViewById(R.id.et_enter_title)
        progressBar = findViewById(R.id.pb_main_screen_search)

        searchIcon.setOnClickListener {
            if (searchIcon.visibility != View.VISIBLE) {
                return@setOnClickListener
            }

            FilmsByKeywordRequest(this, keywordEntered.text.toString()).execute()
        }
    }

    class FilmsByKeywordRequest(private var activity: SearchActivity, var keyword: String) :
        AsyncTask<Unit, Unit, Array<Film>>() {
        override fun onPreExecute() {
            activity.recyclerView.visibility = View.INVISIBLE
            activity.progressBar.visibility = View.VISIBLE
            activity.failureText.visibility = View.INVISIBLE
            activity.failureButton.visibility = View.INVISIBLE
        }

        override fun doInBackground(vararg p0: Unit?): Array<Film>? {
            try {
                return Utils().getFilmsByKeyword(keyword)
            } catch (e: java.lang.Exception) {
                return null
            }
        }

        override fun onPostExecute(result: Array<Film>?) {
            activity.progressBar.visibility = View.INVISIBLE

            if (result == null) {
                activity.failureText.text = "Произошла ошибка при загрузке данных"
                activity.failureText.visibility = View.VISIBLE
                activity.failureButton.visibility = View.VISIBLE
                activity.recyclerView.visibility = View.INVISIBLE
            } else if (result.isEmpty()) {
                activity.failureText.text = "Ничего не найдено"
                activity.failureText.visibility = View.VISIBLE
                activity.failureButton.visibility = View.INVISIBLE
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