package com.example.liz.trykotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by Liz on 2017/8/29.
 * desc: 注册/登陆主页面
 */
class AmazingLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val bg = GradientAnimBackground.Builder()
                .setAnimList(GradientAnimBackground.ANIM_GREEN_PURPLE)
                .setDuration(4000)
                .setView(root_view)
                .build()

        bg.startAnimation()
    }
}