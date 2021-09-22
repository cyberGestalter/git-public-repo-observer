package com.example.gitargus.ui

import android.view.MenuItem

interface ToolbarHandler {
    fun handleItem(item: MenuItem): Boolean
}