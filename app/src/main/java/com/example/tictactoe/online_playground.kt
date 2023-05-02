package com.example.tictactoe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.tictactoe.databinding.ActivityOnlinePlaygroundBinding
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import java.nio.file.AtomicMoveNotSupportedException

var ismymove= isCodeMaker
class online_playground : AppCompatActivity() {
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    lateinit var binding: ActivityOnlinePlaygroundBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlinePlaygroundBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.reset.setOnClickListener {
            reset()
        }

        FirebaseDatabase.getInstance().reference.child("data").addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var data=snapshot.value

                if(ismymove==true)
                    moveOnline(data.toString(), ismymove)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                reset()
                Toast.makeText(this@online_playground,"Game Reset",Toast.LENGTH_SHORT).show()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun moveOnline(data: String, move:Boolean ) {


        if(move)
        {
            var btnselected:Button?
            btnselected=when(data.toInt())
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

                else -> {
                    binding.btn1
                }
            }

            btnselected.text="0"
            btnselected.setTextColor(Color.parseColor("#000000"))
            player2.add(data.toInt())
            emptyCells.add(data.toInt())

            btnselected.isEnabled=false
            checkWinner()
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
            ismymove= isCodeMaker
            if (isCodeMaker)
                FirebaseDatabase.getInstance().reference.child("data").child(code).removeValue()
        }
    }

    fun buttonclick(view: View) {
        if (ismymove) {
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
            Handler().postDelayed(Runnable { playerturn = true }, 0)
            playNow(but, cellid)
            updateDatabase(cellid)
        }
    }

    private fun playNow(buttonSelected: Button, currcell: Int) {
        if (activeUser == 1) {
            binding.arr2.visibility = View.VISIBLE
            binding.arr1.visibility = View.INVISIBLE
            buttonSelected.text = "X"
            buttonSelected.setTextColor(Color.parseColor("#000000"))
            player1.add(currcell)
            emptyCells.add(currcell)
            buttonSelected.isEnabled = false

            Handler().postDelayed(Runnable { }, 0)

            val checkWinnner = checkWinner()
            if (checkWinnner == 1) {
                Handler().postDelayed(Runnable { reset() }, 2000)
            } else {
                activeUser = 0
            }
        } else {
            binding.arr1.visibility = View.VISIBLE
            binding.arr2.visibility = View.INVISIBLE
            buttonSelected.text = "0"
            buttonSelected.setTextColor(Color.parseColor("#000000"))
            player2.add(currcell)
            emptyCells.add(currcell)
            buttonSelected.isEnabled = false

            Handler().postDelayed(Runnable { }, 200)

            val checkWinnner = checkWinner()
            if (checkWinnner == 1) {
                Handler().postDelayed(Runnable { reset() }, 2000)
            } else {
                activeUser = 1
            }
        }
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
        } else if (emptyCells.contains(1) && emptyCells.contains(2) && emptyCells.contains(3) && emptyCells.contains(
                4) && emptyCells.contains(5) &&
            emptyCells.contains(9) && emptyCells.contains(8) && emptyCells.contains(7) && emptyCells.contains(
                6)
        ) {

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
        startActivity(Intent(this@online_playground, MainActivity::class.java))
        finish()
    }

    fun updateDatabase(cellId:Int)
    {
        FirebaseDatabase.getInstance().reference.child("data").child(code).push().setValue(cellId)
    }
}





