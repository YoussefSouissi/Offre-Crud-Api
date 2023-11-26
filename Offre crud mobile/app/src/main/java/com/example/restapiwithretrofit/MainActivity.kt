package com.example.restapiwithretrofit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    lateinit var recyle: RecyclerView;
    lateinit var myadpter: offreAdapter;
    lateinit var infobtn: FloatingActionButton


    var offres: ArrayList<offre> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyle = findViewById(R.id.RecycleView);
        recyle.layoutManager = LinearLayoutManager(this);
        myadpter = offreAdapter(offres);
        recyle.adapter = myadpter;
        infobtn = findViewById(R.id.infoButton)
        fetchData()
        infobtn.setOnClickListener {
            val position = myadpter.GetSelectedItem();
            var offre = offres.get(position);
            val intent = Intent(this, detailsActivity::class.java);
            intent.putExtra("code", offre.code.toString());
            intent.putExtra("intitule", offre.intitule);
            intent.putExtra("specialite", offre.specialite)
            intent.putExtra("societe", offre.societe)
            intent.putExtra("nbpostes", offre.nbPostes.toString())
            intent.putExtra("pays", offre.pays.toString())
            startActivity(intent);


        }
    }


    fun Delete(v: View) {
        val position = myadpter.GetSelectedItem()

        if (position != RecyclerView.NO_POSITION) {
            val offerId = offres[position].code

            var alert = AlertDialog.Builder(this)
            alert.setTitle("Confirmation")
            alert.setMessage("Do You Wanna Delete ${offres[position].intitule}")
            alert.setPositiveButton("Ok") { _, _ ->

                CoroutineScope(Dispatchers.Main).launch {
                    try {

                        val response = ApiClient.apiService.deleteOffre(offerId)


                        if (response.isSuccessful) {

                            offres.removeAt(position)
                            myadpter.notifyItemRemoved(position)
                        } else {
                            Toast.makeText(this@MainActivity, "Failed to delete offer", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Handle exceptions, such as network issues
                        Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            alert.setNegativeButton("No") { _, _ -> }
            alert.show()

            Toast.makeText(this, position.toString(), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "No item selected", Toast.LENGTH_SHORT).show()
        }
    }


    fun Edit(v: View) {
        val position = myadpter.GetSelectedItem()
        if (position == RecyclerView.NO_POSITION) {
            Toast.makeText(this, "Offre Not Selected ", Toast.LENGTH_LONG).show()
        } else {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Update ${offres[position].intitule}")

            val dialogView = LayoutInflater.from(this).inflate(R.layout.update_offre, null)
            alert.setView(dialogView)

            val code = dialogView.findViewById<EditText>(R.id.code)
            code.setText(offres[position].code.toString())

            val intitule = dialogView.findViewById<EditText>(R.id.intitule)
            intitule.setText(offres[position].intitule)

            val specialite = dialogView.findViewById<EditText>(R.id.specialite)
            specialite.setText(offres[position].specialite)

            val societe = dialogView.findViewById<EditText>(R.id.societe)
            societe.setText(offres[position].societe)

            val nbpostes = dialogView.findViewById<EditText>(R.id.nbpostes)
            nbpostes.setText(offres[position].nbPostes.toString())

            val pays = dialogView.findViewById<EditText>(R.id.pays)
            pays.setText(offres[position].pays.toString())

            alert.setPositiveButton("Ok") { _, _ ->
                val updatedOffre = offre(
                    code.text.toString().toInt(),
                    intitule.text.toString(),
                    specialite.text.toString(),
                    societe.text.toString(),
                    nbpostes.text.toString().toInt(),
                    pays.text.toString(),
                    false
                )

                // Make the network request using Retrofit
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val response = ApiClient.apiService.updateOffre(offres[position].code, updatedOffre)
                        if (response.isSuccessful) {
                            // Update the local list with the updated data
                            offres[position] = response.body()!!
                            myadpter.notifyItemChanged(position)
                            Toast.makeText(this@MainActivity, "Offre Updated Successfully", Toast.LENGTH_SHORT).show()

                        } else {
                            // Handle error, e.g., show an error message
                            Toast.makeText(this@MainActivity, "Update failed", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Handle network exception, e.g., show an error message
                        Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            alert.setNegativeButton("No") { _, _ -> }
            alert.show()
        }
    }

    private fun fetchData() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            try {

                val responseOffres = ApiClient.apiService.getOffres()

                withContext(Dispatchers.Main) {
                    if (responseOffres.isSuccessful && responseOffres.body() != null) {
                        offres.addAll(responseOffres.body()!!)
                        myadpter.notifyDataSetChanged()
                    } else {
                        Log.e("Error", responseOffres.message())
                    }
                }

            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }

    }


    fun AddOffre(v: View) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.add_offre, null)

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setTitle("Add New Offre")

        alertDialogBuilder.setPositiveButton("Add") { _, _ ->
            val code = dialogView.findViewById<EditText>(R.id.editTextCode).text.toString().toInt()
            val Intitule = dialogView.findViewById<EditText>(R.id.editTextIntitule).text.toString()
            val Specialite = dialogView.findViewById<EditText>(R.id.editSpecialite).text.toString()
            val Societe = dialogView.findViewById<EditText>(R.id.editSociete).text.toString()
            val nbPostes = dialogView.findViewById<EditText>(R.id.editTextnbPostes).text.toString().toInt()
            val Pays = dialogView.findViewById<EditText>(R.id.editTextPays).text.toString()

            val newOffre = offre(code, Intitule, Specialite, Societe, nbPostes, Pays, false)

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = ApiClient.apiService.addOffre(newOffre)
                    if (response.isSuccessful) {
                        offres.add(newOffre)
                        myadpter.notifyItemInserted(offres.size - 1)
                    } else {
                        Toast.makeText(this@MainActivity, "Failed to add offer", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        alertDialogBuilder.show()
    }

}

