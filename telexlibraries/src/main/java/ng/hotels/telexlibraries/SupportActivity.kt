package ng.hotels.telexlibraries

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_support.*

class SupportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_support)


        goto_knowledge_base.setOnClickListener {
            val intentKnowledgeBase = Intent(this, KnowledgeBase::class.java)
            startActivity(intentKnowledgeBase)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            //onSNACK(it, "This feature is not yet available")
        }

        remain_open_ticket.setOnClickListener {
            onSNACK(it, "Already in selected page")
        }


        general_enquiry.setOnClickListener {
            val intentTechnicalProblems = Intent(this, TechnicalProblems::class.java)
            intentTechnicalProblems.putExtra("ticketType", "General Enquiry")
            startActivity(intentTechnicalProblems)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        technical_problem.setOnClickListener {
            val intentTechnicalProblems = Intent(this, TechnicalProblems::class.java)
            intentTechnicalProblems.putExtra("ticketType", "Technical Problem")
            startActivity(intentTechnicalProblems)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        sales_problem.setOnClickListener {
            val intentTechnicalProblems = Intent(this, TechnicalProblems::class.java)
            intentTechnicalProblems.putExtra("ticketType", "Sales Problem")
            startActivity(intentTechnicalProblems)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }



    private fun onSNACK(view: View, str: String){
        //Snackbar(view)
        Snackbar.make(view, str, Snackbar.LENGTH_LONG).show()
    }

}
