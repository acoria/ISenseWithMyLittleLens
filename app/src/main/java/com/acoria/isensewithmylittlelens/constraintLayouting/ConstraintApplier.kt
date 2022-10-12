package com.acoria.isensewithmylittlelens.constraintLayouting

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class ConstraintApplier {
    fun applyConstraints(
        layout: ConstraintLayout,
        constraintDefinition: (ConstraintSet, parentLayoutId: Int) -> Unit
    ) {
        val constraintSet = ConstraintSet().apply { clone(layout) }
        constraintDefinition(constraintSet, layout.id)
        constraintSet.applyTo(layout)
    }
}