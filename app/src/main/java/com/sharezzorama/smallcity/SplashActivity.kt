package com.sharezzorama.smallcity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sharezzorama.smallcity.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity( Intent(this, MainActivity::class.java))
        finish()
    }

}
