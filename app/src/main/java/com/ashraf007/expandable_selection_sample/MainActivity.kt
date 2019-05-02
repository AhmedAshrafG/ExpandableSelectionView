package com.ashraf007.expandable_selection_sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ashraf007.expandable_selection_view.adapter.BasicStringAdapter
import com.ashraf007.expandable_selection_view.view.ExpandableSelectionView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

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
        singleSelectionView.selectionListener = {
            Toast.makeText(this, "SelectedIndex is $it", Toast.LENGTH_SHORT).show()
        }
//        singleSelectionView.setState(ExpandableSelectionView.State.Expanded)
//        singleSelectionView.selectIndex(1)
//        postDelayed {
//            singleSelectionView.clearSelection()
//            postDelayed {
//                singleSelectionView.selectIndex(0)
//            }
//        }
    }

    private fun postDelayed(function: TimerTask.() -> Unit) {
        Timer("Whatever").schedule(2000) {
            runOnUiThread { function(this) }
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
        multiSelectionView.selectionListener = {
            Toast.makeText(this, "SelectedIndices are $it", Toast.LENGTH_SHORT).show()
        }
        multiSelectionView.setState(ExpandableSelectionView.State.Expanded)
        multiSelectionView.selectIndex(1)
        postDelayed {
            multiSelectionView.unSelectIndices(listOf(1, 2))
            postDelayed {
                multiSelectionView.selectIndices(listOf(0, 2))
            }
        }
    }
}