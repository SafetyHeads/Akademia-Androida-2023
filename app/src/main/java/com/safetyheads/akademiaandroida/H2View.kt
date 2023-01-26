package com.safetyheads.akademiaandroida

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

/* Example of use in XML:
         <com.safetyheads.akademiaandroida.H2View
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
    private val view: View = View.inflate(context, R.layout.h2_view, this)

    init {
        val textH2 = view.findViewById<TextView>(R.id.text_view_h2)
        textH2.text = attributes.getString(R.styleable.H2View_text)


    }

}