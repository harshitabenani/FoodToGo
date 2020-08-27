package com.harshita.foodtogo.activity



import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import com.harshita.foodtogo.R
import com.harshita.foodtogo.database.ResDatabase
import com.harshita.foodtogo.database.ResEntity
import com.harshita.foodtogo.fragment.*
import kotlinx.android.synthetic.*


class HomeActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView
    lateinit var txtName: TextView
    lateinit var txtPhone: TextView
    var phone: String? = null


    var previousMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        drawerLayout = findViewById(R.id.drawerLayout)
        coordinatorLayout = findViewById(R.id.coordinatorLayout)
        toolbar = findViewById(R.id.toolbar)
        frameLayout = findViewById(R.id.frameLayout)
        navigationView = findViewById(R.id.navigationView)
        val headerView = navigationView.getHeaderView(0)

        txtPhone = headerView.findViewById(R.id.txtPhone)

        phone = intent.getStringExtra("phone")
        txtPhone.setText(phone)


        setUpToolbar()
        openHome()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@HomeActivity,
            drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        navigationView.setNavigationItemSelectedListener {

            if (previousMenuItem != null) {
                previousMenuItem?.isChecked = false
            }

            it.isCheckable = true
            it.isChecked = true
            previousMenuItem = it

            when (it.itemId) {
                R.id.home -> {

                    openHome()

                    drawerLayout.closeDrawers()

                }




                R.id.favourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            FavouriteFragment()
                        )
                        .commit()

                    supportActionBar?.title = "Favourite Restaurants"

                    drawerLayout.closeDrawers()
                }



                R.id.faq -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            FaqFragment()
                        )
                        .commit()

                    supportActionBar?.title = "FAQs"

                    drawerLayout.closeDrawers()
                }

                R.id.logout -> {
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frameLayout,
                            LogoutFragment()
                        )
                        .commit()

                    supportActionBar?.title = "Logout"

                    drawerLayout.closeDrawers()
                }


            }
            return@setNavigationItemSelectedListener true
        }
    }

    fun setUpToolbar() { //tool bar title
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Toolbar Title"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //selecting the options in drawer layout

        val id = item.itemId

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    fun openHome() {  //So that first screen is dashboard as soon as app is opened

        val fragment = HomeFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)

        transaction.commit()
        navigationView.setCheckedItem(R.id.home)

        supportActionBar?.title = "All Restaurants"

    }

    override fun onBackPressed() {   //it goes back to the screen it should go back to

        val frag = supportFragmentManager.findFragmentById(R.id.frameLayout)

        when (frag) {
            !is HomeFragment -> openHome()

            else -> super.onBackPressed()
        }

    }


}