package com.sun.listview_recycleview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.lang.ref.WeakReference
import java.net.URL
import android.graphics.drawable.Drawable
import android.R.attr.bitmap
import java.io.IOException as IOException1


class ListViewAdapter : BaseAdapter() {
    private var data = emptyList<ItemData>()

    fun submitData(data: List<ItemData>) {
        this.data = data
        notifyDataSetChanged()

    }

    override fun getView(position: Int, itemView: View?, viewGroup: ViewGroup): View {
        val holder: ViewHolder
        val view: View
        if (null == itemView) {
            view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_image, viewGroup, false)
            holder = ViewHolder()
            holder.tvContext = view.findViewById(R.id.tv_content)
            holder.ivImage = view.findViewById(R.id.iv_image)
            view.tag = holder
        } else {
            view = itemView
            holder = itemView.tag as ViewHolder
        }
        val itemData = getItem(position)
        holder.tvContext.text = itemData.content
        val sy = holder.ivImage.tag as AsyncTask<*, *, *>?
        sy?.cancel(true)

        if (itemData.bm == null) {
            loadImage(itemData.url, holder.ivImage, position)
        } else {
            holder.ivImage.setImageBitmap(itemData.bm)
        }

        return view
    }

    override fun getItem(p0: Int): ItemData = data[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getCount(): Int = data.size

    private fun loadImage(link: String, ivImage: ImageView, position: Int) {
        val imageViewReference = WeakReference(ivImage)
        val sy =
                @SuppressLint("StaticFieldLeak")
                object : AsyncTask<String, Void, Bitmap>() {
                    override fun doInBackground(vararg strings: String): Bitmap? {
                        try {
                            val url = URL(strings[0])
                            val inputStream = url.openStream()
                            val bm = BitmapFactory.decodeStream(inputStream)
                            inputStream.close()
                            return bm
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        return null
                    }

                    override fun onPostExecute(bitmap: Bitmap?) {
                        if (bitmap != null) {
                            ivImage.setImageBitmap(bitmap)
                            data[position].bm = bitmap
                            notifyDataSetChanged()
                        }

                        // val imageView = imageViewReference.get()
                        //imageView?.setImageBitmap(bitmap)
                    }
                }
        ivImage.tag = sy
        sy.execute(link)
    }

    inner class ViewHolder {
        lateinit var tvContext: TextView
        lateinit var ivImage: ImageView

    }

}