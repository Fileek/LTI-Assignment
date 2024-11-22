package com.lti.assignment.data

import androidx.annotation.RawRes

interface JSONParser {

    fun parseJSON(@RawRes resId: Int): String?
}