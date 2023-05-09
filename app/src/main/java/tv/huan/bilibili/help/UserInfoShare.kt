package tv.huan.bilibili.help

import tv.huan.bilibili.utils.CustomSpUtils

class UserInfoShare private constructor() {

    private val USER_SHARE = "user_share"
    private val AUTH_STATUS = "auth_status"


    companion object {
        val INSTANCE: UserInfoShare by lazy {
            UserInfoShare()
        }
    }

    fun setAuthStatus(value: String) {
        getShareObject().putString(AUTH_STATUS, value)
    }

    fun getAuthStatus(): String {
        return getShareObject().getString(AUTH_STATUS)
    }

    private fun getShareObject(): CustomSpUtils {
        return CustomSpUtils.getInstance().setSharedPreferences(USER_SHARE)
    }
}