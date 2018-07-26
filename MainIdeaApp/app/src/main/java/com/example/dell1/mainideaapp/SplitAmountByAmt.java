package com.example.dell1.mainideaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class SplitAmountByAmt extends AppCompatActivity{

    Double totalsum=0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.onclicksplit);
        Intent intent=getIntent();
        ArrayList<GroupMembers> groupMembersNewArrayList=intent.getParcelableArrayListExtra("newgrplist");
        Log.e("tag",""+groupMembersNewArrayList.size());

        TextView textView=findViewById(R.id.tvdisplayTotalAmount);
        for(int i=0;i<groupMembersNewArrayList.size();i++){
            totalsum=totalsum+groupMembersNewArrayList.get(i).getPaidAmount();
        }

        textView.setText(String.valueOf(totalsum));
        RecyclerView recyclerView1=findViewById(R.id.rv_splitEqually);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getBaseContext());
        recyclerView1.setLayoutManager(linearLayoutManager1);
        SplitByAmountAdapter splitByAmountAdapter =new SplitByAmountAdapter(getBaseContext(),groupMembersNewArrayList,totalsum);
        recyclerView1.setAdapter(splitByAmountAdapter);

        RecyclerView recyclerView2=findViewById(R.id.rv_settledebts);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getBaseContext());
        recyclerView2.setLayoutManager(linearLayoutManager2);
        SettleDebtsAdapter settleDebtsAdapter=new SettleDebtsAdapter(getBaseContext(),groupMembersNewArrayList,totalsum);
        recyclerView2.setAdapter(settleDebtsAdapter);
    }

}
