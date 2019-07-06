package com.ashraf007.expandableselectionsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ashraf007.expandableselectionview.adapter.BasicStringAdapter
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
        singleSelectionView.selectIndex(0, false)
        singleSelectionView.selectionListener = {
            Toast.makeText(this, "SelectedIndex is $it", Toast.LENGTH_SHORT).show()
        }
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
        multiSelectionView.selectIndices(listOf(2, 4), false)
        multiSelectionView.selectionListener = {
            Toast.makeText(this, "SelectedIndices are $it", Toast.LENGTH_SHORT).show()
        }
    }
}