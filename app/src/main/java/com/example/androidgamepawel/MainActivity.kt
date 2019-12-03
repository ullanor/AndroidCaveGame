package com.example.androidgamepawel

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.androidgamepawel.classes.Game
import kotlinx.android.synthetic.main.activity_main.*
import com.bumptech.glide.load.resource.gif.GifDrawable
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.CountDownTimer
import androidx.annotation.MainThread
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.androidgamepawel.classes.RoomImpossible
import com.example.androidgamepawel.classes.RoomWithEnemy


class MainActivity : AppCompatActivity() {
    val game:Game = Game()
    var gameIsStarted:Boolean = false

    lateinit var timer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation =  (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main)

        activateButtons()
        InitGame()
    }

    private fun activateButtons(){
        GameButtonForward.setOnClickListener{
            buttonClick(0)
        }
        GameButtonLeft.setOnClickListener{
            buttonClick(1)
        }
        GameButtonRight.setOnClickListener{
            buttonClick(2)
        }
        GameButtonGameOver.setOnClickListener{
            if(!gameIsStarted)
                startOver(true)
            else
                startOver(false)
        }
    }

    private fun buttonClick(dir:Int){
        try {
            if(game.currentLocation.Exits[dir] is RoomImpossible){
                Toast.makeText(this@MainActivity, "There's no way!", Toast.LENGTH_SHORT).show() //Room is impossible!

            }else
                MoveToANewLocation(dir)
        }
        catch (e: Exception) {
            Toast.makeText(this@MainActivity, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun MoveToANewLocation(newLocation:Int){
        val isMonsterRoom:Boolean = game.MoveToANewLocation(newLocation)
        ShowData()
        if (isMonsterRoom) {
            SetImageAnimation()
        }
    }

    private fun InitGame(){
        game.SetStartLocation()
        ShowData()
        SetImageAnimation()
    }

    private fun ShowData(){
        //gif author: jjjjjohn on GIPHY
        //https://media2.giphy.com/media/MuE7CqaehCJiZfL8wS/giphy.gif?cid=790b76117f47267955f3a3f2721b4ca2ca5cb07a26b757d4&rid=giphy.gif

        //dwarf image:
        //https://warhammerfantasy.fandom.com/wiki/Dwarf?file=Dwarfhead-0.png

        if(game.currentLocation.direction == "Exit")
            Glide.with(this).asGif().load(R.drawable.skeldance).into(GameImage)

        GameDescription.text = game.currentLocation.Description()
        GameImage.setImageResource(game.currentLocation.image)
    }

    private fun SetImageAnimation(){
        var counter:Short= 0
        val animationImages = (game.currentLocation as RoomWithEnemy).getEventImages()
        setButtonsVisibility()
        timer = object: CountDownTimer(2000, 500) {
            override fun onTick(millisUntilFinished: Long) {
                counter++
                if(counter == 2.toShort())
                    GameImage.setImageResource(animationImages[1])
                else if(counter == 3.toShort())
                    GameImage.setImageResource(animationImages[2])
            }

            override fun onFinish() {
                GameImage.setImageResource(animationImages[3])
                GameButtonGameOver.isVisible = true
            }
        }
        timer.start()
    }

    //--------------- buttons operations ---------------------------

    private fun setButtonsVisibility(){
        GameButtonForward.isVisible = !GameButtonForward.isVisible
        GameButtonLeft.isVisible = !GameButtonLeft.isVisible
        GameButtonRight.isVisible = !GameButtonRight.isVisible
    }

    private fun startOver(firstStart:Boolean){
        if(firstStart){
            gameIsStarted = true
            GameButtonGameOver.text = "Game Over"
        }
        timer.cancel()
        GameButtonGameOver.isVisible = false
        setButtonsVisibility()
        MoveToANewLocation(0)
    }

}
