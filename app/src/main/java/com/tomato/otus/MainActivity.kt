package com.tomato.otus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var filmId = 0
    private val TAG = "TomatoKino"

    private val title1: TextView by lazy { findViewById(R.id.text_view_1) }
    private val title2: TextView by lazy { findViewById(R.id.text_view_2) }
    private val title3: TextView by lazy { findViewById(R.id.text_view_3) }
    private var startDetailsActivity: ActivityResultLauncher<Intent>? = null

    private fun onClick(v: View?) {
        val s = (v as Button).tag.toString()
        filmId = s.toInt()
        selectFilm()
        startNewActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startDetailsActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { res ->
            val data = res.data
            if (res.resultCode == RESULT_OK && data != null ) {

                val comment = data.getStringExtra(COMMENT)
                val isLiked = data.getBooleanExtra(IS_LIKED, false)

                Log.d(TAG, "Comment = $comment")
                Log.d(TAG, "Is liked = $isLiked")
            } else {
                Toast.makeText(this, "Can not extract result from secondary activity", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<Button>(R.id.btn_details_1).setOnClickListener{onClick(it)}
        findViewById<Button>(R.id.btn_details_2).setOnClickListener{onClick(it)}
        findViewById<Button>(R.id.btn_details_3).setOnClickListener{onClick(it)}
    }

    private fun startNewActivity() {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(FILM_ID, filmId)
        startDetailsActivity?.launch(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(FILM_ID, filmId)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        filmId = savedInstanceState.getInt(FILM_ID, 0 )

        selectFilm()
    }

    private fun selectFilm() {
        title1.setTextColor(ContextCompat.getColor(this,
            if (filmId == 1) R.color.title_selected else R.color.title_default))
        title2.setTextColor(ContextCompat.getColor(this,
            if (filmId == 2) R.color.title_selected else R.color.title_default))
        title3.setTextColor(ContextCompat.getColor(this,
            if (filmId == 3) R.color.title_selected else R.color.title_default))
    }

    companion object {
        const val FILM_ID = "film_id"
        const val COMMENT = "comment"
        const val IS_LIKED = "is_liked"
    }
}