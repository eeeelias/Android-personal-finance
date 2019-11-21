package se.mau.eliasmoltedo.p1.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import se.mau.eliasmoltedo.p1.CategoryEx;
import se.mau.eliasmoltedo.p1.Controller;
import se.mau.eliasmoltedo.p1.R;
import se.mau.eliasmoltedo.p1.database.Expenditure;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ExpenditureAdapter extends RecyclerView.Adapter<ExpenditureAdapter.Holder>{
    private LayoutInflater inflater;
    private List<Expenditure> content;
    private Controller controller;

//    public ExpenditureAdapter(Context context){
//        this(context, new ArrayList<Expenditure>());
//    }

    public ExpenditureAdapter(Context context, List<Expenditure> content){
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.content = content;
    }

    public void setContent(List<Expenditure> content){
        this.content = content;
        super.notifyDataSetChanged();
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.expenditure_row, viewGroup ,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int pos) {
        holder.tvTitle.setText(content.get(pos).getTitle());
        holder.tvAmount.setText(content.get(pos).getAmount());

        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yy");
        String s = fmtOut.format(content.get(pos).getDate()).toString();
        holder.tvDate.setText(s);
        holder.categoryString = content.get(pos).getCategory().toString();

        CategoryEx categoryEx = content.get(pos).getCategory();

        switch (categoryEx){
            case Livsmedel : holder.ivCategoryPic.setImageResource(R.drawable.caty_trolly); break;
            case Boende : holder.ivCategoryPic.setImageResource(R.drawable.caty_house); break;
            case Fritid : holder.ivCategoryPic.setImageResource(R.drawable.caty_sport); break;
            case Resor : holder.ivCategoryPic.setImageResource(R.drawable.caty_plane); break;
            case Övrigt : holder.ivCategoryPic.setImageResource(R.drawable.caty_ghost); break;
        }
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private TextView tvAmount;
        private TextView tvDate;
        private ImageView ivCategoryPic;
        private String categoryString;

        public Holder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivCategoryPic = itemView.findViewById(R.id.ivCategory);
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
