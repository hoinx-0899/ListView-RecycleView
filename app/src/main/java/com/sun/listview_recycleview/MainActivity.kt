package com.sun.listview_recycleview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        registerListeners()
    }

    private fun initView() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.rootLayout, ListViewFragment(), ListViewFragment::class.java.simpleName)
        fragmentTransaction.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_out_left,
                R.anim.slide_in_right
        )
        fragmentTransaction.commit()
    }

    private fun registerListeners() {
        btnRecycle.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.btnRecycle -> {
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.rootLayout, RecycleViewFragment(), RecycleViewFragment::class.java.simpleName)
                fragmentTransaction.setCustomAnimations(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left,
                        R.anim.slide_out_left,
                        R.anim.slide_in_right
                )
                fragmentTransaction.commit()

            }
        }

    }
}
