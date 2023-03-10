/*
 * This file is part of BOINC.
 * http://boinc.berkeley.edu
 * Copyright (C) 2020 University of California
 *
 * BOINC is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * BOINC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with BOINC.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.berkeley.boinc.rpc

import android.os.Parcel
import android.os.Parcelable
import androidx.core.os.ParcelCompat.readBoolean
import androidx.core.os.ParcelCompat.writeBoolean

data class Notice(
        var seqno: Int = -1,
        var title: String = "",
        var description: String = "",
        var createTime: Double = 0.0,
        var arrivalTime: Double = 0.0,
        /**
         * Assigned by RSS source. Reserved values:
         * "client": generated by client
         * "server": scheduler RPC message
         */
        var category: String = "",
        /**
         * URL where original message can be seen, if any
         */
        var link: String = "",
        /**
         * If notice is associated with a project
         */
        var projectName: String = "",
        var isPrivate: Boolean = false,
        var isServerNotice: Boolean = false,
        var isClientNotice: Boolean = false
) : Parcelable {
    private constructor(parcel: Parcel)
            : this(seqno = parcel.readInt(), title = parcel.readString() ?: "",
            description = parcel.readString() ?: "", createTime = parcel.readDouble(),
            arrivalTime = parcel.readDouble(), category = parcel.readString() ?: "",
            link = parcel.readString() ?: "", projectName = parcel.readString() ?: "",
            isPrivate = readBoolean(parcel), isServerNotice = readBoolean(parcel),
            isClientNotice = readBoolean(parcel))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(seqno)
        dest.writeString(title)
        dest.writeString(description)
        dest.writeDouble(createTime)
        dest.writeDouble(arrivalTime)
        dest.writeString(category)
        dest.writeString(link)
        dest.writeString(projectName)
        writeBoolean(dest, isPrivate)
        writeBoolean(dest, isServerNotice)
        writeBoolean(dest, isClientNotice)
    }

    object Fields {
        const val SEQNO = "seqno"
        const val TITLE = "title"
        const val CREATE_TIME = "create_time"
        const val ARRIVAL_TIME = "arrival_time"
        const val Category = "category"
        const val LINK = "link"
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Notice> = object : Parcelable.Creator<Notice> {
            override fun createFromParcel(parcel: Parcel) = Notice(parcel)

            override fun newArray(size: Int) = arrayOfNulls<Notice>(size)
        }
    }
}
