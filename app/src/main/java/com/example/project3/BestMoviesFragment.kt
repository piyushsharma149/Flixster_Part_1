package com.example.project3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
//import com.codepath.bestsellerlistapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class BestMoviesFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.movie_info_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context,  1)
        updateAdapter(progressBar, recyclerView)
//        updateAdapter(recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        //val url = "https://api.themoviedb.org/3/movie/now_playing?api_key=$params&language=en-US&page=1"

        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/now_playing?",
                params,
                object: JsonHttpResponseHandler()

//        Uncomment me once you complete the above sections!
                {
                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        //TODO - Parse JSON into Models

//                        val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
                        val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray

                        val moviesRawJSON : String = resultsJSON.toString()
                        val gson = Gson()
                        val arraymovieType = object : TypeToken<List<BestMovies>>() {}.type
                        val models : List<BestMovies> = gson.fromJson(moviesRawJSON, arraymovieType)
                        recyclerView.adapter = BestMoviesRecyclerViewAdapter(models, this@BestMoviesFragment)
                        // Look for this in Logcat:
                        Log.d("BestMoviesFragment", "response successful")
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("BestMoviesFragment", errorResponse)
                        }
                    }
                }]


    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: BestMovies) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}
