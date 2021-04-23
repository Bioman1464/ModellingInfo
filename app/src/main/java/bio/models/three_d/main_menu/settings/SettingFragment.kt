package bio.models.three_d.main_menu.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import bio.models.three_d.R
import bio.models.three_d.databinding.FragmentSettingBinding

class SettingFragment : Fragment(R.layout.fragment_setting){

    private lateinit var binding: FragmentSettingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

    }
}