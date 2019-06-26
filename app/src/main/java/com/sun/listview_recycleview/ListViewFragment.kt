package com.sun.listview_recycleview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.listview_recycleview.Data.initData
import kotlinx.android.synthetic.main.fragment_listview.*
import java.util.*

class ListViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_listview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val adapterLv = ListViewAdapter()
        adapterLv.submitData(initData())
        with(listView) {
            adapter = adapterLv
        }

    }

}