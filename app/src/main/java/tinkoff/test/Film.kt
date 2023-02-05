package tinkoff.test

class Film(
    val filmId: Long,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val description: String,
    val year: Int,
    val genres: Array<Genre>,
    val countries: Array<Country>
) {
    lateinit var genreString: String;

    lateinit var countryString: String;
}