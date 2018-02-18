package com.hanan.and.udacity.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.hanan.and.udacity.popularmovies.models.Movie;
import com.hanan.and.udacity.popularmovies.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mMoviesRecyclerView;
    List<Movie> mMoviesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMoviesRecyclerView = findViewById(R.id.movies_rv);
        //set the RecyclerView Layout
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mMoviesRecyclerView.setLayoutManager(mGridLayoutManager);

        populateMoviesList();

        //set the RecyclerView Adapter
        MoviesAdapter mMoviesAdapter = new MoviesAdapter(MainActivity.this, mMoviesList);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);
    }

    public void populateMoviesList(){
        String json = getResources().getString(R.string.movie_example_data);
        mMoviesList = JsonUtils.parseMoviesListJson(json);

//        mMoviesList = new ArrayList<>();
//
//        Movie movie1 = new Movie();
//        movie1.setOriginalTitle("Dilwale Dulhania Le Jayenge");
//        movie1.setPosterImage("/nl79FQ8xWZkhL3rDr1v2RFFR6J0.jpg");
//        movie1.setPlotSynopsis("Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.");
//        movie1.setReleaseDate("1995-10-20");
//        movie1.setUserRatings("9.1");
//
//        Movie movie2 = new Movie();
//        movie2.setOriginalTitle("Life Is Beautiful");
//        movie2.setPosterImage("/f7DImXDebOs148U4uPjI61iDvaK.jpg");
//        movie2.setPlotSynopsis("A touching story of an Italian book seller of Jewish ancestry who lives in his own little fairy tale. His creative and happy life would come to an abrupt halt when his entire family is deported to a concentration camp during World War II. While locked up he tries to convince his son that the whole thing is just a game.");
//        movie2.setReleaseDate("1997-12-20");
//        movie2.setUserRatings("8.3");
//
//        Movie movie3 = new Movie();
//        movie3.setOriginalTitle("Pulp Fiction");
//        movie3.setPosterImage("/dM2w364MScsjFf8pfMbaWUcWrR.jpg");
//        movie3.setPlotSynopsis("A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.");
//        movie3.setReleaseDate("1994-09-10");
//        movie3.setUserRatings("8.3");
//
//        Movie movie4 = new Movie();
//        movie4.setOriginalTitle("One Flew Over the Cuckoo's Nest");
//        movie4.setPosterImage("/2Sns5oMb356JNdBHgBETjIpRYy9.jpg");
//        movie4.setPlotSynopsis("While serving time for insanity at a state mental hospital, implacable rabble-rouser, Randle Patrick McMurphy inspires his fellow patients to rebel against the authoritarian rule of head nurse, Mildred Ratched.");
//        movie4.setReleaseDate("1975-11-18");
//        movie4.setUserRatings("8.3");
//
//        mMoviesList.add(movie1);
//        mMoviesList.add(movie2);
//        mMoviesList.add(movie3);
//        mMoviesList.add(movie4);
//        mMoviesList.add(movie2);
//        mMoviesList.add(movie2);
//        mMoviesList.add(movie3);
//        mMoviesList.add(movie4);
//        mMoviesList.add(movie2);
//        mMoviesList.add(movie2);
//        mMoviesList.add(movie3);
//        mMoviesList.add(movie4);
//        mMoviesList.add(movie2);
//        mMoviesList.add(movie2);
//        mMoviesList.add(movie3);
//        mMoviesList.add(movie4);
    }
}
