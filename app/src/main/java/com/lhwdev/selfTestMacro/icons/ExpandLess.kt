package com.lhwdev.selfTestMacro.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Butt
import androidx.compose.ui.graphics.StrokeJoin.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


private val sExpandLess: ImageVector by lazy {
	ImageVector.Builder(
		name = "ExpandLess-24px", defaultWidth = 24.0.dp,
		defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
	).apply {
		path(
			fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
			strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
			pathFillType = NonZero
		) {
			moveTo(12.0f, 8.0f)
			lineToRelative(-6.0f, 6.0f)
			lineToRelative(1.41f, 1.41f)
			lineTo(12.0f, 10.83f)
			lineToRelative(4.59f, 4.58f)
			lineTo(18.0f, 14.0f)
			close()
		}
	}.build()
}

@Suppress("unused")
val FilledIcons.ExpandLess: ImageVector
	get() = sExpandLess
