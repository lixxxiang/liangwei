package com.android.lixiang.liangwei

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.ielse.imagewatcher.ImageWatcher

internal class GlideSimpleLoader : ImageWatcher.Loader {
    override fun load(context: Context, uri: Uri, lc: ImageWatcher.LoadCallback) {
        Glide.with(context).load(uri)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        lc.onResourceReady(resource)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        lc.onLoadFailed(errorDrawable)
                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        lc.onLoadStarted(placeholder)
                    }
                })
    }
}
