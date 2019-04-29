package com.karumi.androidanimations.sharedelements

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.karumi.androidanimations.R
import com.karumi.androidanimations.common.CircularTransform
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


interface SharedImageTransition {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val row: View = itemView.findViewById(R.id.row)
        val avatar: ImageView = itemView.findViewById(R.id.avatar)
        val userName: TextView = itemView.findViewById(R.id.userName)
    }

    data class SharedElements(val avatar: ImageView, val userName: TextView)

    class Binder(
        val getContext: () -> Context,
        val onClick: (avatarUrl: String, userFullName: String, sharedElements: SharedElements) -> Unit
    ) {
        operator fun invoke(receiver: VH, onFinished: () -> Unit) = receiver.bind(onFinished)

        companion object {
            private const val avatarUrl = "https://randomuser.me/api/portraits/men/21.jpg"
            private const val userFullName = "Sergio Gutiérrez Mota"
        }

        private fun VH.bind(onFinished: () -> Unit) {
            Picasso.get()
                .load(avatarUrl)
                .transform(CircularTransform())
                .fit()
                .noFade()
                .centerCrop()
                .into(avatar, object : Callback {
                    override fun onSuccess() {
                        onFinished()
                    }

                    override fun onError(e: Exception?) {
                        onFinished()
                    }
                })
            userName.text = userFullName
            row.setOnClickListener {
                onClick(
                    avatarUrl,
                    userFullName,
                    SharedElements(avatar, userName)
                )
            }
        }
    }
}