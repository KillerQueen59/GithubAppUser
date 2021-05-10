package com.example.githubuserapp


import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuserapp.databinding.ActivitySettingBinding
import com.example.githubuserapp.notification.Reminder
import org.jetbrains.anko.toast


class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminder: Reminder
    private lateinit var sessionManagement: SessionManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sessionManagement = SessionManagement(this)
        initState()
        onClick()
        reminder = Reminder()

    }

    private fun initState() {
        binding.switchButton.isChecked = sessionManagement.notif
    }

    private fun onClick() {
        binding.changeLanguange.setOnClickListener {
            val mIntent = Intent(ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        binding.btnSave.setOnClickListener {
            if (binding.switchButton.isChecked){
                reminder.setRepeatingAlarm(this,"Kembalilah Cek Aplikasimu ")
                sessionManagement.setNotif(true)
                toast("Aktifkan Alarm")
                finish()
            } else {
                reminder.cancelAlarm(this)
                sessionManagement.setNotif(false)
                toast("Mematikan Alarm")
                finish()
            }
        }
    }



}