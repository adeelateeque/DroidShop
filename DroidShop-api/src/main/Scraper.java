package moviecrawler;

import java.io.IOException;
import java.util.ListIterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scraper {

	private String country;
	private Elements movies;
	private Elements theaters;

	MovieSortType sortType = MovieSortType.ALPHABETICAL;

	public Scraper() {
		country = "Singapore";
	}

	public Scraper(String country) {
		this.country = country;
	}

	public Scraper(String country, MovieSortType sortType) {
		this.country = country;
		this.sortType = sortType;
	}

	public void scrape() {
		Document moviesDoc = null;
		Document theatersDoc = null;
		try {
			moviesDoc = Jsoup.connect(
					"http://www.google.com/movies?near=" + country + "&sort="
							+ sortType).get();

			// sort is always 4 for theaters
			theatersDoc = Jsoup.connect(
					"http://www.google.com/movies?near=" + country + "&sort=4")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// select all movies
		movies = moviesDoc.select("div.movie_results div.movie");

		// select all theaters
		theaters = theatersDoc.select("div.movie_results div.theater");
	}

	public void getAllMoviesForToday() {
		ListIterator<Element> movieIterator = movies.listIterator();
		while (movieIterator.hasNext()) {
			Element movie = movieIterator.next();

			System.out
					.println("*******************************************************************************");

			// Movie title
			System.out.println("Movie: "
					+ movie.select("div.header div.desc h2[itemprop=name] a")
							.first().text());

			// IMBD link
			System.out.println("IMDB link "
					+ movie.select("div.header meta[itemprop=sameas]").first()
							.attr("content"));

			System.out
					.println("*******************************************************************************");
		}
	}
}

enum MovieSortType {
	// These numbers depend on the query parameters
	FEATURED(1), MOST_REVIEWS(2), ALPHABETICAL(3);

	private final int id;

	MovieSortType(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}

	@Override
	public String toString() {
		return Integer.toString(getValue());
	}
}
