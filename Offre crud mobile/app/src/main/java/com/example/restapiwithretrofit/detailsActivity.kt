package com.example.restapiwithretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class detailsActivity : AppCompatActivity() {
    lateinit var btn_return: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.offre_details)

        val code = intent.getStringExtra("code");
        val intitule = intent.getStringExtra("intitule")
        val specialite = intent.getStringExtra("specialite");
        val societe = intent.getStringExtra("societe");
        val nbpostes = intent.getStringExtra("nbpostes");
        val pays = intent.getStringExtra("pays");

        findViewById<TextView?>(R.id.code).setText(code)
        findViewById<TextView?>(R.id.intitule).setText(intitule)
        findViewById<TextView?>(R.id.specialite).setText(specialite)
        findViewById<TextView?>(R.id.societe).setText(societe)
        findViewById<TextView?>(R.id.nbpostes).setText(nbpostes)
        findViewById<TextView?>(R.id.pays).setText(pays)
    }
    fun navigate(v: View) {

        val intent = Intent(this, MainActivity::class.java);

        startActivity(intent);
    }
}


