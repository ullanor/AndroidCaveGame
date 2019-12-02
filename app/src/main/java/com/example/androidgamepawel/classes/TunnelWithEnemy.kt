package com.example.androidgamepawel.classes

import com.example.androidgamepawel.R

class TunnelWithEnemy(direction: String, image:Int, decoration: String)
    : RoomWithEnemy(direction, image, decoration) {

    override fun getEventImages(): Array<Int> {
        return arrayOf(image,
            R.drawable.hallmonster2,
            R.drawable.hallmonster3,
            R.drawable.hallmonster3blood)
    }

}