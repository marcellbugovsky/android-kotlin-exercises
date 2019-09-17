package com.android.example.guesstheword.misc

import android.content.Context

class ResourceProvider(context: Context) {

    private var context: Context = context

    fun getString(resId: Int): String? {
        return context.getResources().getString(resId)
    }

    fun getArrayString(resId: Int): Array<String> {
        return context.getResources().getStringArray(resId)
    }

}