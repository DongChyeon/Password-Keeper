package com.dongchyeon.passwordkeeper.util

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object AES256Chiper {
    var ivBytes = byteArrayOf(
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00
    )

    // AES256 암호화
    @Throws(
        UnsupportedEncodingException::class,
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun AES_Encode(str: String, secretKey: String): String {
        val textBytes = str.toByteArray(charset("UTF-8"))
        val ivSpec: AlgorithmParameterSpec = IvParameterSpec(ivBytes)
        val newKey = SecretKeySpec(secretKey.toByteArray(charset("UTF-8")), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec)
        return Base64.encodeToString(cipher.doFinal(textBytes), 0)
    }

    // AES256 복호화
    @Throws(
        UnsupportedEncodingException::class,
        NoSuchPaddingException::class,
        NoSuchAlgorithmException::class,
        InvalidAlgorithmParameterException::class,
        InvalidKeyException::class,
        BadPaddingException::class,
        IllegalBlockSizeException::class
    )
    fun AES_Decode(str: String?, secretKey: String): String {
        val textBytes = Base64.decode(str, 0)
        val ivSpec: AlgorithmParameterSpec = IvParameterSpec(ivBytes)
        val newKey = SecretKeySpec(secretKey.toByteArray(Charsets.UTF_8), "AES")
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec)
        return String(cipher.doFinal(textBytes), Charsets.UTF_8)
    }
}