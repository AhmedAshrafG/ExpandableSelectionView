package com.ashraf007.expandable_selection_sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ashraf007.expandable_selection_view.adapter.BasicStringAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSingleSelectionView()
        initMultiSelectionView()
    }

    private fun initSingleSelectionView() {
        val genders = listOf(
            "Male",
            "Female",
            "Prefer not to specify"
        )
        val adapter = BasicStringAdapter(genders, "Select Gender..")
        singleSelectionView.setAdapter(adapter)
    }

    private fun initMultiSelectionView() {
        val languages = listOf(
            "English",
            "Spanish",
            "Chinese",
            "Other Lang",
            "Other Lang",
            "Other Lang",
            "Other Lang",
            "Other Lang"
        )
        val adapter = BasicStringAdapter(languages, "Language Spoken")
        multiSelectionView.setAdapter(adapter)
    }
}