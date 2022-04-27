package com.tenutz.multipleviewtypepagination.application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tenutz.multipleviewtypepagination.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}