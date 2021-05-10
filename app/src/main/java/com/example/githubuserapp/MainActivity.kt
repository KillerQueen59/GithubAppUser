package com.example.githubuserapp

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.adapter.UserAdapter
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
    lateinit var popupWindow: PopupWindow
    lateinit var viewModal: View


    val apiService by lazy {
        ApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bindingModal = DialogSwitchSettingBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)
        showData()
        onClick()
        showProgress(false)
        showOpening(true)

        viewModal = bindingModal.root
        popupWindow = PopupWindow(
                viewModal,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        )

    }

    private fun onClick() {
        binding.btnFavouriteFloat.setOnClickListener {
            startActivity<FavouriteActivity>()
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.setting){
            startActivity<SettingActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showData(){
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = UserAdapter(list)
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
        model.onPaused()
    }

}