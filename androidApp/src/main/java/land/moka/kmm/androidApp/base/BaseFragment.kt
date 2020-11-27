package land.moka.kmm.androidApp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import land.moka.kmm.shared.lib.log.Log

open class BaseFragment : Fragment() {

    private val TAG = javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.print("$TAG onCreateView is called 🐳")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Log.print("$TAG onResume is called 🐳")
    }

    override fun onStop() {
        super.onStop()
        Log.print("$TAG onStop is called 🐳")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.print("$TAG onDestroyView is called 🐳")
    }

}
