package com.example.aaalamabra1925

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check permissions
        val permissionsList = arrayListOf<String>()
        // Check write permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        // Location permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        // Ask for needed permissions
        val permissionsArray = arrayOfNulls<String>(permissionsList.size)
        permissionsList.toArray(permissionsArray)
        if (permissionsList.size > 0){
            ActivityCompat.requestPermissions(this,
                permissionsArray, 0)
        }


        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = this.findViewById(R.id.drawer_layout)
        val navView: NavigationView = this.findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_ipmenu,
                R.id.nav_inner_maps_list
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.clear_database -> {
                val dbManager = DbManager(this)
                dbManager.deleteAll()
                true
            }
            R.id.fill_database -> {
                val dbManager = DbManager(this)

                if (dbManager.fillDatabase() > 0){
                    Toast.makeText(this, "DataBase filled!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "DataBase not filled!", Toast.LENGTH_LONG).show()
                }
                true
            }
            R.id.check_database -> {
                val dbManager = DbManager(this)
                val cursor = dbManager.queryAll()
                if (cursor.moveToFirst()) {
                    Toast.makeText(this, "DataBase isn't empty!", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(this, "DataBase is empty!", Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
