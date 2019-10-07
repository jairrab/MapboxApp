package com.jairrab.mapboxapp.ui.mainmapview.helpers

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.lang.ref.WeakReference
import javax.inject.Inject


internal class ViewSlider @Inject constructor(
) {

    private var weakSheetBehavior: WeakReference<BottomSheetBehavior<ViewGroup>?>? = null

    fun setup(bottomSheet: ViewGroup) {
        weakSheetBehavior = WeakReference(BottomSheetBehavior.from(bottomSheet))

        setupBottomSheetListeners()
    }

    private fun setupBottomSheetListeners() {
        sheetBehavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(view: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN        -> {
                        //not needed now, intentionally left blank
                    }
                    BottomSheetBehavior.STATE_EXPANDED      -> {
                        //not needed now, intentionally left blank
                    }
                    BottomSheetBehavior.STATE_COLLAPSED     -> {
                        //not needed now, intentionally left blank
                    }
                    BottomSheetBehavior.STATE_DRAGGING      -> {
                        //not needed now, intentionally left blank
                    }
                    BottomSheetBehavior.STATE_SETTLING      -> {
                        //not needed now, intentionally left blank
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        //not needed now, intentionally left blank
                    }
                }
            }

            override fun onSlide(view: View, v: Float) {

            }
        })
    }

    fun setState(state: Int) {
        sheetBehavior?.state = state
    }

    //closes BottomSheet when clicked outside
    //https://stackoverflow.com/a/45958525
    class AutoCloseBottomSheetBehavior<V : View>(context: Context, attrs: AttributeSet) :
        BottomSheetBehavior<V>(context, attrs) {

        override fun onInterceptTouchEvent(
            parent: CoordinatorLayout,
            child: V,
            event: MotionEvent
        ): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN && state == STATE_EXPANDED) {

                val outRect = Rect()
                child.getGlobalVisibleRect(outRect)

                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    state = STATE_COLLAPSED
                }
            }
            return super.onInterceptTouchEvent(parent, child, event)
        }
    }

    private val sheetBehavior
        get() = weakSheetBehavior?.get()
}

