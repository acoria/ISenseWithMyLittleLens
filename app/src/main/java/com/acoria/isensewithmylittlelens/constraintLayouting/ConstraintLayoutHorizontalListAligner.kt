package com.acoria.isensewithmylittlelens.constraintLayouting

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class ConstraintLayoutHorizontalListAligner : IConstraintLayoutHorizontalListAligner {

    private val constraintApplier by lazy { ConstraintApplier() }

    override fun align(
        parentLayout: ConstraintLayout,
        viewIdList: List<Int>,
        maxNumberOfEntriesPerRow: Int,
        spreadHorizontally: Boolean
    ) {
        viewIdList.forEachIndexed { index, viewId ->
            if (spreadHorizontally) {
                parentLayout.findViewById<View>(viewId).layoutParams =
                    ConstraintLayout.LayoutParams(
                        0,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
            }
            setHorizontalConstraintsToView(
                parentLayout, viewId,
                determineTopViewId(
                    index,
                    viewIdList,
                    maxNumberOfEntriesPerRow,
                    parentLayout.id
                ), determineBottomViewId(
                    index,
                    viewIdList,
                    maxNumberOfEntriesPerRow,
                    parentLayout.id
                ), determineStartViewId(
                    index,
                    viewIdList,
                    maxNumberOfEntriesPerRow,
                    parentLayout.id
                ), determineEndViewId(
                    index,
                    viewIdList,
                    maxNumberOfEntriesPerRow,
                    parentLayout.id
                )
            )
        }
    }

    private fun determineStartViewId(
        index: Int,
        viewIdList: List<Int>,
        maxNumberOfEntriesPerRow: Int,
        parentLayoutId: Int,
    ): Int? {
        return if (index == 0 || index.mod(maxNumberOfEntriesPerRow) == 0) {
            //first entry in row -> attach to parent
            parentLayoutId
        } else {
            //attach to left neighbor
            viewIdList[index - 1]
        }
    }

    private fun determineTopViewId(
        index: Int,
        viewIdList: List<Int>,
        maxNumberOfEntriesPerRow: Int,
        parentLayoutId: Int,
    ): Int {
        return if (index == 0 || isElementInFirstRow(index, maxNumberOfEntriesPerRow)) {
            //first entry or first row
            parentLayoutId
        } else {
            return if (isFirstElementInList(index, maxNumberOfEntriesPerRow)) {
                //first element in row -> attach to previous first view in row
                viewIdList[index - maxNumberOfEntriesPerRow]
            } else {
                //other element in row -> attach to previous first view in row
                viewIdList[getElementIndexOfFirstElementInPreviousRow(
                    index,
                    maxNumberOfEntriesPerRow
                )]
            }
        }
    }

    private fun determineEndViewId(
        index: Int,
        viewIdList: List<Int>,
        maxNumberOfEntriesPerRow: Int,
        parentLayoutId: Int,
    ): Int? {
        return if (isLastElementInList(index, viewIdList)
        ) {
            //attach to parent
            parentLayoutId
        } else {
            if (isLastElementInRow(index, maxNumberOfEntriesPerRow)) {
                parentLayoutId
            } else {
                //attach to left neighbor
                viewIdList[index + 1]
            }
        }
    }

    private fun determineBottomViewId(
        index: Int,
        viewIdList: List<Int>,
        maxNumberOfEntriesPerRow: Int,
        parentLayoutId: Int
    ): Int {
        return if (hasListNextRow(index, maxNumberOfEntriesPerRow, viewIdList)) {
            //view has another element below it -> attach to first element of next row
            return viewIdList[getElementIndexOfFirstElementInNextRow(
                index,
                maxNumberOfEntriesPerRow
            )]
        } else {
            //last row -> attach to parent
            parentLayoutId
        }
    }


    private fun isLastElementInList(
        index: Int,
        viewIdList: List<Int>
    ) = index + 1 == viewIdList.size

    private fun isElementInFirstRow(
        index: Int,
        maxNumberOfEntriesPerRow: Int
    ) = index / maxNumberOfEntriesPerRow == 0

    private fun isLastElementInRow(
        index: Int,
        maxNumberOfEntriesPerRow: Int
    ) = (index + 1).mod(maxNumberOfEntriesPerRow) == 0

    private fun hasListNextRow(
        index: Int,
        maxNumberOfEntriesPerRow: Int,
        viewIdList: List<Int>
    ) = getElementIndexOfFirstElementInNextRow(
        index,
        maxNumberOfEntriesPerRow
    ) <= viewIdList.size - 1


    private fun getElementIndexOfFirstElementInNextRow(
        index: Int,
        maxNumberOfEntriesPerRow: Int
    ): Int {
        return index + (maxNumberOfEntriesPerRow - getElementPositionFromLeftInList(
            index,
            maxNumberOfEntriesPerRow
        ))
    }

    private fun getElementIndexOfFirstElementInPreviousRow(
        index: Int,
        maxNumberOfEntriesPerRow: Int
    ): Int {
        return index - getElementPositionFromLeftInList(
            index,
            maxNumberOfEntriesPerRow
        ) - maxNumberOfEntriesPerRow
    }

    private fun isFirstElementInList(index: Int, maxNumberOfEntriesPerRow: Int): Boolean {
        return getElementPositionFromLeftInList(index, maxNumberOfEntriesPerRow) == 0
    }

    private fun getElementPositionFromLeftInList(
        index: Int,
        maxNumberOfEntriesPerRow: Int
    ): Int {
        return index.mod(maxNumberOfEntriesPerRow)
    }

    private fun setHorizontalConstraintsToView(
        parentLayout: ConstraintLayout,
        viewIdToConnect: Int,
        topViewId: Int?,
        bottomViewId: Int?,
        startViewId: Int?,
        endViewId: Int?
    ) {
        constraintApplier.applyConstraints(parentLayout) { constraintSet, parentLayoutId ->
            topViewId?.let {
                connectTopView(
                    constraintSet,
                    viewIdToConnect,
                    topViewId,
                    parentLayoutId
                )
            }
            bottomViewId?.let {
                connectBottomView(
                    bottomViewId,
                    constraintSet,
                    viewIdToConnect,
                    parentLayoutId
                )
            }
            startViewId?.let {
                connectStartView(
                    startViewId,
                    constraintSet,
                    viewIdToConnect,
                    parentLayoutId,
                )
            }
            endViewId?.let {
                connectEndView(
                    endViewId,
                    constraintSet,
                    viewIdToConnect,
                    parentLayoutId,
                )
            }
        }
    }

    private fun connectEndView(
        endViewId: Int,
        constraintSet: ConstraintSet,
        viewIdToConnect: Int,
        parentLayoutId: Int,
    ) {
        if (endViewId == parentLayoutId) {
            constraintSet.connect(
                viewIdToConnect,
                ConstraintSet.END,
                parentLayoutId,
                ConstraintSet.END
            )
        } else {
            constraintSet.connect(
                viewIdToConnect,
                ConstraintSet.END,
                endViewId,
                ConstraintSet.START
            )
        }
    }

    private fun connectStartView(
        startViewId: Int,
        constraintSet: ConstraintSet,
        viewIdToConnect: Int,
        parentLayoutId: Int,
    ) {
        if (startViewId == parentLayoutId) {
            constraintSet.connect(
                viewIdToConnect,
                ConstraintSet.START,
                parentLayoutId,
                ConstraintSet.START
            )
        } else {
            constraintSet.connect(
                viewIdToConnect,
                ConstraintSet.START,
                startViewId,
                ConstraintSet.END
            )
        }
    }

    private fun connectBottomView(
        bottomViewId: Int,
        constraintSet: ConstraintSet,
        viewIdToConnect: Int,
        parentLayoutId: Int
    ) {
        if (bottomViewId == parentLayoutId) {
            constraintSet.connect(
                viewIdToConnect,
                ConstraintSet.BOTTOM,
                parentLayoutId,
                ConstraintSet.BOTTOM
            )
        } else {
            constraintSet.connect(
                viewIdToConnect,
                ConstraintSet.BOTTOM,
                bottomViewId,
                ConstraintSet.TOP
            )
        }
    }

    private fun connectTopView(
        constraintSet: ConstraintSet,
        viewIdToConnect: Int,
        topViewId: Int,
        parentLayoutId: Int
    ) {
        if (topViewId == parentLayoutId) {
            constraintSet.connect(
                viewIdToConnect,
                ConstraintSet.TOP,
                topViewId,
                ConstraintSet.TOP
            )
        } else {
            constraintSet.connect(
                viewIdToConnect,
                ConstraintSet.TOP,
                topViewId,
                ConstraintSet.BOTTOM
            )
        }
    }
}