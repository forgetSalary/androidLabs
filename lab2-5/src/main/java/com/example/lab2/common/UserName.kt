package com.example.lab2.common

class UserName{
    companion object{
        fun hashPassword(pass:String): ByteArray{
            return CommonHelper.hashStringSha1(pass)
        }
    }


    data class Raw(
        val login: String,
        val pass: String
    ){
        fun toEncoded() : Encoded{
            return Encoded(
                0,
                login,
                hashPassword(pass)
            )
        }
    }

    data class Encoded(
        val id: Int,
        val login: String,
        val passHash: ByteArray
    ) {
        companion object{
            const val PASS_HASH_LEN = 20
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Encoded

            if (id != other.id) return false
            if (login != other.login) return false
            if (!passHash.contentEquals(other.passHash)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = id
            result = 31 * result + login.hashCode()
            result = 31 * result + passHash.contentHashCode()
            return result
        }
    }

}

