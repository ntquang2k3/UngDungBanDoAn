package com.example.doanandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar=getSupportActionBar();
        //Ánh xạ id :
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav);
        //Kết thúc ánh xạ
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        // Tạo instance của ThongTinSinhVienFragment
                        FragmentHome fragment1 = new FragmentHome();
                        // Ép kiểu FragmentHome thành Fragment
                        Fragment fragmentConverted = (Fragment) fragment1;
                        loadFragment(fragmentConverted);
                        return true;
                    case R.id.navigation_history:
                        // Tạo instance của ThongTinSinhVienFragment
                        FragmentHistory fragmentHistory = new FragmentHistory();
                        // Ép kiểu FragmentHome thành Fragment
                        Fragment fragment3 = (Fragment) fragmentHistory;
                        loadFragment(fragment3);
                        return true;
                    case R.id.navigation_pay:
                        // Tạo instance của ThongTinSinhVienFragment
                        FragmentPay fragmentPay = new FragmentPay();
                        // Ép kiểu FragmentHome thành Fragment
                        Fragment fragment4 = (Fragment) fragmentPay;
                        loadFragment(fragment4);
                        return true;
                    case R.id.navigation_cart:
                        // Tạo instance của ThongTinSinhVienFragment
                        FragmentCart fragmentCart = new FragmentCart();
                        // Ép kiểu FragmentHome thành Fragment
                        Fragment fragment5 = (Fragment) fragmentCart;
                        loadFragment(fragment5);
                        return true;
                    case R.id.navigation_account:
                        // Tạo instance của ThongTinSinhVienFragment
                        FragmentAccount fragmentAccount = new FragmentAccount();
                        // Ép kiểu FragmentHome thành Fragment
                        Fragment fragment2 = (Fragment) fragmentAccount;
                        loadFragment(fragment2);
                        return true;
                }

                return false;

            }
        });
        // Tạo instance của FragmentHome
        FragmentHome fragment = new FragmentHome();
        // Ép kiểu FragmentHome thành Fragment
        Fragment fragmentConverted = (Fragment) fragment;
        loadFragment(fragmentConverted);
    }
    void loadFragment(Fragment fragmentConverted)
    {
        //Mở fragment
        // Bắt đầu transaction để replace Fragment hiện tại
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragmentConverted); // fragment_container là id của FrameLayout trong layout Activity
        transaction.commit();
    }

}