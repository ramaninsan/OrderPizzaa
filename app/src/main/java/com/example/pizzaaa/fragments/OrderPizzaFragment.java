package com.example.pizzaaa.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.amrdeveloper.lottiedialog.LottieDialog;
import com.example.pizzaaa.R;
import com.example.pizzaaa.dataClasses.Orders;
import com.example.pizzaaa.dataClasses.Pizza;
import com.example.pizzaaa.database.DBHelper;

import java.util.Currency;

public class OrderPizzaFragment extends Fragment {

    public OrderPizzaFragment() {
    }

    RelativeLayout layoutM,layoutL;
    ImageView imgTickM,imgTickL,imgPizzaM,imgPizzaL;
    TextView tvSizeM,tvSizeL,tvDescM,tvDescL;
    Spinner spinnerQuantity;
    RadioGroup rgStyle;
    RadioButton rbThin,rbThick;
    CheckBox chkPepper,chkCharam,chkOnions;
    EditText etDesc;
    Button btnOrder;
    Pizza data;
    View v;

    String size,style,topping1,topping2,topping3,otherDesc;
    int quantity,price,finalPrice;

    Orders dataOrders;
    DBHelper dbHelper;

    String symbol;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_order_pizza, container, false);

        Bundle bundle = getArguments();
        data = (Pizza) bundle.getSerializable("PIZZADATA");

        dbHelper = new DBHelper(getActivity());

        layoutM = v.findViewById(R.id.rlM);
        layoutL = v.findViewById(R.id.rlL);
        imgTickL = v.findViewById(R.id.imgTickL);
        imgTickM = v.findViewById(R.id.imgTick);
        imgPizzaL = v.findViewById(R.id.imgSizeL);
        imgPizzaM = v.findViewById(R.id.imgSizeM);
        tvSizeM = v.findViewById(R.id.tvSize);
        tvSizeL = v.findViewById(R.id.tvSizeL);
        tvDescL = v.findViewById(R.id.tvSizeDesL);
        tvDescM = v.findViewById(R.id.tvSizeDes);
        spinnerQuantity = v.findViewById(R.id.spnQuantity);
        rgStyle = v.findViewById(R.id.rgStyle);
        rbThick = v.findViewById(R.id.rbThick);
        rbThin = v.findViewById(R.id.rbThin);
        chkPepper = v.findViewById(R.id.chkPepper);
        chkCharam = v.findViewById(R.id.chkChambord);
        chkOnions = v.findViewById(R.id.chkOnion);
        etDesc = v.findViewById(R.id.etOtherDesc);
        btnOrder = v.findViewById(R.id.btnOrder);




        Currency cur = Currency.getInstance("USD");
        symbol = cur.getSymbol();

        btnOrder.setText("Place Order - "+ symbol + price);



        // by default values
        size = "M";
        style = "Thin";
        topping1 = "Pepper";
        topping2 = "";
        topping3 = "";
        quantity = 1;
        price = data.getPrice();


        layoutM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutM.setBackgroundResource(R.drawable.enable_bg);
                imgTickM.setVisibility(View.VISIBLE);
                imgPizzaM.setImageResource(R.drawable.ic_pizza_enabled);
                tvSizeM.setTextColor(Color.BLACK);
                tvDescM.setTextColor(Color.BLACK);

                layoutL.setBackgroundResource(R.drawable.disable_bg);
                imgTickL.setVisibility(View.GONE);
                imgPizzaL.setImageResource(R.drawable.ic_pizza_disabled);
                tvSizeL.setTextColor(Color.LTGRAY);
                tvDescL.setTextColor(Color.LTGRAY);

                size = "M";

                spinnerQuantity.setSelection(0);
                price = data.getPrice();
                int priceForM = price;
                btnOrder.setText("Place Order - "+ symbol + priceForM);
                finalPrice = priceForM;

            }
        });


        layoutL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutL.setBackgroundResource(R.drawable.enable_bg);
                imgTickL.setVisibility(View.VISIBLE);
                imgPizzaL.setImageResource(R.drawable.ic_pizza_enabled);
                tvSizeL.setTextColor(Color.BLACK);
                tvDescL.setTextColor(Color.BLACK);

                layoutM.setBackgroundResource(R.drawable.disable_bg);
                imgTickM.setVisibility(View.GONE);
                imgPizzaM.setImageResource(R.drawable.ic_pizza_disabled);
                tvSizeM.setTextColor(Color.LTGRAY);
                tvDescM.setTextColor(Color.LTGRAY);

                size = "L";
                spinnerQuantity.setSelection(0);

                price = data.getPrice();
                int priceForL =price*2;
                price = priceForL;
                btnOrder.setText("Place Order - "+ symbol + priceForL);
                finalPrice = priceForL;
            }
        });


        // Fetching string array of sizes from string resource and setting it to adapter
        ArrayAdapter<CharSequence> adapterSize = ArrayAdapter.createFromResource(getActivity(),R.array.size_array, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapterSize.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        // Setting adapter to spinner
        spinnerQuantity.setAdapter(adapterSize);

        // spinner handling
        spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // fetching and setting the name of current selected item to dataClass variable
                String pizzaQuantity = spinnerQuantity.getSelectedItem().toString();

                quantity = Integer.parseInt(pizzaQuantity);

                int amount = price * quantity;
                btnOrder.setText("Place Order - "+ symbol + amount);
                finalPrice = amount;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        rbThin.setChecked(true);
        // radioGroup beverages listener and handling
        rgStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // getting radioButton Ids
                RadioButton rb = group.findViewById(checkedId);

                //assigning value to dataClass variable accordingly
                style = rb.getText().toString();

            }
        });


        // checkbox  listener and handling
        chkPepper.setChecked(true);
        topping1 = chkPepper.getText().toString();
        chkPepper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkPepper.isChecked()){
                    topping1 = chkPepper.getText().toString();
                } else {
                    topping1 = "";
                }
            }
        });

        // checkbox  listener and handling
        chkCharam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chkCharam.isChecked()){
                    topping2 = chkPepper.getText().toString();
                } else {
                    topping2 = "";
                }
            }
        });


        // checkbox listener and handling
        chkOnions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkOnions.isChecked()){
                    topping3 = chkOnions.getText().toString();
                } else {
                    topping3 = "";
                }
            }
        });


        otherDesc = etDesc.getText().toString();



        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addOrders();

                Button ok = new Button(getActivity());
                ok.setBackgroundResource(R.drawable.dialog_bg);
                ok.setText("GOT IT");
                ok.setTextColor(Color.WHITE);

                LottieDialog dialog = new LottieDialog(getActivity())
                        .setAnimation(R.raw.success)
                        .setAnimationRepeatCount(LottieDialog.INFINITE)
                        .setAutoPlayAnimation(true)
                        .setTitle("ORDER PLACED !!! ")
                        .setTitleTextSize(18)
                        .setTitleColor(Color.BLACK)
                        .setMessage("Message...")
                        .setMessageColor(Color.WHITE)
                        .setDialogBackground(Color.WHITE)
                        .setCancelable(false)
                        .addActionButton(ok)
                        //.addActionButton(cancelButton)
                        .setOnShowListener(dialogInterface -> {})
                        .setOnDismissListener(dialogInterface -> {})
                        .setOnCancelListener(dialogInterface -> {});
                dialog.show();

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                   dialog.dismiss();
                   getActivity().finish();
                    }
                });

            }
        });


        return  v;
    }


    @Override
    public void onResume() {
        super.onResume();

        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle("Order And Reviews");
        }
    }
    private void addOrders(){

        String finalToppings = topping1 + ", " + topping2 + ", " + topping3;

        dataOrders = new Orders();
        dataOrders.setId(data.getId());
        dataOrders.setName(data.getName());
        dataOrders.setPrice(finalPrice);
        dataOrders.setQuantity(quantity);
        dataOrders.setCalories(data.getCalories());
        dataOrders.setPrepTime(data.getPrepTime());
        dataOrders.setSize(size);
        dataOrders.setImage(data.getImage());
        dataOrders.setStyle(style);
        dataOrders.setToppings(finalToppings);
        dataOrders.setOtherDesc(otherDesc);
        dbHelper.AddOrders(dataOrders);


    }
}
