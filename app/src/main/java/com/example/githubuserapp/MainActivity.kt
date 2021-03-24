package com.example.githubuserapp

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.transition.Fade
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.connection.ApiService
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.databinding.DialogSwitchSettingBinding
import com.example.githubuserapp.model.User
import com.example.githubuserapp.notification.Reminder
import com.example.githubuserapp.viewmodel.UserViewModel
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {
    private var list: ArrayList<User> = arrayListOf()
    private lateinit var adapter: UserAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingModal: DialogSwitchSettingBinding
    private val model: UserViewModel by viewModels()
    private var found = false
    private lateinit var reminder: Reminder
    lateinit var popupWindow: PopupWindow
    lateinit var viewModal: View


    val apiService by lazy {
        ApiService.create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingModal = DialogSwitchSettingBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)
        showData()
        showProgress(false)
        showOpening(true)
        reminder = Reminder()

        viewModal = bindingModal.root
        popupWindow = PopupWindow(
                viewModal,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun openSetting(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val fadeIn = Fade()
            popupWindow.enterTransition = fadeIn
            val fadeOut= Fade()
            popupWindow.exitTransition = fadeOut
        }

        bindingModal.btnSave.setOnClickListener {
            if (bindingModal.switchButton.isChecked){
                reminder.setRepeatingAlarm(this,"Kembalilah Cek Aplikasimu ")
                toast("Aktifkan Alarm")
            } else {
                reminder.cancelAlarm(this)
                toast("Mematikan Alarm")
            }
            popupWindow.dismiss()
        }
        TransitionManager.beginDelayedTransition(bindingModal.root)
        popupWindow.showAtLocation(
                bindingModal.root,
                Gravity.CENTER,
                0,
                0
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val search = menu?.findItem(R.id.appSearchBar)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Find User"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    model.searchUser(apiService, query)
                    model.resultSearch.observe({ lifecycle }, {
                        if (it.result) {
                            list.clear()
                            list.addAll(it.items)
                            found = false
                            if (it.total_count > 0) found = true
                            adapter.notifyDataSetChanged()
                            showProgress(false)
                        } else {
                            toast(it.message)
                        }
                    })
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    showOpening(true)
                    showProgress(false)
                    found = false
                } else {
                    showOpening(false)
                    if (!found) showProgress(true)
                }
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.localization){
            val mIntent = Intent(ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        if (item.itemId == R.id.favourite){
            startActivity<FavouriteActivity>()
        }
        if (item.itemId == R.id.alarm){
            openSetting()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showData(){
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(list,this)
        binding.rvUser.adapter = adapter
        if (found) binding.rvUser.visibility = View.VISIBLE else binding.rvUser.visibility = View.GONE
    }



    private fun showOpening(show: Boolean){
        if (!show){
            binding.textView.visibility = View.GONE
            binding.imageView.visibility = View.GONE
        } else {
            binding.textView.visibility = View.VISIBLE
            binding.imageView.visibility = View.VISIBLE
        }
    }

    private fun showProgress(show: Boolean) {
        binding.userLoad.bringToFront()
        binding.userLoad.visibility = if (show) View.VISIBLE else View.GONE
        binding.rvUser.visibility = if(show || !found) View.GONE else View.VISIBLE
        binding.textEmptyUser.visibility = if (show || found) View.GONE else View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
    }

}