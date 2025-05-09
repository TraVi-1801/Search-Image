package com.vic_project.search_image.utils.helpers

import android.app.Activity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.vic_project.search_image.utils.LogUtils.logger

object BiometricHelper {
    private lateinit var biometricPrompt: BiometricPrompt
    private fun createBiometricPrompt(
        activity: Activity,
        onAuthenticationError: (Int, CharSequence) -> Unit,
        onAuthenticationFailed: () -> Unit,
        onAuthenticationSucceeded: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(activity)

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                logger("onAuthenticationError") {
                    "$errorCode :: $errString"
                }
                onAuthenticationError(errorCode, errString)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                logger("onAuthenticationFailed") {
                    "Authentication failed for an unknown reason"
                }
                onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                logger("onAuthenticationSucceeded") {
                    "Authentication was successful"
                }
                onAuthenticationSucceeded(result)
            }
        }

        return BiometricPrompt(activity as FragmentActivity, executor, callback)
    }

    private fun createDecryptionPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .setTitle("Biometric Authentication")
            .setDescription("Scan your fingerprint.")
            .setConfirmationRequired(false)
            .setNegativeButtonText("Cancel")
            .build()
    }

    private fun createEncryptionPromptInfo(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
            .setTitle("Biometric Authorization")
            .setSubtitle(
                "You need to authorize HRM to use biometric information before activating the feature."
            )
            .setDescription("Scan your fingerprint.")
            .setConfirmationRequired(false)
            .setNegativeButtonText("Cancel")
            .build()
    }

    private fun showBiometricPromptForEncryption(
        activity: Activity,
        onAuthenticationSucceeded: (BiometricPrompt.CryptoObject?) -> Unit,
        onAuthenticationError: (Int, CharSequence) -> Unit,
        onAuthenticationFailed: () -> Unit,
        onCancel: () -> Unit
    ) {
        val canAuthenticate =
            BiometricManager.from(activity)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val biometricPrompt = createBiometricPrompt(
                activity,
                onAuthenticationSucceeded = {
                    onAuthenticationSucceeded(it.cryptoObject)
                },
                onAuthenticationError = onAuthenticationError,
                onAuthenticationFailed = onAuthenticationFailed
            )
            val promptInfo = createEncryptionPromptInfo()
            biometricPrompt.authenticate(promptInfo)
        } else {
            onCancel()
        }
    }

    fun showBiometricPrompt(
        activity: Activity,
        isEncrypting: Boolean,
        onAuthenticationSucceeded: (BiometricPrompt.CryptoObject?) -> Unit,
        onAuthenticationError: (Int, CharSequence) -> Unit,
        onAuthenticationFailed: () -> Unit,
        onCancel: () -> Unit
    ) {
        if (isEncrypting) {
            showBiometricPromptForEncryption(
                activity,
                onAuthenticationSucceeded = onAuthenticationSucceeded,
                onAuthenticationError = onAuthenticationError,
                onAuthenticationFailed = onAuthenticationFailed,
                onCancel = onCancel
            )
        } else {
            showBiometricPromptForDecryption(
                activity,
                onAuthenticationSucceeded = onAuthenticationSucceeded,
                onAuthenticationError = onAuthenticationError,
                onAuthenticationFailed = onAuthenticationFailed
            )
        }
    }

    private fun showBiometricPromptForDecryption(
        activity: Activity,
        onAuthenticationSucceeded: (BiometricPrompt.CryptoObject?) -> Unit,
        onAuthenticationError: (Int, CharSequence) -> Unit,
        onAuthenticationFailed: () -> Unit
    ) {
        biometricPrompt =
            createBiometricPrompt(
                activity,
                onAuthenticationSucceeded = {
                    onAuthenticationSucceeded(it.cryptoObject)
                },
                onAuthenticationFailed = onAuthenticationFailed,
                onAuthenticationError = onAuthenticationError
            )
        val promptInfo = createDecryptionPromptInfo()
        biometricPrompt.authenticate(promptInfo)
    }
}