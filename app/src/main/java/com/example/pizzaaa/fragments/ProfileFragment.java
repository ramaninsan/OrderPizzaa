package com.example.pizzaaa.fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pizzaaa.R;
import com.example.pizzaaa.utils.AppSession;
import com.example.pizzaaa.utils.Constants;
import com.google.android.material.snackbar.Snackbar;


import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    View v;
    CircleImageView circleImageView;
    ImageView imgEditPic;
    CoordinatorLayout coordinatorLayout;
    EditText etName, etDob, etEmail, etPassword;
    TextView tvDOB;
    Button btnEdit, btnUpdate;
    AppSession session;
    Intent cameraIntent;
    DatePickerDialog datePickerDialog;

    public ProfileFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_profile, container, false);

        getActivity().setTitle("Profile");

        circleImageView = v.findViewById(R.id.profile_image);
        imgEditPic = v.findViewById(R.id.imgEditDP);
        coordinatorLayout = v.findViewById(R.id.cl);
        etName = v.findViewById(R.id.etName);
        tvDOB = v.findViewById(R.id.etDob);
        etPassword = v.findViewById(R.id.etPassword);
        etEmail = v.findViewById(R.id.etEmail);
        btnEdit = v.findViewById(R.id.btnEdit);
        btnUpdate = v.findViewById(R.id.btnUpdate);

        session = AppSession.getInstance(getActivity());

        etName.setText(session.getStringData(Constants.NAME));
        etEmail.setText(session.getStringData(Constants.EMAIL));
        etPassword.setText(session.getStringData(Constants.PASSWORD));
        if (session.getStringData(Constants.DOB).equals("NA")){
            tvDOB.setText("--");
        } else {
            tvDOB.setText(session.getStringData(Constants.DOB));
        }


        String previouslyEncodedImage = session.getStringData(Constants.IMAGE);
        if( !previouslyEncodedImage.equalsIgnoreCase("NA") ){
            byte[] b = Base64.decode(previouslyEncodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            circleImageView.setImageBitmap(bitmap);
        } else {
            circleImageView.setImageResource(R.drawable.user_img1);
        }

        imgEditPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionOpenCamera();
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    etName.setEnabled(true);
                    etEmail.setEnabled(true);
                    tvDOB.setEnabled(true);
                    etPassword.setEnabled(true);

                    btnEdit.setVisibility(View.GONE);
                    btnUpdate.setVisibility(View.VISIBLE);

                    etName.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etName, InputMethodManager.SHOW_IMPLICIT);

            }
        });
        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month = cal.get(Calendar.MONTH);
                int year = cal.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvDOB.setText(day+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etName.getText().toString().equals("")){
                    showError("Error","Enter valid name.");
                    return;
                }
                if (etEmail.getText().toString().equals("")){
                    showError("Error","Enter valid email.");
                    return;
                }
                if (tvDOB.getText().toString().equals("")){
                    showError("Error","Enter valid date of birth.");
                    return;
                }
                if (etPassword.getText().toString().equals("")){
                    showError("Error","Enter valid password.");
                    return;
                }

                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String dateOfB = tvDOB.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                Log.e("----","---=-" + name);
                session.storeStringData(Constants.NAME,name);
                session.storeStringData(Constants.PASSWORD,password);
                session.storeStringData(Constants.EMAIL,email);
                session.storeStringData(Constants.DOB,dateOfB);

                etName.setEnabled(false);
                etEmail.setEnabled(false);
                tvDOB.setEnabled(false);
                etPassword.setEnabled(false);

                btnUpdate.setVisibility(View.GONE);
                btnEdit.setVisibility(View.VISIBLE);

                showDialog("Profile updated successfully.");
            }
        });

        return v;
    }

    public void showDialog(String msg){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        TextView text = (TextView) dialog.findViewById(R.id.tvMessage);
        text.setText(msg);

        TextView dialogButton = (TextView) dialog.findViewById(R.id.tvClose);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }




    // startOnActivityResult's new way is defined below
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result!=null && result.getResultCode() == RESULT_OK){

                if (result.getData()!=null){
                    Bundle extras = result.getData().getExtras();
                    Bitmap photo = (Bitmap) extras.get("data");

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                    session.storeStringData(Constants.IMAGE,encodedImage);

                    circleImageView.setImageBitmap(photo);


                }
            }
        }
    });


    private void checkPermissionOpenCamera(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA},5);

        } else {
            takePhoto();
        }
    }




    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    Log.e("00000","00000000000fbff");
                    if (result) {
                        Log.e("00000","000000000bff00");
                        // PERMISSION GRANTED
                    } else {
                        Log.e("00000","00000ssrs000000");
                        // PERMISSION NOT GRANTED
                    }
                }
            }
    );




    private void takePhoto(){
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startForResult.launch(cameraIntent);
    }

    private void showError(String mTitle, String message){

        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();

    }
}
