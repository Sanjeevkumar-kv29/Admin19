package com.covid19.admin19

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_verification.*


class VerificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        if (intent != null) {
            txtName.text = intent.getStringExtra("name")
            txtCity.text = "${intent.getStringExtra("city")}, ${intent.getStringExtra("state")}"
            txtProvider.text = intent.getStringExtra("provider")
            txtAddress.text = intent.getStringExtra("address")
            txtContact.text = intent.getStringExtra("contact")

        }

        btnCall.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel: +91" + txtContact.text)
            startActivity(callIntent)
        }

        btnVerify.setOnClickListener {
            AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("Verification Prompt")
                .setMessage("You sure this reource is verified?")
                .setPositiveButton("Verify") { _, _ ->
                    modifythisResource(intent.getStringExtra("id").toString(), 1)
                }
                .setNegativeButton(
                    "Cancel"
                ) { _, _ -> }
                .show()

        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("Deletion Prompt")
                .setMessage("You wanna delete this resource?")
                .setNegativeButton(
                    "Cancel"
                ) { _, _ -> }
                .setPositiveButton("Delete") { _, _ ->
                    modifythisResource(intent.getStringExtra("id").toString(), 2)
                }
                .show()

        }
    }

    private fun modifythisResource(id: String, mode: Int) {
        val sharedPreferences = getSharedPreferences("admin", MODE_PRIVATE)
        val db = FirebaseFirestore.getInstance()
        val resoRef = db.collection("ADDED-RESOURCES").document(id)

        when (mode) {
            1 -> {
//                update verifiedBY field in document
                resoRef.update(
                    "verifiedBY",
                    "${
                        sharedPreferences.getString(
                            "Name",
                            "Anon"
                        )
                    } - (${sharedPreferences.getString("Phone", "-")})"
                )
                    .addOnSuccessListener {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
            }
            2 -> {
//                delete document
                resoRef.delete()
                    .addOnSuccessListener {
                        startActivity(Intent(this, MainActivity::class.java))
                    }
            }
        }
    }
}