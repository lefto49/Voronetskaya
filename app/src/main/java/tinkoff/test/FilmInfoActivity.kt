package tinkoff.test

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso


class FilmInfoActivity : AppCompatActivity() {
    lateinit var filmPoster: ImageView;
    lateinit var textTitle: TextView;
    lateinit var textDescription: TextView;
    lateinit var textGenre: TextView;
    lateinit var textCountry: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.film_info)

        filmPoster = findViewById(R.id.iv_big_poster)
        textTitle = findViewById(R.id.tv_big_title)
        textDescription = findViewById(R.id.tv_description)
        textGenre = findViewById(R.id.tv_genre)
        textCountry = findViewById(R.id.tv_country)

        val intent = intent
        textTitle.text = intent.getStringExtra("title")
        textDescription.text = intent.getStringExtra("description")

        if (intent.getStringExtra("genre") != null) {
            textGenre.append(intent.getStringExtra("genre"))
        }

        if (intent.getStringExtra("country") != null) {
            textCountry.append(intent.getStringExtra("country"))
        }
        Picasso.get().load(intent.getStringExtra("posterUrl")).into(filmPoster);
    }
}