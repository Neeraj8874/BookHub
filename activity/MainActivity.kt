package com.internsala.bookhub.activity


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.internsala.bookhub.*
import com.internsala.bookhub.fragment.AboutAppFragment
import com.internsala.bookhub.fragment.DashboardFragment
import com.internsala.bookhub.fragment.FavouritesFragment
import com.internsala.bookhub.fragment.ProfileFragment

@SuppressLint("Registered")
class MainActivity : AppCompatActivity() {
   lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    var previousMenuItem:MenuItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout =findViewById(R.id.DrawerLayout)
        toolbar =findViewById(R.id.Toolbar)
        coordinatorLayout = findViewById(R.id.Coordinator)
        frameLayout =findViewById(R.id.Frame)
        navigationView = findViewById(R.id.Navigation)
        setUpToolBar()
        openDashboard()
        val actionBarDrawerToggle=ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            if(previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it
            when(it.itemId){
                R.id.titleDashBoard ->{
                    openDashboard()
                    drawerLayout.closeDrawers()
                }
                R.id.titlefavourites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.Frame,
                            FavouritesFragment()
                        )
                        .commit()
                    supportActionBar?.title="Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.titleProfile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.Frame,
                            ProfileFragment()
                        )
                        .commit()
                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.titleAboutApp ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.Frame,
                            AboutAppFragment()
                        )
                        .commit()
                    supportActionBar?.title="AboutApp"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }


    }
    fun setUpToolBar(){
        setSupportActionBar(toolbar)
        supportActionBar?.title="ToolBar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        if(id==android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }
    fun openDashboard(){
        val fragment = DashboardFragment()
        val transaction =supportFragmentManager.beginTransaction()
        transaction.replace(
            R.id.Frame,
            DashboardFragment()
        )
        transaction.commit()
        supportActionBar?.title="DashBoard"
        navigationView.setCheckedItem(R.id.titleDashBoard)
    }

    override fun onBackPressed() {
        val frag = supportFragmentManager.findFragmentById(R.id.Frame)
        when(frag){
            !is DashboardFragment -> openDashboard()
            else -> super.onBackPressed()
        }
    }
}
