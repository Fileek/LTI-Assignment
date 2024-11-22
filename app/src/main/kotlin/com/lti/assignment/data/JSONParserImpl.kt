package com.lti.assignment.data

import android.content.Context
import com.lti.assignment.R
import java.lang.ref.WeakReference
import javax.inject.Inject

class JSONParserImpl @Inject constructor(
    context: Context
) : JSONParser {

    private val wrappedContext = WeakReference(context)

    override fun parseJSON(resId: Int): String? {
        return wrappedContext.get()
            ?.resources
            ?.openRawResource(R.raw.products)
            ?.bufferedReader()
            ?.use { reader ->
                reader.readText()
            }
    }
}