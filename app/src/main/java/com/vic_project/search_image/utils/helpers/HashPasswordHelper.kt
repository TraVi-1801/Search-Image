package com.vic_project.search_image.utils.helpers

import android.content.res.AssetManager
import android.os.Build
import android.util.Base64
import at.favre.lib.crypto.bcrypt.BCrypt
import java.io.DataInputStream
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


object HashPasswordHelper {
    private var fileBytes: ByteArray? = null

    /**
     * First of all, Need get file publicKey.rsa from BE and save in assets/raw folder
     *
     * @param password the password is string to generate encrypt password
     *
     * @return the string password encrypted.
     */
    fun generateEncryptPassword(password: String): String? {
        val passwordDidSHA256 = getPasswordHashedByBCrypt(password)
        return getPublicKey(passwordDidSHA256)
    }

    /**
     * To generate key HMAC Sha512 we need <i>keyHMAC</i> from BE to use when generate key
     * match with BE
     *
     * @return the string HMAC Sha512 to use when generate key
     */
    fun getHMACSha512(keyHMAC: String, content: String): String? {
        try {
            val byteKey = keyHMAC.toByteArray(StandardCharsets.UTF_8)
            val sha512Hmac = Mac.getInstance("HmacSHA512")
            val keySpec = SecretKeySpec(byteKey, "HmacSHA512")
            sha512Hmac.init(keySpec)
            val macData = sha512Hmac.doFinal(content.toByteArray(StandardCharsets.UTF_8))

            // Can either base64 encode or put it right into hex
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                java.util.Base64.getEncoder().encodeToString(macData)
            } else {
                Base64.encodeToString(macData, Base64.NO_WRAP)
            }
            //result = bytesToHex(macData);
        } catch (e: InvalidKeyException) {
            e.printStackTrace()
            return null
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }
    }

    private fun sha256(toSHAString: String): String? {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hash = digest.digest(toSHAString.toByteArray(charset("UTF-8")))
            val hexString = StringBuffer()
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            hexString.toString()
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
    }

    private fun getPublicKey(text: String): String? {
        try { // Encrypt the string using the public key
            val test = text.toByteArray()
            val cipherText = encrypt(test, getPublicKey(fileBytes))
            return Base64.encodeToString(cipherText, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun md5(s: String): String {
        val md5 = "MD5"
        try { // Create MD5 Hash
            val digest = MessageDigest
                .getInstance(md5)
            digest.update(s.toByteArray())
            val messageDigest = digest.digest()
            // Create Hex String
            val hexString = StringBuilder()
            for (aMessageDigest in messageDigest) {
                var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
                while (h.length < 2) h = "0$h"
                hexString.append(h)
            }
            return hexString.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    @Throws(Exception::class)
    private fun getPublicKey(keyBytes: ByteArray?): PublicKey {
        val spec = X509EncodedKeySpec(keyBytes)
        val kf = KeyFactory.getInstance("RSA")
        println("Public Key " + kf.generatePublic(spec))
        return kf.generatePublic(spec)
    }

    private fun encrypt(text: ByteArray?, key: PublicKey?): ByteArray? {
        var cipherText: ByteArray? = null
        try {
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            cipherText = cipher.doFinal(text)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cipherText
    }

    fun getByte(assets: AssetManager) {
        if (fileBytes != null) return
        try {
            val fis = assets.open("raw/" + "publicKey.rsa")
            val dis = DataInputStream(fis)
            fileBytes = ByteArray(fis.available())
            dis.readFully(fileBytes)
            dis.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    const val alg = "$2a$"
    private const val cost = 12
    private val saltByteArray = byteArrayOf(-122, 107, 9, 116, 7, -112, 83, 0, 73, 58, -105, -3, -98, 6, 96, -9)
    private fun getPasswordHashedByBCrypt(password: String): String {
        val hashByteArray =
            BCrypt.withDefaults().hash(cost, saltByteArray, password.toByteArray())
        val hashCharArray = hashByteArray.map {
            it.toInt().toChar()
        }
        return hashCharArray.joinToString(separator = "")
    }
}