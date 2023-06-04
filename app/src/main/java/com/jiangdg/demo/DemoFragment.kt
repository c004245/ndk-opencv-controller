/*
 * Copyright 2017-2022 Jiangdg
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jiangdg.demo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Typeface
import android.hardware.usb.UsbDevice
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.jiangdg.ausbc.MultiCameraClient
import com.jiangdg.ausbc.base.BaseBottomDialog
import com.jiangdg.ausbc.base.CameraFragment
import com.jiangdg.ausbc.callback.ICameraStateCallBack
import com.jiangdg.demo.databinding.FragmentDemoBinding
import com.jiangdg.ausbc.callback.ICaptureCallBack
import com.jiangdg.ausbc.callback.IPlayCallBack
import com.jiangdg.ausbc.camera.CameraUVC
import com.jiangdg.ausbc.render.effect.EffectBlackWhite
import com.jiangdg.ausbc.render.effect.EffectSoul
import com.jiangdg.ausbc.render.effect.EffectZoom
import com.jiangdg.ausbc.render.effect.bean.CameraEffect
import com.jiangdg.ausbc.utils.*
import com.jiangdg.ausbc.utils.bus.BusKey
import com.jiangdg.ausbc.utils.bus.EventBus
import com.jiangdg.utils.imageloader.ILoader
import com.jiangdg.utils.imageloader.ImageLoaders
import com.jiangdg.ausbc.widget.*
import com.jiangdg.demo.databinding.DialogMoreBinding
import com.jiangdg.utils.MMKVUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/** CameraFragment Usage Demo
 *
 * @author Created by jiangdg on 2022/1/28
 */
class DemoFragment : CameraFragment(), CaptureMediaView.OnViewClickListener {
    private var isCapturingVideoOrAudio: Boolean = false


    private val mEffectDataList by lazy {
        arrayListOf(
            CameraEffect.NONE_FILTER,
            CameraEffect(
                EffectBlackWhite.ID,
                "BlackWhite",
                CameraEffect.CLASSIFY_ID_FILTER,
                effect = EffectBlackWhite(requireActivity()),
                coverResId = R.mipmap.filter0
            ),
            CameraEffect.NONE_ANIMATION,
            CameraEffect(
                EffectZoom.ID,
                "Zoom",
                CameraEffect.CLASSIFY_ID_ANIMATION,
                effect = EffectZoom(requireActivity()),
                coverResId = R.mipmap.filter2
            ),
            CameraEffect(
                EffectSoul.ID,
                "Soul",
                CameraEffect.CLASSIFY_ID_ANIMATION,
                effect = EffectSoul(requireActivity()),
                coverResId = R.mipmap.filter1
            ),
        )
    }


    private var mCameraMode = CaptureMediaView.CaptureMode.MODE_CAPTURE_PIC

    private lateinit var mViewBinding: FragmentDemoBinding

    override fun initView() {
        super.initView()
        mViewBinding.captureBtn.setOnViewClickListener(this)
        switchLayoutClick()
    }

    override fun initData() {
        super.initData()
    }

    override fun onCameraState(
        self: MultiCameraClient.ICamera,
        code: ICameraStateCallBack.State,
        msg: String?
    ) {
        when (code) {
            ICameraStateCallBack.State.OPENED -> handleCameraOpened()
            ICameraStateCallBack.State.CLOSED -> handleCameraClosed()
            ICameraStateCallBack.State.ERROR -> handleCameraError(msg)
        }
    }

    private fun handleCameraError(msg: String?) {
        ToastUtils.show("camera opened error: $msg")
    }

    private fun handleCameraClosed() {
        ToastUtils.show("camera closed success")
    }

    private fun handleCameraOpened() {
        ToastUtils.show("camera opened success")
    }

    private fun switchLayoutClick() {
        updateCameraModeSwitchUI()
        showRecentMedia()
    }

    override fun getCameraView(): IAspectRatio {
        return AspectRatioTextureView(requireContext())
    }

    override fun getCameraViewContainer(): ViewGroup {
        return mViewBinding.cameraViewContainer
    }

    override fun getRootView(inflater: LayoutInflater, container: ViewGroup?): View {
        mViewBinding = FragmentDemoBinding.inflate(inflater, container, false)
        return mViewBinding.root
    }

    override fun getGravity(): Int = Gravity.CENTER

    override fun onViewClick(mode: CaptureMediaView.CaptureMode?) {
        if (!isCameraOpened()) {
            ToastUtils.show("camera not worked!")
            return
        }
        when (mode) {
            CaptureMediaView.CaptureMode.MODE_CAPTURE_PIC -> {
                captureImage()
            }
        }
    }

    private fun captureImage() {
        captureImage(object : ICaptureCallBack {
            override fun onBegin() {

            }

            override fun onError(error: String?) {
                ToastUtils.show(error ?: "未知异常")
            }

            override fun onComplete(path: String?) {
                showRecentMedia(true)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }





    private fun showRecentMedia(isImage: Boolean? = null) {
        lifecycleScope.launch(Dispatchers.IO) {
            context ?: return@launch
            if (!isFragmentAttached()) {
                return@launch
            }
            try {
                if (isImage != null) {
                    MediaUtils.findRecentMedia(requireContext(), isImage)
                } else {
                    MediaUtils.findRecentMedia(requireContext())
                }?.also { path ->
                    val size = Utils.dp2px(requireContext(), 38F)
                    ImageLoaders.of(this@DemoFragment)
                        .loadAsBitmap(path, size, size, object : ILoader.OnLoadedResultListener {
                            override fun onLoadedSuccess(bitmap: Bitmap?) {
                                lifecycleScope.launch(Dispatchers.Main) {
                                }
                            }

                            override fun onLoadedFailed(error: Exception?) {
                                lifecycleScope.launch(Dispatchers.Main) {
                                    ToastUtils.show("Capture image error.${error?.localizedMessage}")
                                }
                            }
                        })
                }
            } catch (e: Exception) {
                activity?.runOnUiThread {
                    ToastUtils.show("${e.localizedMessage}")
                }
                Logger.e(TAG, "showRecentMedia failed", e)
            }
        }
    }

    private fun updateCameraModeSwitchUI() {
        mViewBinding.captureBtn.setCaptureViewTheme(CaptureMediaView.CaptureViewTheme.THEME_WHITE)
        mViewBinding.captureBtn.setCaptureMode(mCameraMode)
        if (mCameraMode == CaptureMediaView.CaptureMode.MODE_CAPTURE_PIC) {

        } else {
        }
    }




    companion object {
        private const val TAG = "DemoFragment"
        private const val WHAT_START_TIMER = 0x00
        private const val WHAT_STOP_TIMER = 0x01

        const val KEY_FILTER = "filter"
        const val KEY_ANIMATION = "animation"

    }
}
