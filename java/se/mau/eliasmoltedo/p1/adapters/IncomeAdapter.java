package se.mau.eliasmoltedo.p1.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import se.mau.eliasmoltedo.p1.Controller;
import se.mau.eliasmoltedo.p1.R;
import se.mau.eliasmoltedo.p1.database.Income;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.support.constraint.Constraints.TAG;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.Holder>{
    private LayoutInflater inflater;
    private List<Income> content;
    private Controller controller;

//    public IncomeAdapter(Context context){
//
//        this(context, new ArrayList<Income>());
//    }

    public IncomeAdapter(Context context, List<Income> content){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.content = content;
    }

    public void setContent(List<Income> content){
        this.content = content;
        super.notifyDataSetChanged();
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.income_row, viewGroup ,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.Holder holder, int pos) {
        holder.tvTitle.setText(content.get(pos).getTitle());
        holder.tvAmount.setText(content.get(pos).getAmount());

//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String strDate = dateFormat.format(content.get(pos).getDate());

        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yy");
        String s = fmtOut.format(content.get(pos).getDate()).toString();
        holder.tvDate.setText(s);
        holder.categoryString = content.get(pos).getCategory().toString();
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private TextView tvAmount;
        private TextView tvDate;
        private String categoryString;

        public Holder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(v.getContext());
            a_builder.setTitle("Detaljerad information");
            a_builder.setMessage("Titel: " + tvTitle.getText().toString()
                    + "\nBelopp: " + tvAmount.getText().toString()
                    + "\nDatum: "  + tvDate.getText().toString()
                    + "\nKategori: " + categoryString);
            a_builder.setPositiveButton("Fortsätt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog dialog = a_builder.create();
            dialog.show();
        }
    }
}
