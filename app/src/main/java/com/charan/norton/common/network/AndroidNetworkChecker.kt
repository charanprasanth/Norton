package com.charan.norton.common.network

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidNetworkChecker @Inject constructor(
    @param:ApplicationContext private val context: Context
) : NetworkChecker {
    override fun isConnected(): Boolean = isNetworkAvailable(context)
}