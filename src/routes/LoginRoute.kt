package com.josh.routes

import com.josh.data.checkPasswordForEmail
import com.josh.data.request.AccountRequest
import com.josh.data.responses.SimpleResponse
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute(){
    route("/login"){
        post {
            val request = try {
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(BadRequest)
                return@post
            }

            val isPasswordCorrect = checkPasswordForEmail(request.email, request.password)
            if (isPasswordCorrect){
                call.respond(OK, SimpleResponse(true, "You are now logged in!"))
            }else{
                call.respond(OK, SimpleResponse(false, "The e-mail or password is incorrect"))
            }
        }
    }
}