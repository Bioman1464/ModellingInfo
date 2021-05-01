package bio.models.three_d.main_menu.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import bio.models.three_d.R
import bio.models.three_d.common.ThemeSharedPrefs
import bio.models.three_d.databinding.FragmentSettingBinding

class SettingFragment : Fragment(R.layout.fragment_setting){

    private lateinit var binding: FragmentSettingBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)

        val themeId = ThemeSharedPrefs.getInstance(requireContext())
        when (themeId.getTheme()) {
            0 -> { binding.radioDayTheme.isChecked = true }
            1 -> { binding.radioNightTheme.isChecked = true }
        }

        binding.colorThemeRadioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.radioDayTheme.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    themeId.setTheme(0)
                    return@setOnCheckedChangeListener
                }
                binding.radioNightTheme.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    themeId.setTheme(1)
                    return@setOnCheckedChangeListener
                }
            }
        }
    }
}