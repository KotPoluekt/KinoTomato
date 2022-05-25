package com.tomato.otus

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.content.ContextCompat

class DetailsActivity : AppCompatActivity() {

    var filmName : Int = 0
    var filmDetails : Int = 0
    var filmDrawable: Drawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        loadFilm()

        val imageView = findViewById<ImageView>(R.id.imageView)
        val titleView = findViewById<TextView>(R.id.title)
        val detailsView = findViewById<TextView>(R.id.details)

        imageView.setImageDrawable(filmDrawable)
        titleView.setText(filmName)
        detailsView.setText(filmDetails)

        findViewById<Button>(R.id.btnClose).setOnClickListener {
            val data = Intent()

            val comment = findViewById<EditText>(R.id.editText).text.toString()
            val isLiked = findViewById<CheckBox>(R.id.checkBox).isChecked

            data.putExtra(MainActivity.COMMENT, comment)
            data.putExtra(MainActivity.IS_LIKED, isLiked)

            setResult(RESULT_OK, data)
            finish()
        }

        findViewById<Button>(R.id.btnInvite).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Привет. Давай посмотрим вместе фильм \"${resources.getString(filmName)}\"")
                type = "text/plain"
            }


            val shareIntent = Intent.createChooser(sendIntent, "Выберите действие")
            startActivity(shareIntent)
        }
    }

    private fun loadFilm() {
        when (intent.getIntExtra(MainActivity.FILM_ID, 1)) {
            1 -> {
                filmName = R.string.film_name_1
                filmDetails = R.string.film_definition_1
                filmDrawable = ContextCompat.getDrawable(this, R.drawable.film_1)
            }
            2 -> {
                filmName = R.string.film_name_2
                filmDetails = R.string.film_definition_2
                filmDrawable = ContextCompat.getDrawable(this, R.drawable.film_2)
            }
            3 -> {
                filmName = R.string.film_name_3
                filmDetails = R.string.film_definition_3
                filmDrawable = ContextCompat.getDrawable(this, R.drawable.film_3)
            }
        }
    }
}