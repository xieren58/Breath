package com.zkp.breath.adpter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zkp.breath.R


class GlideAdapter(data: MutableList<String>? = null) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.adpter_glide, data) {

    override fun convert(holder: BaseViewHolder, item: String) {
        val imageView = holder.getView<ImageView>(R.id.iv)

        val sharedOptions: RequestOptions = RequestOptions()
                .placeholder(R.drawable.block_canary_icon)
                .error(R.drawable.block_canary_icon)
                // 数据为null也视为合法情况
                .fallback(R.drawable.block_canary_icon)
                // 默认磁盘缓存策略
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                // 是否跳过内存缓存
                .skipMemoryCache(false)
                // 变化策略
                .centerCrop()
                // 多重变化策略
                .transform(CenterCrop(), GranularRoundedCorners(100f, 100f, 0f, 0f))
                // 禁止图片变换(取消之前设置的变化效果)
                .dontTransform()
                // 是否仅从缓存加载图片，如果缓存中没有，就会加载失败，不管有没有网络，一般这功能不开启，体验不好。
//                .onlyRetrieveFromCache(true)
                // 指定加载的图片大小，内部会按照最短边和源数据的宽高比进行计算
                .override(300, 300)


        Glide.with(imageView.context)
                /**
                 * 默认返回Drawable类型的RequestBuilder，可以使用asBitmap返回对应资源类型的Bitmap类型的RequestBuilder，
                 * 还有asGif,asFile等。
                 */
                .load(item)
                /**
                 * 缩略图加载，其实就是先加载显示一张低分辨率的图片，再显示高分辨率，提供更好的视觉过度效果，
                 * 和主请求并行启动，如果缩略图先加载完毕则先显示，如果后加载完毕则不显示。
                 *
                 * 如果有提供缩略图的url则使用如下 ：Glide.with(context)load(thumbnailUrl)，但是一般
                 * 的公司不会切多套图；所以还可以使用目标url但指定宽高：Glide.with(imageView.context).load(item).override(500, 500)，
                 * 还有一个简化版本只需要sizeMultiplier参数，指定尺寸为原来尺寸的百分比。
                 *
                 */
                .thumbnail(0.25f)
                /**
                 * 交叉淡入变换，如果是bitmap则使用BitmapTransitionOptions
                 */
                .transition(DrawableTransitionOptions.withCrossFade())
                /**
                 * apply() 方法可以被调用多次，因此 RequestOption 可以被组合使用，如果 RequestOptions
                 * 对象之间存在相互冲突的设置，那么只有最后一个被应用的 RequestOptions 会生效。
                 */
                .apply(sharedOptions)
                .into(imageView)

        // 如果需要操控到源数据可以使用target或者listener
//        testTarget(imageView, item)

    }

    /**
     * Target 负责展示占位符，加载资源，并为每个请求决定合适的尺寸，into() 方法不仅仅用于启动每个请求，
     * 它同时也指定了接收请求结果的 Target。Glide 提供了一个辅助方法 into(ImageView) ，它接受一个 ImageView
     * 参数并为其请求的资源类型包装了一个合适的 ImageViewTarget。
     */
    private fun testTarget(imageView: ImageView, item: String) {
        //        Glide.with(imageView.context)
//                .asBitmap()
//                .load(item)
//                .into(object : CustomViewTarget<ImageView, Bitmap>(imageView) {
//                    override fun onLoadFailed(errorDrawable: Drawable?) {
//                    }
//
//                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                        imageView.setImageBitmap(resource)
//                    }
//
//                    override fun onResourceCleared(placeholder: Drawable?) {
//                    }
//                })


        Glide.with(imageView.context)
                .load(item)
                .into(object : CustomViewTarget<ImageView, Drawable>(imageView) {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }

                    override fun onResourceCleared(placeholder: Drawable?) {
                    }
                })
    }

}