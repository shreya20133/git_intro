package com.example.dell1.mainideaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ForWhomAdapter extends RecyclerView.Adapter<ForWhomAdapter.ViewHolder> {

    private Context baseContext;
    public ArrayList<GroupMembers> groupMembersArrayList;


    public ForWhomAdapter(Context baseContext, ArrayList<GroupMembers> groupMembersArrayList) {

        this.baseContext=baseContext;
        this.groupMembersArrayList=groupMembersArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView= LayoutInflater.from(baseContext).inflate(R.layout.item_row_forwhom,parent,false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final GroupMembers currentgrpMember=groupMembersArrayList.get(position);
        String initial=currentgrpMember.getName().substring(0,1);
        holder.memberForWhomPaidIcon.setText(initial);
        holder.memberForWhomPaidName.setText(currentgrpMember.getName());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(currentgrpMember.getGetPaidByOther());
        Log.e("BEFORECHECKCHANGED",""+currentgrpMember.getGetPaidByOther());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentgrpMember.setGetPaidByOther(isChecked);
                Boolean test=currentgrpMember.getGetPaidByOther();
                Log.e("TAG", "onCheckedChanged: " + test);
//                if(currentgrpMember.getGetPaidByOther()) {
//                    checkedArrayList.add(currentgrpMember);
//                    Log.e("TAG", "inCheckedArrayList: " + currentgrpMember.getName());
//                    notifyDataSetChanged();
//                }
//                else{
//                    currentgrpMember.setGetPaidByOther();
//                    checkedArrayList.remove(currentgrpMember);
//                    notifyDataSetChanged();
//                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return groupMembersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView memberForWhomPaidName;
        TextView memberForWhomPaidIcon;
        CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            memberForWhomPaidName=itemView.findViewById(R.id.member_forWhomPaidName);
            memberForWhomPaidIcon=itemView.findViewById(R.id.member_forWhomPaidImage);
            checkBox=itemView.findViewById(R.id.checkBoxForPaidForMember);
        }
    }


//    public  ArrayList<GroupMembers> getCheckedArrayList() {
//        return checkedArrayList;
//    }

    public ArrayList<GroupMembers> getGroupMembersArrayList() {
        return groupMembersArrayList;
    }
}
