package com.sun.listview_recycleview

import android.graphics.Bitmap

import java.lang.ref.WeakReference

class ItemData(internal val url: String, internal val content: String) {
    private var bitmap: Bitmap? = null

    var bm: Bitmap?
        get() = bitmap
        set(value) {
            this.bitmap = value
        }
}
