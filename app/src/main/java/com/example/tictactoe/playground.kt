package com.example.tictactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.databinding.ActivityMainBinding
import com.example.tictactoe.databinding.ActivityPlaygroundBinding

var playerturn=true
class playground : AppCompatActivity() {

    private lateinit var binding: ActivityPlaygroundBinding
    var player1count = 0
    var player2count = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaygroundBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.reset.setOnClickListener {
            reset()
        }
    }

    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()

        activeUser = 1

        for (i in 1..9) {
            var selectedbutton: Button?
            selectedbutton = when (i) {
                1 -> binding.btn1
                2 -> binding.btn2
                3 -> binding.btn3
                4 -> binding.btn4
                5 -> binding.btn5
                6 -> binding.btn6
                7 -> binding.btn7
                8 -> binding.btn8
                9 -> binding.btn9

                else -> {
                    binding.btn1
                }
            }
            selectedbutton.isEnabled = true
            selectedbutton.text = ""
        }
    }

    fun buttonclick(view: View) {
        if (playerturn) {
            val but = view as Button
            var cellid = 0
            when (but.id) {
                R.id.btn1 -> cellid = 1
                R.id.btn2 -> cellid = 2
                R.id.btn3 -> cellid = 3
                R.id.btn4 -> cellid = 4
                R.id.btn5 -> cellid = 5
                R.id.btn6 -> cellid = 6
                R.id.btn7 -> cellid = 7
                R.id.btn8 -> cellid = 8
                R.id.btn9 -> cellid = 9
            }

            playerturn = false
            Handler().postDelayed(Runnable { playerturn = true }, 500)
            playNow(but, cellid)

        }
    }

    private fun playNow(buttonSelected: Button, currcell: Int) {
        if (activeUser == 1) {
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#000000"))
            player1.add(currcell)
            emptyCells.add(currcell)
            buttonSelected.isEnabled = false

            Handler().postDelayed(Runnable { }, 200)

            val checkWinnner = checkWinner()
            if (checkWinnner == 1) {
                Handler().postDelayed(Runnable { reset() }, 2000)
            } else if (singleplayer) {
                Handler().postDelayed(Runnable { robot() }, 500)
            } else {
                activeUser = 0
            }
        } else {
            buttonSelected.text = "0"
            buttonSelected.setTextColor(Color.parseColor("#000000"))
            player2.add(currcell)
            emptyCells.add(currcell)
            buttonSelected.isEnabled = false

            Handler().postDelayed(Runnable { }, 200)

            val checkWinnner = checkWinner()
            if (checkWinnner == 1) {
                Handler().postDelayed(Runnable { reset() }, 2000)
            }
            else {
                activeUser = 1
            }
        }
    }

    private fun robot() {
        TODO("Not yet implemented")
    }

    private fun checkWinner(): Int {
        if ((player1.contains(1) && player1.contains(2) && player1.contains(3)) ||
            (player1.contains(4) && player1.contains(5) && player1.contains(6)) ||
            (player1.contains(7) && player1.contains(8) && player1.contains(9)) ||
            (player1.contains(1) && player1.contains(4) && player1.contains(7)) ||
            (player1.contains(2) && player1.contains(5) && player1.contains(8)) ||
            (player1.contains(3) && player1.contains(6) && player1.contains(9)) ||
            (player1.contains(1) && player1.contains(5) && player1.contains(9)) ||
            (player1.contains(7) && player1.contains(5) && player1.contains(3))
        ) {
            player1count += 1
            buttonDisabled()
            disablereset()

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 1 wins \n\n" + "Do you want to play again")
            build.setPositiveButton("Yup") { dialog, which ->
                reset()
            }

            build.setNegativeButton("Nope") { dialog, which ->
                quit()
            }
            build.show()
            return 1
        }
        if ((player2.contains(1) && player2.contains(2) && player2.contains(3)) ||
            (player2.contains(4) && player2.contains(5) && player2.contains(6)) ||
            (player2.contains(7) && player2.contains(8) && player2.contains(9)) ||
            (player2.contains(1) && player2.contains(4) && player2.contains(7)) ||
            (player2.contains(2) && player2.contains(5) && player2.contains(8)) ||
            (player2.contains(3) && player2.contains(6) && player2.contains(9)) ||
            (player2.contains(1) && player2.contains(5) && player2.contains(9)) ||
            (player2.contains(7) && player2.contains(5) && player2.contains(3))
        ) {
            player2count += 1
            buttonDisabled()
            disablereset()

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Player 2 wins \n\n" + "Do you want to play again")
            build.setPositiveButton("Yup") { dialog, which ->
                reset()
            }

            build.setNegativeButton("Nope") { dialog, which ->
                quit()
            }
            build.show()
            return 1
        }

        else if(emptyCells.contains(1)&&emptyCells.contains(2)&&emptyCells.contains(3)&&emptyCells.contains(4)&&emptyCells.contains(5)&&
            emptyCells.contains(9)&&emptyCells.contains(8)&&emptyCells.contains(7)&&emptyCells.contains(6))
        {
            buttonDisabled()
            disablereset()

            val build = AlertDialog.Builder(this)
            build.setTitle("Game Over")
            build.setMessage("Game Draw \n\n" + "Do you want to play again")
            build.setPositiveButton("Yup") { dialog, which ->
                reset()
            }

            build.setNegativeButton("Nope") { dialog, which ->
                quit()
            }
            build.show()
            return 1;
        }
        return 0;

    }

    private fun quit() {

    }

    private fun disablereset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser=1

        for(i in 1..9)
        {
            var buttonSelected:Button?
            buttonSelected=when(i)
            {
                1 -> binding.btn1
                2 -> binding.btn2
                3 -> binding.btn3
                4 -> binding.btn4
                5 -> binding.btn5
                6 -> binding.btn6
                7 -> binding.btn7
                8 -> binding.btn8
                9 -> binding.btn9

                else
                    ->
                {
                    binding.btn1
                }

            }
        }
    }

    private fun buttonDisabled() {

    }
}