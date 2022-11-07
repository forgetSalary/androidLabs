package com.example.lab2.common

import java.security.SecureRandom
import java.security.spec.KeySpec
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class CommonHelper {
    companion object{
        private fun byteArrayOfInts(vararg ints: Int) = ByteArray(ints.size) { pos -> ints[pos].toByte() }
        private val salt : ByteArray = byteArrayOfInts(0x8a,0x6e,0xb8,0x84,0x3a,0xe,0x22,0x8a,0xb8,0xbe,0x4e,0x70,0x4e,0x48,0x30,0x5a)

        fun hashStringSha1(src: String): ByteArray {
            val random = SecureRandom()
            //val salt = ByteArray(16)
            //random.nextBytes(salt)
            val spec: KeySpec = PBEKeySpec(src.toCharArray(), salt, 65536, 128)
            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            return factory.generateSecret(spec).encoded
        }
    }
}