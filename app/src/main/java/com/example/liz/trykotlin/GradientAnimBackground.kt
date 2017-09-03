package com.example.liz.trykotlin

import android.graphics.drawable.AnimationDrawable
import android.view.View

/**
 * Created by Liz on 2017/8/30.
 * desc:
 */
class GradientAnimBackground private constructor(private val rootView: View,
                                                 private val animDuration: Int,
                                                 private val mAnimList: Int) {
    companion object {
        @JvmField
        val ANIM_GREEN_PURPLE = R.drawable.anim_green_purple
        @JvmField
        val ANIM_BLUE_PURPLE = R.drawable.anim_blue_purple
        @JvmField
        val ANIM_RED_PURPLE = R.drawable.anim_red_purple
    }

    private constructor(builder: Builder) :
            this(builder.view!!, builder.duration, builder.animList)

    fun startAnimation() {

        rootView.let {
            it.setBackgroundResource(mAnimList)
            val background = it.background as AnimationDrawable

            background.setEnterFadeDuration(animDuration)
            background.setExitFadeDuration(animDuration)

            it.background = background
            it.post {
                background.start()
            }
        }
    }

    class Builder {

        var duration: Int = 4000
            private set
        var view: View? = null
            private set
        var animList: Int = ANIM_GREEN_PURPLE
            private set

        fun setDuration(duration: Int): Builder {
            this.duration = duration
            return this
        }

        fun setView(view: View): Builder {
            this.view = view
            return this
        }

        fun setAnimList(animList: Int): Builder {
            this.animList = animList
            return this
        }

        fun build() = GradientAnimBackground(this)
    }
}
