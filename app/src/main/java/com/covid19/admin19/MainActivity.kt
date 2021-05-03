package com.covid19.admin19

//import kotlinx.android.synthetic.main.fragment_home.*
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.covid19.admin19.Notification.FirebaseService
import com.covid19.admin19.adapters.ResourceAdapter
import com.covid19.admin19.dataClasses.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_home_main_screen.*
import java.util.*

const val TOPIC = "newresourceadded"

class MainActivity : AppCompatActivity() {


    /// sorts by timing
    var TimeSorter = Comparator<Resource> { b1, b2 ->
        b1.time.compareTo(b2.time, true)
    }

    //----------------------------------- substituted by one resource data class-----------------------------------------
//    private val resourceName = ArrayList<String>()
//    private val cityname = ArrayList<String>()
//    private val statename = ArrayList<String>()
//    private val providername = ArrayList<String>()
//    private val providercontact = ArrayList<String>()
//    private val provideraddress = ArrayList<String>()
//    private val verifiedby = ArrayList<String>()
//    private val moredetail = ArrayList<String>()
//    private val timeadded = ArrayList<String>()

    var resourceList = arrayListOf<Resource>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main_screen)
        val sharedPreferences = getSharedPreferences("admin", MODE_PRIVATE)

        val currentCity: String = sharedPreferences.getString("City", "").toString()

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        FirebaseService.sharedPref = this.getSharedPreferences("sharedPreftoken", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { FirebaseService.token = it.token   }


        getalldata()

    }



    private fun getalldata() {

        val db = FirebaseFirestore.getInstance()

//        resourceName.clear()
//        cityname.clear()
//        statename.clear()
//        providername.clear()
//        providercontact.clear()
//        provideraddress.clear()
//        verifiedby.clear()
//        timeadded.clear()
//        moredetail.clear()

        db.collection("ADDED-RESOURCES").addSnapshotListener { snapshot, e ->
            // if there is an exception we want to skip.
            if (e != null) {
                Log.w("Failed", "Listen Failed", e)
                return@addSnapshotListener
            }
            // if we are here, we did not encounter an exception
            if (snapshot != null) {
                // now, we have a populated shapshot

                val documents = snapshot.documents

                documents.forEach {

                    if (it.get("verifiedBY").toString() == "not") {

                        val resource = Resource(
                            it.id,
                            it.get("resource").toString(),
                            it.get("city").toString(),
                            it.get("State").toString(),
                            it.get("providername").toString(),
                            it.get("providercontact").toString(),
                            it.get("provideraddress").toString(),
                            it.get("verifiedBY").toString(),
                            it.get("time").toString(),
                            it.get("comment").toString()
                        )

                        resourceList.add(resource)

                    } else Log.i("state", it.get("city").toString(), e)

                }

                Collections.sort(resourceList, TimeSorter)


//                Log.w(
//                    "details",
//                    resourceName.count().toString() + cityname.count()
//                        .toString() + statename.count().toString() + providername.count()
//                        .toString() + providercontact.count().toString() + provideraddress.count()
//                        .toString(),
//                    e
//                )
//                Log.w(
//                    "details",
//                    verifiedby.count().toString() + timeadded.count()
//                        .toString() + moredetail.count().toString(),
//                    e
//                )
//
//
//                Log.w("cityfound", resourceName.toString(), e)


                if (resourceList.isEmpty()) {
                    Toast.makeText(
                        this,
                        "No data yolo",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    foorvadap()
                }

            }
        }


    }

    private fun foorvadap() {

        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val recyclerView: RecyclerView = mainRV
        recyclerView.layoutManager = layoutManager

        val adapter = ResourceAdapter(this, resourceList)

        recyclerView.adapter = adapter

    }


}

