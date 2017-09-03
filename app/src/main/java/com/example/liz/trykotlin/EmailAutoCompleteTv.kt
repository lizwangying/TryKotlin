package com.example.liz.trykotlin

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import java.util.regex.Pattern


/**
 * Created by Liz on 2017/8/30.
 * desc: 自动补全邮箱后缀的AutoCompleteTextView
 */
class EmailAutoCompleteTv(context: Context?, attrs: AttributeSet?) : AutoCompleteTextView(context, attrs) {

    private val emailSufixs: Array<String> = arrayOf("@qq.com", "@163.com", "@126.com", "@gmail.com", "@sina.com", "@hotmail.com",
            "@yahoo.cn", "@sohu.com", "@foxmail.com", "@139.com", "@icloud.com")

    init {
        this.setAdapter(EmailAutoCompleteAdapter(context, R.layout.item_layout_autocomplete_tv, emailSufixs))
        this.threshold = 1
        this.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                var text: String = this@EmailAutoCompleteTv.text.toString()
                if (android.text.TextUtils.isEmpty(text))
                    performFiltering(text, 0)//当该文本重新获得焦点后，重启自动完成
            } else {
                //当文本输入完成后，检查输入email地址的格式
                var ev: EmailAutoCompleteTv = view as EmailAutoCompleteTv
                var text: String = ev.text.toString()
                var pattern: Pattern = Pattern.compile("\"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)\$\"")
                if (!(!android.text.TextUtils.isEmpty(text) && pattern.matcher(text).matches())) {
                    this@EmailAutoCompleteTv.error = "邮箱格式不正确"
                }
            }

        }
    }


    override fun replaceText(text: CharSequence?) {
        var t: String = this.text.toString()
        var index: Int = t.indexOf("@")
        if (index != -1) t = text!!.substring(0, index)
        super.replaceText(t + text)
    }

    override fun performFiltering(text: CharSequence?, keyCode: Int) {
        //在用户输入完成后调用，将已经出入的文本和adapter中的数据对比，若匹配到adapter中数据的前半部分，则adapter中的这条数据就会在下拉框中出现
        var t = text.toString()
        var p: Pattern = Pattern.compile("^[a-zA-Z0-9_]+$")
        if (t.indexOf("@") == -1) {
            if (p.matcher(t).matches()) {
                super.performFiltering("@", keyCode)
            } else {
                //输入违法，关闭下拉框
                this.dismissDropDown()
            }
        } else {
            super.performFiltering(t.substring(t.indexOf("@")), keyCode)
        }

    }

    private inner class EmailAutoCompleteAdapter(context: Context?, resource: Int, objects: Array<out String>?) : ArrayAdapter<String>(context, resource, objects) {

//        var holder: ViewHolder? = null
//        @SuppressLint("SetTextI18n")
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            var view: View? = convertView
//            if (view == null) {
//                view = LayoutInflater.from(context).inflate(R.layout.item_layout_autocomplete_tv, null)
//                view!!.tag = holder
//            } else {
//                holder = view.tag as ViewHolder
//            }
//            holder!!.tv = item_email_tv
//            var text: String = this@EmailAutoCompleteTv.text.toString()
//            var index: Int = text.indexOf("@")
//            if (index != -1) {
//                text = text.substring(0, index)
//            }
//            //将用户输入的字符串和邮箱后缀拼接，在下拉框中显示
//            holder!!.tv.text = """$text${getItem(position)}"""
//            return view
//
//        }
//
//        inner class ViewHolder {
//            lateinit var tv: TextView
//        }

        @SuppressLint("SetTextI18n")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view: View? = convertView
            if (view == null)
                view = LayoutInflater.from(context).inflate(R.layout.item_layout_autocomplete_tv, null)
            var t = this@EmailAutoCompleteTv.getText().toString()
            val index = t.indexOf("@")
            if (index != -1)
                t = t.substring(0, index)
            //将用户输入的文本与adapter中的email后缀拼接后，在下拉框中显示
            var tv: TextView = view!!.findViewById(R.id.item_email_tv)
            tv.text = t + getItem(position)!!
            return view!!
        }

    }
}
