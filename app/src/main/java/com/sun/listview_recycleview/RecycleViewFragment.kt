package com.sun.listview_recycleview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_recycleview.*

class RecycleViewFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycleview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        registerListener()
        initView()
    }

    private fun initView() {
        //To do cach 2 de call back
//        val rcAdapter = object :RecyclerViewAdapter(){
//            override fun onClick(itemData: ItemData) {
//                //super.onClick(itemData)
//            }
//        }
        val rcAdapter = RecyclerViewAdapter()
        rcAdapter.submitData(Data.initData())
        attachAdapterToRecyclerView(recycleView, rcAdapter)


        rcAdapter.setItemClickListener {
            Toast.makeText(context, it.content, Toast.LENGTH_LONG).show()
        }

    }

    private fun registerListener() {
        // swipeRefreshLayout.isRefreshing = true
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()
        swipeRefreshLayout.isRefreshing = false

    }

    private fun attachAdapterToRecyclerView(recyclerView: RecyclerView, rcAdapter: RecyclerViewAdapter) {
        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            // val resId = R.anim.layout_animation_right_to_left
            //val animation = AnimationUtils.loadLayoutAnimation(context, resId)
            //recyclerView.layoutAnimation = animation
            //recyclerView.adapter?.notifyDataSetChanged()
            //recyclerView.scheduleLayoutAnimation()
            adapter = rcAdapter
            val space = resources.getDimensionPixelSize(R.dimen._8sdp)
            recyclerView.addItemDecoration(SpaceItemDecoration(space))
            val callback = DragManageAdapter(
                    rcAdapter, context,
                    ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
            )
            val helper = ItemTouchHelper(callback)
            helper.attachToRecyclerView(recyclerView)
        }
    }

}