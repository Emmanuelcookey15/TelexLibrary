package ng.hotels.telexlibraries

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_support.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SupportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SupportFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var tal = activity as (AppCompatActivity)

        tal.supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_support, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        goto_knowledge_base.setOnClickListener {
            val intentKnowledgeBase = Intent(activity, KnowledgeBase::class.java)
            startActivity(intentKnowledgeBase)
            activity?.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }

        remain_open_ticket.setOnClickListener {
            onSNACK(it, "Already in selected page")
        }

        general_enquiry.setOnClickListener {
            val intentTechnicalProblems = Intent(activity, TechnicalProblems::class.java)
            intentTechnicalProblems.putExtra("ticketType", "General Enquiry")
            startActivity(intentTechnicalProblems)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        technical_problem.setOnClickListener {
            val intentTechnicalProblems = Intent(activity, TechnicalProblems::class.java)
            intentTechnicalProblems.putExtra("ticketType", "Technical Problem")
            startActivity(intentTechnicalProblems)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        sales_problem.setOnClickListener {
            val intentTechnicalProblems = Intent(activity, TechnicalProblems::class.java)
            intentTechnicalProblems.putExtra("ticketType", "Sales Problem")
            startActivity(intentTechnicalProblems)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }


    private fun onSNACK(view: View, str: String){
        //Snackbar(view)
        val snackbar = Snackbar.make(view, str,
            Snackbar.LENGTH_LONG).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.LTGRAY)
        val textView =
            snackbarView.findViewById(R.id.snackbar_text) as TextView
        textView.setTextColor(Color.BLUE)
        textView.textSize = 28f
        snackbar.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SupportFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SupportFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
