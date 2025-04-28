package com.example.ecommerceapp.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ecommerceapp.R
import com.example.ecommerceapp.databinding.ActivityMainBinding
import com.example.ecommerceapp.presentation.fragments.StartFragmentDirections
import com.example.ecommerceapp.presentation.viewmodels.MainViewModel
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.setGone
import com.example.ecommerceapp.presentation.uiutils.VisibilityUtils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val  viewModel by viewModels<MainViewModel>()
     var navHostFragment: NavHostFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
        bottomNavigationVisibilityHandler()
        observe()


    }


   private fun setupBottomNav() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as? NavHostFragment
       navHostFragment?.let {
           NavigationUI.setupWithNavController(binding.bottomNavigation, it.navController)
           binding.bottomNavigation.itemIconTintList = null
           binding.bottomNavigation.stateListAnimator = null
       }

       }



  private  fun bottomNavigationVisibilityHandler() {
        navHostFragment?.navController?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> binding.bottomNavigationCard.setGone()
                R.id.homeFragment -> binding.bottomNavigationCard.show()
                R.id.startFragment -> binding.bottomNavigationCard.setGone()
                R.id.loginFragment -> binding.bottomNavigationCard.setGone()
                R.id.signupFragment -> binding.bottomNavigationCard.setGone()
                R.id.profileFragment -> binding.bottomNavigationCard.show()
                R.id.settingsFragment -> binding.bottomNavigationCard.setGone()
                R.id.cartFragment -> binding.bottomNavigationCard.show()


            }
        }


    }

    private fun observe(){
        viewModel.isUserLoggedIn.observe(this){
            if(it){
                navHostFragment?.findNavController()?.navigate(R.id.homeFragment)

            }
            else{
                navHostFragment?.findNavController()?.navigate(R.id.startFragment)

            }

        }

    }









}




