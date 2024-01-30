package com.example.homework2

//import androidx.room.Entity
//import androidx.room.PrimaryKey

//@Entity(tableName = "images")
data class User {

    //@PrimaryKey(autoGenerate = true) val id: Int = 0,

    val name : String
    val picture : ByteArray

    constructor(name:String, picture:ByteArray) {
        this.name  = name;
        this.picture = picture
    }
}