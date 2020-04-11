package longbowou.getnovissi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import longbowou.getnovissi.R

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NovissisFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_novissis, container, false)
    }

}
