package tinkoff.test

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FilmsAdapter(var films: Array<Film>, var activity: Context) :
    RecyclerView.Adapter<FilmsAdapter.FilmsViewHolder>() {
    inner class FilmsViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filmTitle: TextView = itemView.findViewById(R.id.tv_film_title);
        val filmYear: TextView = itemView.findViewById(R.id.tv_film_year);
        val previewImage: ImageView = itemView.findViewById(R.id.iv_film_picture);
        lateinit var film: Film;

        init {
            itemView.setOnClickListener {
                if (itemView.visibility != View.VISIBLE) {
                    return@setOnClickListener
                }

                FilmByIdRequest(film.filmId).execute()
            }
        }
    }

    inner class FilmByIdRequest(private val id: Long) : AsyncTask<Unit, Unit, Film>() {
        override fun doInBackground(vararg p0: Unit?): Film? {
            return Utils().getFilmById(id)
        }

        override fun onPostExecute(result: Film?) {
            if (result == null) {
                return
            }

            val intent = Intent(activity, FilmInfoActivity::class.java).apply {
                putExtra("title", result.nameRu)
                putExtra("description", result.description)
                putExtra("posterUrl", result.posterUrl)
                putExtra("genre", result.genreString)
                putExtra("country", result.countryString)
            }
            activity.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.film_viewholder, parent, false)
        return FilmsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FilmsViewHolder, position: Int) {
        holder.filmTitle.text = films[position].nameRu
        holder.filmYear.text = films[position].year.toString()
        holder.film = films[position]

        Picasso.get().load(films[position].posterUrlPreview).into(holder.previewImage);
    }

    override fun getItemCount(): Int = films.size
}