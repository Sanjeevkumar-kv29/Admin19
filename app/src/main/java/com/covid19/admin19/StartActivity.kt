package com.covid19.admin19

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.layout_startup.*
import java.util.*


//Runs only for first time, once the user enters his details, the next time this is saved in his shardprefs
// also he will be logged into firebaseAuth.
class StartActivity : AppCompatActivity() {

    val addAdmin = HashMap<String, Any>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_startup)

        val sharedpref = getSharedPreferences("admin", Context.MODE_PRIVATE)

        Log.d("Activity start starts", "onCreate: running")

        btn_proceed.setOnClickListener {
            if (et_name.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Name necessary", Toast.LENGTH_SHORT).show()

            } else {

                if (et_phone.text.toString().isNullOrEmpty()) {
                    Toast.makeText(this, "Phone necessary", Toast.LENGTH_SHORT).show()
                } else {

                    if (et_city.text.toString().isNullOrEmpty()) {
                        Toast.makeText(
                            this,
                            "City necessary",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {

                        sharedpref.edit().putString("City", et_city.text.toString())
                            .putString("Name", et_name.text.toString())
                            .putString("Phone", et_phone.text.toString()).apply()

                        if (!et_mail.text.toString().isNullOrEmpty()) sharedpref.edit()
                            .putString("Email", et_mail.text.toString()).apply()

                        addAdmin["Phone"] = et_phone.text.toString()
                        addAdmin["Name"] = et_name.text.toString()
                        addAdmin["City"] = et_city.text.toString()
                        addAdmin["Mail"] = et_mail.text.toString()

                        addAdminToDB(addAdmin)


                    }
                }
            }
        }

    }

    private fun addAdminToDB(Admin: HashMap<String, Any>) {

        val db = FirebaseFirestore.getInstance()
        db.collection("ADMIN-LIST").document(Admin["Name"].toString()).set(Admin)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Success",
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, MainActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error: $it",
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
    }
}
