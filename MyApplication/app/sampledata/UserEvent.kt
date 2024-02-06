package com.example.myapplication

sealed interface UserEvent {
    object saveUser: UserEvent
    data class SetName(val name: String): UserEvent
    data class SetUri(val uri: String?): UserEvent

}