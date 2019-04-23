package com.ashraf007.selection_view_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ashraf007.selection_view_sample.adapter.StringAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expandableSelectionView.expandableSelectionAdapter = StringAdapter(listOf("LOL1", "LOL2"))
    }
}