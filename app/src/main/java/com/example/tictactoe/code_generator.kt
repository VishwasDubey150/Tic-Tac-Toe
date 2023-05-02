package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.tictactoe.databinding.ActivityCodeGeneratorBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

            if(code!="null" && code!="")
            {
                isCodeMaker=true
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot)
                    {
                        var check=isCodeavaliable(snapshot, code)
                        Handler().postDelayed({
                            if(check==true)
                            {
                                binding.pb.visibility=View.GONE
                            }
                            else
                            {
                                FirebaseDatabase.getInstance().reference.child("codes").push().setValue(
                                    code)
                                isCodeavaliable(snapshot, code)

                                checkTemp=false
                                Handler().postDelayed({
                                    accepted()
                                    Toast.makeText(this@code_generator,"Don't go back!!",Toast.LENGTH_SHORT).show()
                                },300)
                                }
                        },2000)
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
            }
            else{
                binding.pb.visibility=View.GONE
                Toast.makeText(this,"Please enter a code",Toast.LENGTH_SHORT).show()
            }
        }

        binding.join.setOnClickListener {
            code=binding.codeId.text.toString()

            if(code!="null" && code!="")
            {
                isCodeMaker=false
                FirebaseDatabase.getInstance().reference.child("codes").addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot)
                    {
                        var data=isCodeavaliable(snapshot, code)
                        Handler().postDelayed({
                            if(data==true)
                            {
                                codeFound=true
                                accepted()
                                 binding.pb.visibility=View.GONE
                            }
                            else
                            {
                                binding.pb.visibility=View.GONE
                                Toast.makeText(this@code_generator,"Invalid Code",Toast.LENGTH_SHORT).show()
                            }
                        },2000)
                    }
                    override fun onCancelled(error: DatabaseError) {
                    }
                })
            }
            else{
                Toast.makeText(this,"Please enter a code",Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun accepted()
    {
        val intent=Intent(this,online_playground::class.java)
        startActivity(intent)
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
}