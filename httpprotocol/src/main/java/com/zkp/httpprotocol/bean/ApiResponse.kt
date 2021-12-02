package com.zkp.httpprotocol.bean

import com.google.gson.JsonElement

class ApiResponse(val code: Int, val msg: String?, val data: JsonElement?) {

}
