package com.ashraf007.expandableselectionsample;

import com.ashraf007.expandableselectionview.ExpandableMultiSelectionView;
import com.ashraf007.expandableselectionview.ExpandableSingleSelectionView;

public class JavaExample {

    void ExampleCallback() {

        ExpandableSingleSelectionView singleSelection = new ExpandableSingleSelectionView(null);

        singleSelection.setOnSelectionChange(selectedItem -> {

        });


        ExpandableMultiSelectionView multipleSelection = new ExpandableMultiSelectionView(null);

        multipleSelection.setOnSelectionsChange(selectedItem -> {

        });
    }
}
