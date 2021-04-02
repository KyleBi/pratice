package com.github.kotlinapp.ui.activity

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.github.kotlinapp.App
import com.github.kotlinapp.R
import com.github.kotlinapp.extensions.ctx
import com.github.kotlinapp.extensions.slideEnter
import com.github.kotlinapp.extensions.slideExit
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

interface ToolbarManager {

    val toolbar: Toolbar

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }


    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> toolbar.ctx.startActivity<SettingActivity>()
                else -> App.instance.toast("Unknown")
            }
            true
        }
    }


    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }


    fun createUpDrawable() = DrawerArrowDrawable(toolbar.ctx).apply { progress = 1f }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dx > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }


}