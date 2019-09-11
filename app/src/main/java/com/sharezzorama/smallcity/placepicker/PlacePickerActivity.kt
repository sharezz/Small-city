package com.sharezzorama.smallcity.placepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.sharezzorama.smallcity.R

class PlacePickerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        supportFragmentManager.commit {
            replace(R.id.contentFrame, PlacePickerFragment())
        }

    }
}
