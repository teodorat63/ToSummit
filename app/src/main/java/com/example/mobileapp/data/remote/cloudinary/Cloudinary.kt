package com.example.mobileapp.data.remote.cloudinary

import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.callback.ErrorInfo
import com.example.mobileapp.BuildConfig
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CloudinaryDataSource @Inject constructor(
    private val mediaManager: MediaManager
) {
    suspend fun uploadAvatar(uri: Uri, fileName: String): String? =
        suspendCoroutine { cont ->

            MediaManager.get().upload(uri)
                .option("upload_preset", "android_preset")
                .option("folder", "avatars")
                .option("public_id", fileName)
                .callback(object : UploadCallback {
                    override fun onSuccess(
                        requestId: String?,
                        resultData: MutableMap<Any?, Any?>?
                    ) {
                        cont.resume(resultData?.get("secure_url") as? String)
                    }

                    override fun onError(requestId: String?, error: ErrorInfo?) {
                        Log.e("Cloudinary", error?.description ?: "Upload error")
                        cont.resume(null)
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
                    override fun onStart(requestId: String?) {}
                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}
                })
                .dispatch()
        }

    suspend fun uploadLocationImage(
        uri: Uri,
        locationId: String
    ): String? =
        suspendCoroutine { cont ->

            MediaManager.get().upload(uri)
                .option("upload_preset", "android_preset")
                .option("folder", "locations")
                .option("public_id", locationId)
                .callback(object : UploadCallback {

                    override fun onSuccess(
                        requestId: String?,
                        resultData: MutableMap<Any?, Any?>?
                    ) {
                        cont.resume(resultData?.get("secure_url") as? String)
                    }

                    override fun onError(
                        requestId: String?,
                        error: ErrorInfo?
                    ) {
                        Log.e("Cloudinary", error?.description ?: "Upload error")
                        cont.resume(null)
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
                    override fun onStart(requestId: String?) {}
                    override fun onProgress(
                        requestId: String?,
                        bytes: Long,
                        totalBytes: Long
                    ) {}
                })
                .dispatch()
        }


}

