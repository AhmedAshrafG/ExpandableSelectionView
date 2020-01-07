package com.ashraf007.expandableselectionsample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.ashraf007.expandableselectionview.Interfaces
import com.ashraf007.expandableselectionview.adapter.BasicStringAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val savedDarkModeStateID = "SavedDarkModeState"
    private val savedStateID = "SavedStateID"
    private val savedGenderID = "SavedGenderID"
    private val savedCountriesID = "SavedCountriesID"

    private var currentDarkModeTheme = 0
    private var currentStateSelection: List<Int>? = null
    private var currentGenderSelection = 0
    private var currentCountriesSelection = listOf(2, 4)

    override fun onCreate(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            currentDarkModeTheme = savedInstanceState.getInt(savedDarkModeStateID)
            currentStateSelection = savedInstanceState.getIntArray(savedStateID)!!.toList()
            currentGenderSelection = savedInstanceState.getInt(savedGenderID)
            currentCountriesSelection = savedInstanceState.getIntArray(savedCountriesID)!!.toList()
        }

        // https://blog.iamsuleiman.com/daynight-theme-android-tutorial-example/
        //
        // MODE_NIGHT_AUTO – The app switches to night theme based on the time. It makes use of location APIs
        //  (if necessary permissions granted) for accuracy. Otherwise falls back to using system time.
        // MODE_NIGHT_FOLLOW_SYSTEM – (DO NOT USE) This is more of an app-wide setting for night mode. Uses the system
        //  Night Mode setting to determine if it is night or not.
        // MODE_NIGHT_NO – Tells the app to never use night mode, regardless of time / location.
        // MODE_NIGHT_YES –  Tells app to use night mode always, regardless of time / location.

        val newDarkModeTheme: Int

        when (currentDarkModeTheme) {
            0 -> {
                newDarkModeTheme = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            1 -> {
                newDarkModeTheme = AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }
            2 -> {
                newDarkModeTheme = AppCompatDelegate.MODE_NIGHT_YES
            }
            3 -> {
                newDarkModeTheme = AppCompatDelegate.MODE_NIGHT_NO
            }
            else -> {
                newDarkModeTheme = AppCompatDelegate.MODE_NIGHT_NO
            }
        }

        AppCompatDelegate.setDefaultNightMode(newDarkModeTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSingleSelectionWithEntries()
        initSingleSelectionView()
        initMiltiSelectionWithEntries()
        initMultiSelectionView()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(savedDarkModeStateID, currentDarkModeTheme)

        outState.putIntArray(savedStateID, multipleSelectionWithEntries.selectedIndices.toIntArray())

        singleSelectionWithEntries.selectedIndex?.let {
            outState.putInt(savedGenderID, it)
        }

        outState.putIntArray(savedCountriesID, multiSelectionView.selectedIndices.toIntArray())
    }

    private fun initSingleSelectionWithEntries() {

        singleSelectionWithEntries.selectIndex(currentDarkModeTheme)

        singleSelectionWithEntries.setOnSelectionChange(Interfaces.SelectedItemChanged {
            Toast.makeText(this, "SelectedIndex is $it", Toast.LENGTH_SHORT).show()

            currentDarkModeTheme = it?.also {
                if (currentDarkModeTheme != it) {
                    recreate()
                }
            }!!
        })
    }

    private fun initMiltiSelectionWithEntries() {
        currentStateSelection?.let {
            multipleSelectionWithEntries.selectIndices(it)
        }
    }

    private fun initSingleSelectionView() {
        val genders = listOf(
            "Male",
            "Female",
            "I'd rather not say"
        )

        val adapter = BasicStringAdapter(genders, "Select Gender..")

        singleSelectionView.setAdapter(adapter)
        singleSelectionView.selectIndex(currentGenderSelection, false)

        singleSelectionView.setOnSelectionChange(Interfaces.SelectedItemChanged {
            Toast.makeText(this, "SelectedIndex is $it", Toast.LENGTH_SHORT).show()
        })

        singleSelectionView.autoCollapseOnSelection = false
    }

    private fun initMultiSelectionView() {
        val languages = listOf(
            "English",
            "Spanish",
            "Chinese",
            "Other 1",
            "Other 2",
            "Other 3",
            "Other 4",
            "Other 5"
        )

        val adapter = BasicStringAdapter(languages, "Language Spoken")

        multiSelectionView.setAdapter(adapter)
        multiSelectionView.selectIndices(currentCountriesSelection, false)

        multiSelectionView.setOnSelectionsChange(Interfaces.SelectedItemsChanged {
            Toast.makeText(this, "SelectedIndices are $it", Toast.LENGTH_SHORT).show()
        })
    }
}