package com.acoria.isensewithmylittlelens.core.constraintLayouting

import androidx.constraintlayout.widget.ConstraintLayout

interface IConstraintLayoutHorizontalListAligner {
    fun align(
        parentLayout: ConstraintLayout,
        viewIdList: List<Int>,
        maxNumberOfEntriesPerRow: Int = 2,
        spreadHorizontally: Boolean = false
    )
}