package com.safetyheads.presentation.androidcomponents

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.ViewH2Binding

/* Example of use in XML:
         <com.safetyheads.presentation.androidcomponents.H2View
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:text = "Hello"
            />
* */

class H2View @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val attributes: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.H2View)
    private var binding: ViewH2Binding

    init {
        binding = ViewH2Binding.inflate(LayoutInflater.from(context), this)
        binding.textViewH2.text = attributes.getString(R.styleable.H2View_text)
    }
}