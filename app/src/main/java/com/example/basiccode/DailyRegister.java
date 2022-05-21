package com.example.basiccode;

import android.content.Intent;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DailyRegister extends AppCompatActivity {

    private EditText et_id, et_password, et_passck, et_name, et_age, et_phone_number;
    private RadioGroup rg_gender;
    private RadioButton rb_result;
    private Button btn_register, btn_exam;
    private AlertDialog dialog;
    private boolean exam = false; //중복검사 여부

    @Override
    protected void onCreate(Bundle savedInstanceState) { // 액티비티 시작시 처음으로 실행되는 생명주기!
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_register);

        // 아이디 값 찾아주기
        et_id = (EditText) findViewById(R.id.et_id);
        et_password = (EditText) findViewById(R.id.et_password);
        et_passck = (EditText) findViewById(R.id.et_passck);
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);


        // 아이디 중복검사 버튼 클릭 시 수행
        btn_exam = (Button) findViewById(R.id.btn_exam);
        btn_exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String id=et_id.getText().toString();
                if(exam)
                {
                    return;
                }
                if(id.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( DailyRegister.this );
                    dialog=builder.setMessage("아이디는 빈 칸일 수 없습니다")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            //System.out.println("json"+jsonResponse);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                AlertDialog.Builder builder=new AlertDialog.Builder( DailyRegister.this );
                                dialog=builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                et_id.setEnabled(false);
                                exam=true;
                                btn_exam.setText("확인");
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder( DailyRegister.this );
                                dialog=builder.setMessage("사용할 수 없는 아이디입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ExamRequest examRequest=new ExamRequest(id,responseListener);
                RequestQueue queue= Volley.newRequestQueue(DailyRegister.this);
                queue.add(examRequest);

            }
        });


        // 회원가입 버튼 클릭 시 수행
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해온다.
                String id = et_id.getText().toString();
                final String password = et_password.getText().toString();
                final String PassCk=et_passck.getText().toString();
                String name = et_name.getText().toString();
                String role = "ROLE_USER";
                //radio 선택 가져오기
                int rb_id = rg_gender.getCheckedRadioButtonId();
                rb_result = (RadioButton)findViewById(rb_id);
                String gender = rb_result.getText().toString();
                int age = Integer.parseInt(et_age.getText().toString());
                String phone_number = et_phone_number.getText().toString();


                //아이디 중복체크 했는지 확인
                if (!exam) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DailyRegister.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //한 칸이라도 입력 안했을 경우
                if (id.equals("") || password.equals("") || name.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DailyRegister.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //비밀번호가 동일하지 않은 경우
                if(!password.equals(PassCk)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DailyRegister.this);
                    dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            if (success) {

                                Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", name), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(DailyRegister.this, DailyLogin.class);
                                startActivity(intent);

                                //회원가입 실패시
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해서 요청을 함.
                RegisterRequest registerRequest = new RegisterRequest(id,password,name,role,age,gender,phone_number,responseListener);
                RequestQueue queue = Volley.newRequestQueue(DailyRegister.this);
                queue.add(registerRequest);

            }
        });

    }
}
