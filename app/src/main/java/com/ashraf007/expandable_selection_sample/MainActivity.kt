package com.ashraf007.expandable_selection_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ashraf007.expandable_selection_view.adapter.BasicStringAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val items = listOf(
            "English",
            "Spanish",
            "Chinese",
            "Other Lang",
            "Other Lang",
            "Other Lang",
            "Other Lang",
            "Other Lang"
        )
        val adapter = BasicStringAdapter(items, "Language Spoken")
        expandableSelectionView.setAdapter(adapter)
//        Handler().postDelayed({
//            expandableSelectionView.setAdapter(adapter)
//        }, 2000)
    }
}