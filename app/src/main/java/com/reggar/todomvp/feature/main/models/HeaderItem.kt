package com.reggar.todomvp.feature.main.models

import android.support.annotation.StringRes
import com.reggar.todomvp.common.adapter.ViewType
import com.reggar.todomvp.feature.main.adapter.AdapterConstants

data class HeaderItem(
        @StringRes val stringRes: Int
) : ViewType {
    override val viewType = AdapterConstants.HEADER
}
