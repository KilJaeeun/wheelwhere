package com.example.kotlin_tmap


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Handler
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.skt.Tmap.TMapOverlayItem
import com.skt.Tmap.TMapView


class ImageOverlay(context: Context?, view: TMapView?) :
    TMapOverlayItem() {
    private var mContext: Context? = null
    private var mMapView: TMapView? = null
    private var mAnimationCount = 0
    private var popupLayout: LinearLayout? = null
    private var mRunnable: Runnable? = null
    override fun setImage(bitmap: Bitmap?) {
        super.setImage(bitmap)
    }

    override fun draw(canvas: Canvas, mapView: TMapView, showCallout: Boolean) {
        val leftX = mapView.getMapXForPoint(leftTopPoint.longitude, leftTopPoint.latitude)
        val leftY = mapView.getMapYForPoint(leftTopPoint.longitude, leftTopPoint.latitude)
        val RightX = mapView.getMapXForPoint(rightBottomPoint.longitude, rightBottomPoint.latitude)
        val RightY = mapView.getMapYForPoint(rightBottomPoint.longitude, rightBottomPoint.latitude)
        canvas.save()
        val imageWidth = RightX - leftX
    }

    var mHandler: Handler? = null
    fun startAnimation() {
        mRunnable = object : Runnable {
            override fun run() {
                if (animationIcons.size > 0) {
                    if (mAnimationCount >= animationIcons.size) mAnimationCount = 0
                    image = animationIcons[mAnimationCount]
                    mMapView!!.postInvalidate()
                    mAnimationCount++
                    mHandler!!.postDelayed(this, aniDuration.toLong())
                }
            }
        }
        mHandler = Handler()
        mHandler!!.post(mRunnable)
    }

    fun stopAnimation() {
        if (mHandler != null) {
            mHandler!!.removeCallbacks(mRunnable)
        }
    }

    init {
        mContext = context
        mMapView = view
        popupLayout = LinearLayout(mContext)
        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        popupLayout!!.setLayoutParams(params)
        popupLayout!!.setOrientation(LinearLayout.HORIZONTAL)
    }
}