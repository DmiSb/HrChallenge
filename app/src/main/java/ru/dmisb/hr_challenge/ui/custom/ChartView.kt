package ru.dmisb.hr_challenge.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import ru.dmisb.hr_challenge.R
import ru.dmisb.hr_challenge.data.model.Point
import ru.dmisb.hr_challenge.utils.dpToPx

class ChartView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val points = mutableListOf<Point>()
    private val displayWidth = context.resources.displayMetrics.widthPixels
    private val minStepX = context.dpToPx(8)

    private var stepX: Float = 0f
    private var stepY: Float = 0f
    private var maxY: Float = 0f

    private var minX: Float = 0f

    private val lineStrokeWidth = context.dpToPx(2f)
    private val linePaint = Paint()

    private val gridStrokeWidth = context.dpToPx(1f)
    private val gridPaint = Paint()

    init {
        linePaint.color = ContextCompat.getColor(context, R.color.teal_200)
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = lineStrokeWidth
        linePaint.isAntiAlias = true

        gridPaint.color = ContextCompat.getColor(context, R.color.gray)
        gridPaint.style = Paint.Style.STROKE
        gridPaint.strokeWidth = gridStrokeWidth
        gridPaint.isAntiAlias = true
    }

    fun bind(points: List<Point>) {
        this.points.clear()
        this.points.addAll(points)

        minX = points.minByOrNull { it.x }?.x ?: 0f

        measure(0, 0)
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var newWidth = if (points.isEmpty()) displayWidth else minStepX * points.size
        if (newWidth < displayWidth) newWidth = displayWidth

        stepX = if (points.isEmpty()) 0f else newWidth.toFloat() / points.size

        maxY= points.maxByOrNull { it.y }?.y ?: 0f
        val minY = points.minByOrNull { it.y }?.y ?: 0f
        val diffY = maxY - minY

        stepY = if (diffY != 0f) measuredHeight / (maxY - minY) else 0f

        setMeasuredDimension(newWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            if (points.isNotEmpty()) {
                drawGrid(it)
                drawLine(it)
            }
        }
    }

    private fun drawGrid(canvas: Canvas) {
        canvas.drawLine(0f, maxY * stepY, width.toFloat(), maxY * stepY, gridPaint)
    }

    private fun drawLine(canvas: Canvas) {
        var prevPoint: Point? = null
        points.forEachIndexed { index, point ->
            prevPoint?.let {
                canvas.drawLine(
                        (it.x - minX) * stepX,
                        (maxY - it.y) * stepY,
                        (point.x - minX) * stepX,
                        (maxY - point.y) * stepY,
                        linePaint
                )
            }
            prevPoint = point
        }
    }
}