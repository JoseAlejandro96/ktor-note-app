package com.josh.routes

import com.josh.data.checkIfUserExists
import com.josh.data.collections.User
import com.josh.data.registerUser
import com.josh.data.request.AccountRequest
import com.josh.data.responses.SimpleResponse
import com.josh.security.getHashWithSalt
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(){
    route("/register"){
        post {
            val request = try {
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(BadRequest)
                return@post
            }
            val userExists = checkIfUserExists(request.email)
            if (!userExists){
                if (registerUser(User(request.email, getHashWithSalt(request.password)))){
                    call.respond(OK, SimpleResponse(true, "Successfully created account!"))
                }else{
                    call.respond(OK, SimpleResponse(false, "An unknown error occurred"))
                }
            }else{
                call.respond(OK, SimpleResponse(false, "A user with that e-mail already exists"))
            }
        }
    }
}