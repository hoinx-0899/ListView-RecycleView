package com.sun.listview_recycleview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image.view.*
import java.io.IOException
import java.lang.ref.WeakReference
import java.net.URL
import java.util.Collections.swap

open class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = ArrayList<ItemData>()

    fun submitData(data: List<ItemData>) {
        this.data = data as ArrayList<ItemData>
        notifyDataSetChanged()

    }

    private lateinit var onItemClicked: (itemData: ItemData) -> Unit

    fun setItemClickListener(onItemClicked: (itemData: ItemData) -> Unit) {
        this.onItemClicked = onItemClicked
    }

    open fun onClick(itemData: ItemData) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ViewHoder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHoder) {
            holder.bindView(data[position])
        }
    }

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
                        } catch (e: IOException) {
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

    inner class ViewHoder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                data[adapterPosition].let {
                    //onClick(it)
                    if (::onItemClicked.isInitialized) {
                        onItemClicked.invoke(it)
                    }
                }
            }
        }

        fun bindView(item: ItemData) {
            item.apply {
                itemView.apply {
                    tv_content.text = content
                    if (bm != null) {
                        iv_image.setImageBitmap(bm)
                    } else {
                        loadImage(url, iv_image, adapterPosition)
                    }

                }

            }
        }

    }

    fun addItem(position: Int, item: ItemData) {
        data.add(item)
        notifyItemInserted(position)
    }

    fun onMove(oldPosition: Int, newPosition: Int) {
        if (oldPosition < newPosition) {
            for (i in oldPosition until newPosition) {
                swap(data, i, i + 1)
            }
        } else {
            for (i in oldPosition downTo newPosition + 1) {
                swap(data, i, i - 1)
            }
        }
        notifyItemMoved(oldPosition, newPosition)
    }

    fun onSwipe(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


}