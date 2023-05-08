package com.example.pizzaaa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pizzaaa.dataClasses.Pizza;
import com.example.pizzaaa.dataClasses.Reviews;
import com.example.pizzaaa.database.DBHelper;
import com.example.pizzaaa.fragments.AboutFragment;
import com.example.pizzaaa.fragments.ListFragment;
import com.example.pizzaaa.fragments.ListOrdersFragment;
import com.example.pizzaaa.fragments.ProfileFragment;
import com.example.pizzaaa.utils.AppSession;
import com.example.pizzaaa.utils.Constants;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    // declaring variables
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle menuButton;
    NavigationView navigationView;
    DBHelper database;
    AppSession session;
    Pizza pizzaData;
    CircleImageView imgHeaderDP;
    TextView tvHeaderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // calling method to instantiate variables
        init();

        // setting up the layout in frame
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new ListFragment()).commit();
        }

        // adding default pizza and reviews data in database
        addPizzas();
        addReviews();
    }

    // initializing views and variables
    private void init(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        menuButton = new ActionBarDrawerToggle(this,drawerLayout,R.string.close,R.string.open);
        drawerLayout.addDrawerListener(menuButton);
        menuButton.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setNavigateDrawer();
        database = new DBHelper(this);
        session = AppSession.getInstance(this);

        View hView = navigationView.getHeaderView(0);

        tvHeaderName = (TextView) hView.findViewById(R.id.tv_name_header);
        imgHeaderDP = hView.findViewById(R.id.img_dp_header);
        tvHeaderName.setText(session.getStringData(Constants.NAME));

        // setting the image and name value from app session
        String previouslyEncodedImage = session.getStringData(Constants.IMAGE);
        if( !previouslyEncodedImage.equalsIgnoreCase("NA") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            imgHeaderDP.setImageBitmap(bitmap);
        } else {
            imgHeaderDP.setImageResource(R.drawable.user_img1);
        }
    }

    // opening fragments on click of  navigation view item click listeners
    private void setNavigateDrawer(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag = null;
                int itemId = item.getItemId();
                if(itemId == R.id.nav_menu){
                    frag = new ListFragment();
                }
                else if(itemId == R.id.nav_myorders){
                    frag = new ListOrdersFragment();
                }
                else if(itemId == R.id.nav_Profile){
                    frag = new ProfileFragment();
                }
                else if(itemId == R.id.nav_Aboutus){
                    frag = new AboutFragment();
                }
                else if(itemId == R.id.nav_logout){
                  showLogoutDialog();
                }

                if(frag!=null){
                    FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
                    fragTrans.replace(R.id.frameLayout,frag);
                    fragTrans.commit();
                    drawerLayout.closeDrawers();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (menuButton.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    // adding pizza data to database
    private void addPizzas(){

        Cursor cursor = database.ListItems();
        if (cursor.getCount() == 0) {

            String[] names = getResources().getStringArray(R.array.pizza_names_array);
            int[] price = getResources().getIntArray(R.array.price_array);
            int[] quantity = getResources().getIntArray(R.array.quant_array);
            int[] images = {R.drawable.img_pizza1,R.drawable.img_pizza2,R.drawable.img_pizza3,R.drawable.img_pizza4,R.drawable.img_pizza5,R.drawable.img_pizza5,R.drawable.img_pizza7};
            String[] size =  getResources().getStringArray(R.array.sizes_array);
            String[] calories =  getResources().getStringArray(R.array.calories_array);
            String[] prepTime =  getResources().getStringArray(R.array.time_array);

            for (int i = 1; i < 8; i++) {
                pizzaData = new Pizza();
                pizzaData.setId(i);
                pizzaData.setName(names[i - 1]);
                pizzaData.setPrice(price[i - 1]);
                pizzaData.setQuantity(quantity[i - 1]);
                pizzaData.setCalories(calories[i - 1]);
                pizzaData.setPrepTime(prepTime[i - 1]);
                pizzaData.setSize(size[i - 1]);
                pizzaData.setImage(images[i - 1]);
                database.AddPizza(pizzaData);
            }
        }
    }



    private void showLogoutDialog(){

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.alert))
                .setMessage(getResources().getString(R.string.logout))

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        session.storeBoolenData(Constants.ISLOGIN,false);
                        Intent intent = new Intent(MainActivity.this,SplashActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    // adding review data to database
    private void addReviews(){


        Reviews reviews;
        Cursor cursor = database.ListReviews();
        if (cursor.getCount() == 0) {


            String[] names = getResources().getStringArray(R.array.rev_pizza_names_array);
            String[] likes = getResources().getStringArray(R.array.likes_array);
            String[] rating = getResources().getStringArray(R.array.rating_array);
            int[] images = {R.drawable.user_img1, R.drawable.img_user1, R.drawable.img_user2, R.drawable.img_user3, R.drawable.img_user1, R.drawable.user_img1, R.drawable.img_user3};
            String[] userNames = getResources().getStringArray(R.array.rev_user_array);
            String[] comments = getResources().getStringArray(R.array.rev_comments_array);

            for (int i = 1; i < 8; i++) {
                reviews = new Reviews();
                reviews.setPizzaName(names[i - 1]);
                reviews.setLikes(likes[i - 1]);
                reviews.setRating(rating[i - 1]);
                reviews.setUsernames(userNames[i - 1]);
                reviews.setComments(comments[i - 1]);
                reviews.setUserPic(images[i - 1]);
                database.AddReviews(reviews);
            }
        }
    }






}




