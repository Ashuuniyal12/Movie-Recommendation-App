package com.example.movierecommender

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.movierecommender.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    private var MainUrl = "https://moive-recommender-smaller-api.herokuapp.com/?moviename="

    var similarMoviesName = listOf<String>()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val movieInputField =
            findViewById<EditText>(R.id.atv) // binding is only used for non nullable field
        val recommendButton =
            findViewById<Button>(R.id.button) // here field could have been null so binding cant be used.


        /*created a csv file of all avilable movie name and used those for showing while entering */
        val openResource: InputStream = resources.openRawResource(R.raw.movienames)
        val bufferReader: BufferedReader = openResource.bufferedReader()
        var line: String = bufferReader.readLine()
        val movieSuggestionName: ArrayList<String> = arrayListOf<String>()

        while (!line.isEmpty()) {
            movieSuggestionName.add(line)
            line = bufferReader.readLine()
        }

        binding.atv.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                movieSuggestionName
            )
        )

        recommendButton.setOnClickListener {
            if (movieInputField.text.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please Enter a Moive name", Toast.LENGTH_SHORT
                ).show()
                binding.progressbar.visibility = View.GONE
            } else {
                loadSimilarMovies(movieInputField.text.toString())
                binding.progressbar.visibility = View.VISIBLE
                binding.scroller.visibility = View.GONE
                binding.output.visibility = View.GONE
                binding.seekBar.progress = 2
                seekbarlistner()

            }
        }
    }


    private fun loadSimilarMovies(movieName: String) {

        val progressbar = findViewById<ProgressBar>(R.id.progressbar)
        val queue = Volley.newRequestQueue(this)
        val url: String = MainUrl + movieName
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val movieNameList = response.getJSONObject("similarMovie")
            Log.d("tag", movieNameList.toString())
            val moviePosterList = response.getJSONObject("poster")
            //val movieNameList: JsonArrayRequest = responseObj[0] as JsonArrayRequest
            //val moviePosterList: JsonArrayRequest = responseObj[1] as JsonArrayRequest
            val m1 = moviePosterList.getString("1")
            val m2 = moviePosterList.getString("2")
            val m3 = moviePosterList.getString("3")
            val m4 = moviePosterList.getString("4")
            val m5 = moviePosterList.getString("5")
            val m6 = moviePosterList.getString("6")
            val m7 = moviePosterList.getString("7")
            val m8 = moviePosterList.getString("8")
            val m9 = moviePosterList.getString("9")
            val m10 = moviePosterList.getString("10")

            binding.txt1.text = movieNameList.getString("1")
            binding.txt2.text = movieNameList.getString("2")
            binding.txt3.text = movieNameList.getString("3")
            binding.txt3.visibility = View.GONE
            binding.img3.visibility = View.GONE
            binding.txt4.text = movieNameList.getString("4")
            binding.txt4.visibility = View.GONE
            binding.img4.visibility = View.GONE
            binding.txt5.text = movieNameList.getString("5")
            binding.txt5.visibility = View.GONE
            binding.img5.visibility = View.GONE
            binding.txt6.text = movieNameList.getString("6")
            binding.txt6.visibility = View.GONE
            binding.img6.visibility = View.GONE
            binding.txt7.text = movieNameList.getString("7")
            binding.txt7.visibility = View.GONE
            binding.img7.visibility = View.GONE
            binding.txt8.text = movieNameList.getString("8")
            binding.txt8.visibility = View.GONE
            binding.img8.visibility = View.GONE
            binding.txt9.text = movieNameList.getString("9")
            binding.txt9.visibility = View.GONE
            binding.img9.visibility = View.GONE
            binding.txt10.text = movieNameList.getString("10")
            binding.txt10.visibility = View.GONE
            binding.img10.visibility = View.GONE


            Glide.with(this).load(m1).into(binding.img1)
            Glide.with(this).load(m2).into(binding.img2)
            Glide.with(this).load(m3).into(binding.img3)
            Glide.with(this).load(m4).into(binding.img4)
            Glide.with(this).load(m5).into(binding.img5)
            Glide.with(this).load(m6).into(binding.img6)
            Glide.with(this).load(m7).into(binding.img7)
            Glide.with(this).load(m8).into(binding.img8)
            Glide.with(this).load(m9).into(binding.img9)
            Glide.with(this).load(m10).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressbar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressbar.visibility = View.GONE
                    binding.scroller.visibility = View.VISIBLE
                    binding.output.visibility = View.VISIBLE
                    return false
                }
            }).into(binding.img10)

        }, { error ->
            // TODO: Handle error
        })

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest)
    }

    fun seekbarlistner() {

        var start = 0
        var end = 0
        var seekbar = binding.seekBar
        seekbar.max = 10
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                var number = progress
                binding.tvseeekbaar.text = "$number/10"
                when (number) {
                    3 -> {
                        binding.img3.visibility = View.VISIBLE
                        binding.txt3.visibility = View.VISIBLE
                    }
                    4 -> {
                        binding.img4.visibility = View.VISIBLE
                        binding.txt4.visibility = View.VISIBLE
                    }
                    5 -> {
                        binding.img5.visibility = View.VISIBLE
                        binding.txt5.visibility = View.VISIBLE
                    }
                    6 -> {
                        binding.img6.visibility = View.VISIBLE
                        binding.txt6.visibility = View.VISIBLE
                    }
                    7 -> {
                        binding.img7.visibility = View.VISIBLE
                        binding.txt7.visibility = View.VISIBLE
                    }
                    8 -> {
                        binding.img8.visibility = View.VISIBLE
                        binding.txt8.visibility = View.VISIBLE
                    }
                    9 -> {
                        binding.img9.visibility = View.VISIBLE
                        binding.txt9.visibility = View.VISIBLE
                    }
                    10 -> {
                        binding.img10.visibility = View.VISIBLE
                        binding.txt10.visibility = View.VISIBLE
                    }
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                start = seekbar.progress
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                end = seekbar.progress
            }

        })
    }


}