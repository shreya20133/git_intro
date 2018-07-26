package com.example.dell1.mainideaapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MultiViewTypeAdapter extends RecyclerView.Adapter {

    Context ctx;
    ArrayList<GroupMembers> groupMembersArrayList;
    ArrayList<MyModel> modelArrayList;
    int total_types;
    Double totalsum=0.0;
    ForWhomAdapter forwhomAdapter;

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        EditText editText;

        public EventViewHolder(View itemView) {
            super(itemView);

            editText=itemView.findViewById(R.id.etAddEvent);
        }
    }
    public static class WhoPaidViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerViewWhoPaid;
        TextView tvtotalAmount;
        public WhoPaidViewHolder(View itemView) {
            super(itemView);
            recyclerViewWhoPaid=itemView.findViewById(R.id.rv_whoPaid);
            tvtotalAmount=itemView.findViewById(R.id.tv_totalAmountWhoPaid);
        }
    }

    public static class ForWhomViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rvForWhom;


        public ForWhomViewHolder(View itemView) {
            super(itemView);
            rvForWhom=itemView.findViewById(R.id.rv_ForWhom);
        }
    }

    public static class SplitViewHolder extends RecyclerView.ViewHolder {

        Button btnEqualSplit,btnAmountSplit,btnWeightSplit;

        public SplitViewHolder(View itemView) {
            super(itemView);
            btnEqualSplit=itemView.findViewById(R.id.btnEqualSplit);
            btnAmountSplit=itemView.findViewById(R.id.btnAmountSplit);
            btnWeightSplit=itemView.findViewById(R.id.btnWeightSplit);
        }
    }





    public MultiViewTypeAdapter(Context baseContext, ArrayList<GroupMembers> groupMembersArrayList,ArrayList<MyModel> modelArrayList) {

        this.ctx=baseContext;
        this.groupMembersArrayList=groupMembersArrayList;
        this.modelArrayList=modelArrayList;
        total_types=modelArrayList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case MyModel.EVENT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_addevent, parent, false);
                return new EventViewHolder(view);
            case MyModel.WHOPAID_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_whopaid, parent, false);
                return new WhoPaidViewHolder(view);
            case MyModel.FORWHOMPAID_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_newexpenseforwhom, parent, false);
                return new ForWhomViewHolder(view);
            case MyModel.SPLIT_TYPE:
                view=  LayoutInflater.from(parent.getContext()).inflate(R.layout.split,parent,false);
                return new SplitViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyModel object = modelArrayList.get(position);
        if (object != null) {
            switch (object.type) {

                case MyModel.EVENT_TYPE:
                    String eventName=((EventViewHolder) holder).editText.getText().toString();
                    break;

                case MyModel.WHOPAID_TYPE:

                    LinearLayoutManager llm=new LinearLayoutManager(ctx);
                    ((WhoPaidViewHolder) holder).recyclerViewWhoPaid.setLayoutManager(llm);
                    GroupMembersAdapter groupMembersAdapter=new GroupMembersAdapter(ctx,groupMembersArrayList);
                    ((WhoPaidViewHolder) holder).recyclerViewWhoPaid.setAdapter(groupMembersAdapter);

                    for(int i=0;i<groupMembersArrayList.size();i++){
                        totalsum+=groupMembersArrayList.get(i).getPaidAmount();
                    }
                    ((WhoPaidViewHolder) holder).tvtotalAmount.setText(String.valueOf(totalsum));
                    break;

                case MyModel.FORWHOMPAID_TYPE:

                    LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(ctx);
                    ((ForWhomViewHolder) holder).rvForWhom.setLayoutManager(linearLayoutManager2);
                    forwhomAdapter = new ForWhomAdapter(ctx,groupMembersArrayList);
                    ((ForWhomViewHolder) holder).rvForWhom.setAdapter(forwhomAdapter);
                    break;

                case MyModel.SPLIT_TYPE:

                    ((SplitViewHolder) holder).btnEqualSplit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent=new Intent(view.getContext(),SplitAmountEqually.class);
                            intent.putParcelableArrayListExtra("newgrplist",forwhomAdapter.getGroupMembersArrayList());
                            view.getContext().startActivity(intent);
                        }
                    });

                    ((SplitViewHolder) holder).btnAmountSplit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent intent=new Intent(view.getContext(),SplitAmountByAmt.class);
                            intent.putParcelableArrayListExtra("newgrplist",forwhomAdapter.getGroupMembersArrayList());
                            view.getContext().startActivity(intent);
                        }
                    });


            }
        }
        

    }

    @Override
    public int getItemViewType(int position) {
        switch (modelArrayList.get(position).type) {
            case 0:
                return MyModel.EVENT_TYPE;
            case 1:
                return MyModel.WHOPAID_TYPE;
            case 2:
                return MyModel.FORWHOMPAID_TYPE;
            case 3:
                return MyModel.SPLIT_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }
}