package com.dicoding.storyapp.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EmailEditText: AppCompatEditText {

    constructor(context: Context) : super(context) {
        validEmail()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        validEmail()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        validEmail()
    }

    private fun validEmail() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(s).matches())
                {
                    error = "Alamat Email Tidak Valid"
                }
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
}