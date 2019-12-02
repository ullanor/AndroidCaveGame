package com.example.androidgamepawel.classes

import com.example.androidgamepawel.R

open class RoomWithEnemy(direction: String, image:Int, decoration: String)
    : Room(direction, image, decoration) {

    open fun getEventImages(): Array<Int> {
        return arrayOf(image,
            R.drawable.wallmonster2,
            R.drawable.wallmonster3,
            R.drawable.wallmonster3blood)
    }

    override fun Description():String{
        return "$decoration"
    }

}