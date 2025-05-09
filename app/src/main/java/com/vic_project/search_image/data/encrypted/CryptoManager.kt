package com.vic_project.search_image.data.encrypted

import android.content.Context
import com.vic_project.search_image.utils.AppConstants.SHARED_PREFS_FILENAME
import com.vic_project.search_image.utils.LogUtils.logger
import javax.inject.Inject

class CryptoManager @Inject constructor(private val context: Context) {
    private val cryptographyManager = CryptographyManager()

    private fun getPrefKey(alias: String, credentialType: CredentialType): String {
        return alias + "_" + credentialType.value
    }

    private fun getKeyName(alias: String, credentialType: CredentialType): String {
        return "alias_ ${alias}_${credentialType.value}"
    }

    fun encryptAndStorePassword(password: String, mAlias: String) {
        val alias = getKeyName(mAlias, CredentialType.PASSWORD)
        val cipher =
            cryptographyManager.getInitializedCipherForEncryption(alias)
        logger("encryptAndStorePassword") {
            "The password $password"
        }
        val encryptedServerTokenWrapper = cryptographyManager.encryptData(password, cipher)
        cryptographyManager.persistCiphertextWrapperToSharedPrefs(
            encryptedServerTokenWrapper,
            context,
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            getPrefKey(mAlias, CredentialType.PASSWORD)
        )
    }

    fun encryptAndStoreToken(
        token: String,
        mAlias: String,
        credential: CredentialType = CredentialType.TOKEN
    ) {
        val alias = getKeyName(mAlias, credential)
        val cipher =
            cryptographyManager.getInitializedCipherForEncryption(alias)
        logger("encryptAndStoreServerToken") {
            "The token from server is $token"
        }
        val encryptedServerTokenWrapper = cryptographyManager.encryptData(token, cipher)
        cryptographyManager.persistCiphertextWrapperToSharedPrefs(
            encryptedServerTokenWrapper,
            context,
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            getPrefKey(mAlias, credential)
        )
    }

    fun retrieveData(mAlias: String, credentialType: CredentialType): String? {
        val ciphertextWrapper = cryptographyManager.getCiphertextWrapperFromSharedPrefs(
            context,
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            getPrefKey(mAlias, credentialType)
        )
        val alias = getKeyName(mAlias, credentialType)
        ciphertextWrapper?.let { textWrapper ->
            val cipher = cryptographyManager.getInitializedCipherForDecryption(
                alias,
                ciphertextWrapper.initializationVector
            )
            return cryptographyManager.decryptData(textWrapper.ciphertext, cipher)
        }
        return null
    }

    fun clearData(tokenSecretKey: String, credentialType: CredentialType) {
        cryptographyManager.deleteKey(
            tokenSecretKey,
            context,
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            getPrefKey(tokenSecretKey, credentialType)
        )
    }
}

enum class CredentialType(val value: String) {
    PASSWORD("password"),
    TOKEN("token"),
    TOKEN_CHAT("token_chat")
}