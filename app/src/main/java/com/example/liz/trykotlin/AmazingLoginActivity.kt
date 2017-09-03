package com.example.liz.trykotlin

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*
import java.util.regex.Pattern

/**
 * Created by Liz on 2017/8/29.
 * desc: 注册/登陆主页面
 */
class AmazingLoginActivity : AppCompatActivity(), View.OnClickListener {
    private var userInfo = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setAmazingBg()
        setListener()


    }

    private fun setAmazingBg() {
        val bg = GradientAnimBackground.Builder()
                .setAnimList(GradientAnimBackground.ANIM_GREEN_PURPLE)
                .setDuration(4000)
                .setView(root_view)
                .build()

        bg.startAnimation()
    }

    private fun setListener() {
        im_clear_email.setOnClickListener(this)
        im_clear_pwd.setOnClickListener(this)
        email_sign_in_button.setOnClickListener(this)

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                var pattern: Pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+\$")
                if (!TextUtils.isEmpty(email.text)) {
                    var str = email.text
                    if (!pattern.matcher(str).matches()) {
                        email.error = "邮箱格式不正确"
                    } else {
                        email.error = null
                    }
                }
                controlClear(!TextUtils.isEmpty(email.text), im_clear_email, email)

            }
        })
        email.setOnFocusChangeListener({ _, hasFocus ->
            controlClear(hasFocus && !TextUtils.isEmpty(email.text), im_clear_email, email)
        })
        password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var pattern: Pattern = Pattern.compile("^.{6,}\$")
                if (!TextUtils.isEmpty(password.text)) {
                    var str = password.text
                    if (!pattern.matcher(str).matches()) {
                        password.error = "密码长度最少6位"
                    } else {
                        password.error = null
                    }
                }
            }
        })
        password.setOnFocusChangeListener({ _, hasFocus ->
            controlClear(hasFocus && !TextUtils.isEmpty(password.text), im_clear_pwd, password)
        })
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.im_clear_email -> email.setText("")
            R.id.im_clear_pwd -> password.setText("")
            R.id.email_sign_in_button -> {
                if (TextUtils.isEmpty(email.text)) {
                    email.error = "请输入邮箱哦~"
                }
                if (TextUtils.isEmpty(password.text)) {
                    password.error = "请输入密码哦~"
                }
                var manager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(window.decorView.windowToken, 0)


                Snackbar.make(root_view, "注册成功", Snackbar.LENGTH_SHORT).show()
            }

        }
    }

    private fun controlClear(clear: Boolean, clearView: View, editText: EditText) {
        if (clear) {
            clearView.visibility = View.VISIBLE
        } else {
            clearView.visibility = View.GONE
            editText.error = null
        }
    }
}