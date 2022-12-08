package project.st991587295.jasleen.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    val name: String? = null,
    val ingredients: String? = null,
    val description: String? = null,
    val image: String? = null,
    val category: String? = null
)