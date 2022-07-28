package com.fenascimento.astranews.presentation.binding

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.fenascimento.astranews.R
import com.fenascimento.astranews.data.Post
import com.google.android.material.chip.Chip
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@BindingAdapter("postTitle")
fun TextView.setPostTitle(post: Post?) {
    post?.let {
        text = it.title
    }
}

@BindingAdapter("postSummary")
fun TextView.setPostSummary(post: Post?) {
    post?.let {
        text = it.summary
    }
}

@BindingAdapter("postImage")
fun ImageView.setImage(post: Post?) {
    post?.let {
        val progress = CircularProgressDrawable(context)
        progress.strokeWidth = 5f
        progress.centerRadius = 30f
        progress.setColorSchemeColors(Color.WHITE)
        progress.start()

        Glide.with(this).load(it.imageUrl)
            .placeholder(progress).into(this)
    }
}

@BindingAdapter("itemHasLaunch")
fun Chip.setHasLaunch(post: Post?) {
    post?.let {
        visibility = if (it.hasLaunch()) {
            View.VISIBLE
        } else {
            View.GONE
        }
        val count = it.getLaunchCount()
        this.text = resources.getQuantityString(R.plurals.numberOfLaunchEvents, count, count)
    }
}

@BindingAdapter("postPublishedDate")
fun Chip.setUpdate(post: Post?) {
    val format = DateTimeFormatter.ofPattern("dd/MM/uuuu")
        .withZone(ZoneId.from(ZoneOffset.UTC))
    with(format) {
        post?.let {
            val date = Instant.parse(it.publishedAt)
            text = this.format(date)
        }
    }
}