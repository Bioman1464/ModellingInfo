package bio.models.three_d.main_menu.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import bio.models.three_d.MainMenuActivity
import bio.models.three_d.R
import bio.models.three_d.common.ImageDownloadTask
import bio.models.three_d.common.UserAccount
import bio.models.three_d.common.firebase.data.FirebaseDataHelper
import bio.models.three_d.common.firebase.user.FirebaseUserHelper
import bio.models.three_d.databinding.FragmentSettingBinding
import bio.models.three_d.Language
import bio.models.three_d.common.shared_preferences.*
import bio.models.three_d.main_menu.common.article.ArticleData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.yariksoffice.lingver.Lingver


class SettingFragment : Fragment(R.layout.fragment_setting) {

    private val TAG = this::class.java.simpleName
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSettingBinding
    private lateinit var themeSharedPrefs: ThemeSharedPrefs
    private lateinit var languageSharedPrefs: LanguageSharedPrefs
    private lateinit var fontSizeSharedPrefs: FontSharedPrefs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingBinding.bind(view)
        themeSharedPrefs = ThemeSharedPrefs.getInstance(requireContext())
        languageSharedPrefs = LanguageSharedPrefs.getInstance(requireContext())
        fontSizeSharedPrefs = FontSharedPrefs.getInstance(requireContext())
        auth = FirebaseAuth.getInstance()
        setSettingsConfiguration()
        setClickListeners()
        configureUserData()
    }

    private fun setSettingsConfiguration() {
        setFontSizeRadio()
        setThemeRadio()
        setLanguageRadio()
    }

    private fun setThemeRadio() {
        when (themeSharedPrefs.getTheme()) {
            0 -> binding.radioDayTheme.isChecked = true
            1 -> binding.radioNightTheme.isChecked = true
        }
    }

    private fun setFontSizeRadio() {
        when (fontSizeSharedPrefs.getFontSizeKey()) {
            FontSize.SMALL -> binding.radioSizeSmall.isChecked = true
            FontSize.MEDIUM -> binding.radioSizeMedium.isChecked = true
            FontSize.BIG -> binding.radioSizeBig.isChecked = true
        }
    }

    private fun setLanguageRadio() {
        when(languageSharedPrefs.getLanguage()) {
            Language.LANGUAGE_RUSSIAN -> binding.radioRussian.isChecked = true
            Language.LANGUAGE_ENGLISH -> binding.radioEnglish.isChecked = true
        }
    }

    private fun setClickListeners() {
        setFontSizeRadioListener()
        setThemeColorRadioListener()
        setLanguageRadioListener()
        setProfileListener()
    }

    private fun setFontSizeRadioListener() {
        binding.textSizeRadioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.radioSizeSmall.id -> fontSizeSharedPrefs.setFontSize(FontSize.SMALL)
                binding.radioSizeMedium.id -> fontSizeSharedPrefs.setFontSize(FontSize.MEDIUM)
                binding.radioSizeBig.id -> fontSizeSharedPrefs.setFontSize(FontSize.BIG)
            }
        }
    }

    private fun setThemeColorRadioListener() {
        binding.colorThemeRadioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.radioDayTheme.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    themeSharedPrefs.setTheme(0)
                }
                binding.radioNightTheme.id -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    themeSharedPrefs.setTheme(1)
                }
            }
        }
    }

    private fun setLanguageRadioListener() {
        binding.languageRadioGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.radioRussian.id -> {
                    setNewLocale(Language.LANGUAGE_RUSSIAN, Language.RUSSIAN_COUNTRY)
                }
                binding.radioEnglish.id -> {
                    setNewLocale(Language.LANGUAGE_ENGLISH, Language.ENGLISH_COUNTRY)
                }
            }
        }
    }

    private fun setProfileListener() {
        binding.profileLayout.setOnClickListener {
            if (UserAccount.uid.isEmpty()) {
                logInUser()
                return@setOnClickListener
            }
            logOutUser()
        }
    }

    private fun configureUserData() {
        if (UserAccount.uid.isEmpty()) {
            return
        }
        Log.d(TAG, "User is empty")
        updateUiAccountInfo(UserAccount.photoUrl, UserAccount.name)
    }

    private fun logInUser() {
        if (checkIfLoggedIn()) {
            updateUiAccountInfo(UserAccount.photoUrl, UserAccount.name)
            return
        }
        val signInIntent = FirebaseDataHelper.getSignInIntent(requireContext())
        signInIntent?.let {
            startActivityForResult(it, 1)
            return
        }
        Toast.makeText(
            requireContext(),
            getString(R.string.service_is_not_responding),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun checkIfLoggedIn(): Boolean {
        auth.currentUser ?: return false
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            account?.let {
                updateUiAccountInfo(it.photoUrl, it.displayName)
                firebaseAuthWithGoogle(it.idToken.toString())
            }
        } catch (e: ApiException) {
            Log.d(TAG, "Error while google sign in: CODE: ${e.statusCode} MESSAGE: ${e.message}")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        if (idToken.isEmpty()) {
            return
        }
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        UserAccount.run {
                            uid = it.uid
                            name = it.displayName
                            photoUrl = it.photoUrl
                        }
                        updateLocalStorage(it.uid)
                    }

                    Log.d(TAG, "Successful sign-in through firebase")
                    return@addOnCompleteListener
                }
                Log.w(TAG, "signInWithCredential:failure", it.exception)
            }
    }

    private fun logOutUser() {
        auth.signOut()
        UserAccount.clearData()
        FirebaseUserHelper.logOutClient(requireContext())
        updateUiAccountInfo(null, null)
        ArticleSharedPrefs.getInstance(requireContext()).clearData()
    }

    private fun updateLocalStorage(uid: String) {
        FirebaseDataHelper.getUserFavouriteArticles(uid, requireContext())
    }

    private fun updateUiAccountInfo(userImageUrl: Uri?, userName: String?) {
        if (userImageUrl != null) {
            ImageDownloadTask(binding.googleUserImage).execute(userImageUrl.toString())
            binding.googleButton.text = requireContext().resources.getString(R.string.logOut)

        } else {
            binding.googleUserImage.setImageResource(R.drawable.ic_google)
            binding.googleButton.text = requireContext().resources.getString(R.string.logIn)
        }
        binding.userNickname.text = userName ?: getString(R.string.nickname)
    }

    private fun setNewLocale(language: String, country: String) {
        languageSharedPrefs.setLanguage(language)
        Lingver.getInstance().setLocale(requireContext(), language, country)
        ArticleData.recreateList(requireContext())
        restart()
    }

    private fun restart() {
        val i = Intent(requireContext(), MainMenuActivity::class.java)
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}