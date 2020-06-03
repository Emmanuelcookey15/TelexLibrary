package ng.hotels.telexlibraries

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_technical_problems.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class TechnicalProblems : AppCompatActivity() {

    private val PICKFILE_RESULT_CODE: Int = 101

    var mApiService: TelexAPI? = null

    var fullname = ""

    var userEmail = ""

    var problemTitle = ""

    var problemDetails = ""

    var priority = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_technical_problems)
        supportActionBar?.hide()

        subscribeIntializer()


        val organiztion = TelexManager.organizations
        val team = TelexManager.teams

        val gottenIntent = intent.getStringExtra("ticketType")

        technical_problem_header.text = gottenIntent

        val urgency = ArrayList<String>()
        urgency.add("High")
        urgency.add("Normal")
        urgency.add("Low")

        // Creating adapter for spinner
        val dataAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, urgency)

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        urgency_spinner.adapter = dataAdapter

        urgency_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {


            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item = p0!!.getItemAtPosition(p2).toString()

                when(item){
                    "High" -> priority = "3"
                    "Normal" -> priority = "2"
                    "Low" -> priority = "1"
                }

            }

        }

        choose_file.setOnClickListener {
            var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
            chooseFile.type = "*/*"
            chooseFile = Intent.createChooser(chooseFile, "Choose a file")
            startActivityForResult(chooseFile, PICKFILE_RESULT_CODE)
        }

        send_message.setOnClickListener {

            when {
                TextUtils.isEmpty(user_first_name.text) -> {
                    user_first_name.error = "This field cannot be empty"
                }

                TextUtils.isEmpty(user_last_name.text) -> {
                    user_last_name.error = "This field cannot be empty"
                }

                TextUtils.isEmpty(user_email.text) -> {
                    user_email.error = "This field cannot be empty"
                }

                TextUtils.isEmpty(problem_title.text) -> {
                    problem_title.error = "This field cannot be empty"
                }
                TextUtils.isEmpty(details_of_problem.text) -> {
                    details_of_problem.error = "This field cannot be empty"
                }
                else -> {

                    fullname = user_first_name.text.toString() + " " + user_last_name.text.toString()

                    userEmail = user_email.text.toString()

                    problemTitle = problem_title.text.toString()

                    problemDetails = details_of_problem.text.toString()

                    if (isInternetAvailable(this)) {
                        send_message.loadState(0, "Please wait ...")
                        postRequest(
                            organiztion,
                            team,
                            userEmail,
                            fullname,
                            problemTitle,
                            problemDetails,
                            priority
                        )
                    }else{
                        Snackbar.make(it, "No internet connection",
                            Snackbar.LENGTH_LONG).show()
                    }


                }

            }
        }


    }


    private fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }


    private fun subscribeIntializer(){
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val request: Request =
                    chain.request().newBuilder().build()
                return chain.proceed(request)
            }
        })

        val retrofit = Retrofit.Builder()
            .baseUrl("https://teamwork.telex.im/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

        mApiService = retrofit.create<TelexAPI>(TelexAPI::class.java)
    }





    private fun postRequest(organization: String, team:String, email:String,
                            name: String, title:String, description:String, priorties:String){

        val call: Call<JsonObject> = mApiService!!.postTickets(organization, team,
            email, name, title, description, priorties)

        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("CONFIRM", "Failed")

                send_message.loadState(1, "Send Message")
                showNormalDialog("Error", "Something went wrong, please try again later")
            }


            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                if(!response.isSuccessful){
                    Log.d("CONFIRM", "Unsuccessful: " + response.message() + " ${organization} ${team}" +
                            " ${email} ${priorties} ${response.code()}")

                    send_message.loadState(1, "Send Message")
                    showNormalDialog(response.message(), "Your Submission was unsuccessful, Please Try again")


                }

                if(response.isSuccessful){
                    Log.d("CONFIRM", "Successful: " + response.message())

                    val getting = response.body()
                    send_message.loadState(1, "Send Message")

                    showDialog()
                }

            }
        })

    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            val uri = data?.data
            val src = uri?.path

            if (src != null) {
                choose_file_txt.text = src
            }
        }
    }


    private fun showDialog(){
        MaterialDialog(this).show {
            cornerRadius(5F)
            customView(R.layout.item_successful)

        }

    }


    private fun showNormalDialog(title:String, message:String){
        MaterialDialog(this).show {
            cornerRadius(5F)
            title(text = title)
            message(text = message)

            positiveButton(R.string.agree) {
                hide()
            }

            negativeButton {  }

        }

    }

    private fun Button.loadState(state:Int, message: String){
        this.apply {
            when(state){
                0->{
                    alpha=0.6f
                    text=message
                    isClickable=false
                }
                1->{
                    alpha=1.0f
                    text=message
                    isClickable=true
                }
            }

        }
    }

}
