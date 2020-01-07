package com.ashraf007.expandableselectionview;

import java.util.List;

public class Interfaces {

    ////////////////////////////////////////////////////////////////////
    // TODO: Is there really no way to have one callback to satisfy both??
    //  OK, there is but not until Kotlin 1.4; then I can use 'fun interface'
    //  see https://stackoverflow.com/questions/59525086/java-kotlin-callback-syntax-do-i-really-need-2-callback-definitions-after-conv/59527416#59527416
    //  MUST KEEP INTERFACES IN JAVA UNTIL KOTLIN 1.4
    ////////////////////////////////////////////////////////////////////

    public interface SelectedItemChanged {
        void onSelectionChange(Integer selectedItem);
    }

    public interface SelectedItemsChanged {
        void onSelectionChange(List<Integer> selectedItem);
    }
}
