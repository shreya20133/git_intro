package com.example.dell1.mainideaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class AddYourExpenses extends AppCompatActivity {

//    RecyclerView rvWhoPaid,rvForWhom;
//    TextView tvTotalWhoPaid;
//    double totalsum=0.0;
    ArrayList<MyModel> modelArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addyourexpenses);
        Toolbar toolbar = findViewById(R.id.toolbarGroup);
        toolbar.setTitle("NEW EXPENSE");
        toolbar.setNavigationIcon(R.drawable.ic_receipt_black_24dp);

        Intent intent=getIntent();
        ArrayList<GroupMembers> groupMembersArrayList=intent.getParcelableArrayListExtra("grpMembersArrayList");
        Log.e("TAG","inGroupListInEXPENSES: " + groupMembersArrayList.size());
        modelArrayList=new ArrayList<>();
        modelArrayList.add(new MyModel(MyModel.EVENT_TYPE));
        modelArrayList.add(new MyModel(MyModel.WHOPAID_TYPE));
        modelArrayList.add(new MyModel(MyModel.FORWHOMPAID_TYPE));
        modelArrayList.add(new MyModel(MyModel.SPLIT_TYPE));

        MultiViewTypeAdapter adapter=new MultiViewTypeAdapter(getBaseContext(),groupMembersArrayList,modelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, OrientationHelper.VERTICAL, false);
        RecyclerView mRecyclerView =findViewById(R.id.heterogenousrecyclerView);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);

//        rvWhoPaid=findViewById(R.id.rv_whoPaid);
//        LinearLayoutManager llm=new LinearLayoutManager(getBaseContext());
//        rvWhoPaid.setLayoutManager(llm);
//        GroupMembersAdapter groupMembersAdapter=new GroupMembersAdapter(getBaseContext(),groupMembersArrayList);
//        rvWhoPaid.setAdapter(groupMembersAdapter);

//        tvTotalWhoPaid=findViewById(R.id.tv_totalAmountWhoPaid);
//        for(int i=0;i<groupMembersArrayList.size();i++){
//
//            totalsum+=groupMembersArrayList.get(i).getPaidAmount();
//        }
//        tvTotalWhoPaid.setText(String.valueOf(totalsum));

//        rvForWhom=findViewById(R.id.rv_ForWhom);
//        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getBaseContext());
//        rvForWhom.setLayoutManager(linearLayoutManager2);
//        ForWhomAdapter splitAdapter=new ForWhomAdapter(getBaseContext(),groupMembersArrayList);
//        rvForWhom.setAdapter(splitAdapter);

        

    }
}
