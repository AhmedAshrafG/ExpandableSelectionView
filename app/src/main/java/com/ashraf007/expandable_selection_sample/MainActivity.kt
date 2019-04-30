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
            "LOL1",
            "LOL2",
            "LOL2",
            "LOL2",
            "LOL2",
            "LOL2",
            "LOL2",
            "LOL2",
            "LOL2"
        )
        val adapter = BasicStringAdapter(items, "Hint")
        expandableSelectionView.selectIndex(0)
        expandableSelectionView.setAdapter(adapter)
//        Handler().postDelayed({
//            expandableSelectionView.setAdapter(BasicStringAdapter(items.subList(0, 3), "LOL"))
//        }, 2000)
    }
}