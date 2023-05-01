package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.tictactoe.databinding.ActivityCodeGeneratorBinding
import com.google.firebase.database.DataSnapshot

var isCodeMaker = true
var code = "null"
var codeFound = false
var checkTemp = true
var keyValue : String = "null"

class code_generator : AppCompatActivity() {
    private lateinit var binding: ActivityCodeGeneratorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCodeGeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pb.visibility=View.GONE

        binding.create.setOnClickListener {
            code=binding.codeId.text.toString()
            binding.pb.visibility=View.VISIBLE
        }

    }

    fun accepted()
    {
        startActivity(Intent(this@code_generator,online_playground::class.java))
    }

    fun isCodeavaliable(snapshot: DataSnapshot,code : String):Boolean{
        val data=snapshot.children
        data.forEach{
            var value=it.getValue().toString()
            if(value == code)
            {
                keyValue=it.key.toString()
                return true
            }
        }
        return false
    }

    fun create(view: View) {}

    fun join(view: View) {}
}