package com.example.androidgamepawel.classes

import com.example.androidgamepawel.R

class StartRoom(direction: String, image:Int, decoration: String)
    : RoomWithEnemy(direction, image, decoration) {

    override fun getEventImages(): Array<Int> {
        return arrayOf(image,
            R.drawable.startscreen2,
            R.drawable.startscreen3,
            R.drawable.startscreen)
    }

}