package ng.hotels.telexlibraries

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import kotlinx.android.synthetic.main.activity_technical_problems.*


class TechnicalProblems : AppCompatActivity() {

    private val PICKFILE_RESULT_CODE: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_technical_problems)
        supportActionBar?.hide()

        val gottenIntent = intent.getStringExtra("ticketType")

        technical_problem_header.text = gottenIntent

        val urgency = ArrayList<String>()
        urgency.add("High")
        urgency.add("Moderate")
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
                TextUtils.isEmpty(problem_description.text) -> {
                    problem_description.error = "This field cannot be empty"
                }
                TextUtils.isEmpty(details_of_problem.text) -> {
                    details_of_problem.error = "This field cannot be empty"
                }
                else -> {

                    showDialog()

                }

            }


        }


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


    fun gettingPath(uri: Uri): String?{

        var path: String? = null
        val projection = { MediaStore.Files.FileColumns.DATA } as Array<out String>?
        val cursor = getContentResolver().query(uri,
            projection, null, null, null);

        if(cursor == null){
            path = uri.path
        }
        else{
            cursor.moveToFirst();
            val column_index = cursor.getColumnIndexOrThrow(projection?.get(0));
            path = cursor.getString(column_index);
            cursor.close();
        }

        if(path == null || path.isEmpty()) return uri.path else return  path

    }


    private fun showDialog(){
        MaterialDialog(this).show {
            cornerRadius(5F)
            customView(R.layout.item_successful)

        }

    }

}
