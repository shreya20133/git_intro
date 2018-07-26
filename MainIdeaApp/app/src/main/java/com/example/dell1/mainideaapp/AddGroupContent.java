package com.example.dell1.mainideaapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class AddGroupContent extends AppCompatActivity {

    RecyclerView rvaddMembers;
    List<GroupMembers> groupMembersList;
    EditText editTextMemberName,editTextMemberAmountPaid;
    String groupname, groupDate;
    Button addMembershortcutButon;
    Button continueBtn;
    GroupMembersAdapter groupMembersAdapter;
    GroupDao groupDao;
    GroupMemberDao groupMemberDao;
    MyGroups clickedGrp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewgroup);

        Intent intent = getIntent();
        groupname = intent.getStringExtra("GrpName");
        groupDate=intent.getStringExtra("GrpDate");

        groupMemberDao = MyAppApplication.getMyAppDatabase().getGroupMemberDao();
        groupDao = MyAppApplication.getMyAppDatabase().getGroupDao();


        clickedGrp = groupDao.getgroupWithId(groupDate);
        groupMembersList = clickedGrp.getGroupMembersArrayList();
//        clickedGrp.setGroupMembersArrayList(groupMembersList);

        Toolbar toolbar = findViewById(R.id.toolbarGroup);
        toolbar.setTitle(groupname);
        toolbar.setNavigationIcon(R.drawable.ic_group_black_24dp);

        editTextMemberName = findViewById(R.id.etAddNewMember);
        editTextMemberAmountPaid=findViewById(R.id.etAddMemberAmountPaid);
        addMembershortcutButon=findViewById(R.id.addmemberBtn);
        continueBtn=findViewById(R.id.ContinueButton);
        rvaddMembers = findViewById(R.id.rv_addMembers);
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rvaddMembers.setLayoutManager(llm);

        rvaddMembers.setAdapter(groupMembersAdapter);

            addMembershortcutButon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(editTextMemberName.getText().toString())) {
                        if(!TextUtils.isEmpty(editTextMemberAmountPaid.getText().toString())){

                           groupMemberDao.insertGroupMember(new GroupMembers(
                                    editTextMemberName.getText().toString(),true,true,Double.valueOf(editTextMemberAmountPaid.getText().toString())
                                    ,0.0,0.0));
                           groupMemberDao.insertGroupMembersList(groupMemberDao.getAllGroupMembers());
                           
                        }
                        else{
//                            groupMembersArrayList.add(new GroupMembers(editTextMemberName.getText().toString(),"",false,true,0.0,
//                                    0.0,0.0));
                           groupMemberDao.insertGroupMember(new GroupMembers(
                                    editTextMemberName.getText().toString(),false,true,0.0
                                    ,0.0,0.0));
                            groupMemberDao.insertGroupMembersList(groupMemberDao.getAllGroupMembers());

                        }

//                        groupMembersList=groupMemberDao.getAllGroupMembers();
//                        groupMembersAdapter = new GroupMembersAdapter(getBaseContext(), groupMembersList);
//                        rvaddMembers.setAdapter(groupMembersAdapter);
                        editTextMemberName.setText("");
                        editTextMemberAmountPaid.setText("");
//                        groupMembersList=groupMemberDao.getAllGroupMembers();
//                        groupMembersAdapter=new GroupMembersAdapter(getBaseContext(),groupMembersList);
//                        rvaddMembers.setAdapter(groupMembersAdapter);
//                        groupMembersAdapter.notifyDataSetChanged();

                    }
                    else{
                        Toast.makeText(getBaseContext(),"Member name can't be empty!",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            continueBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentAddExpenses=new Intent(getBaseContext(),AddYourExpenses.class);
                    if(groupMembersList.size()>0) {
                    startActivity(intentAddExpenses);
                    }
                    else{
                        Toast.makeText(getBaseContext(),"add at least 1 member",Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
}