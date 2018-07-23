package young.project.android.com.bakeaide.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import young.project.android.com.bakeaide.R;
import young.project.android.com.bakeaide.domain.model.Ingredient;

public class IngredientListRecyclerViewAdapter extends RecyclerView.Adapter<IngredientListRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Ingredient> mIngredientList;
    private FragmentChangeListener fragmentChangeListener;

    public IngredientListRecyclerViewAdapter(Context context, List<Ingredient> ingredientList){
        mContext = context;
        mIngredientList = ingredientList;
        fragmentChangeListener = (MainActivity) context;
    }

    @NonNull
    @Override
    public IngredientListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.ingredient_list_item, parent, false);
        return new IngredientListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientListRecyclerViewAdapter.ViewHolder holder, int position) {
        final Ingredient ingredient = mIngredientList.get(position);
        holder.mIngredientNameTextView.setText(ingredient.getIngredient());
        holder.mIngredientQuantityTextView.setText(String.valueOf(ingredient.getQuantity()));
        String measureText = " " + ingredient.getMeasure();
        holder.mIngredientMeasureTextView.setText(measureText);
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        final CardView mIngredientItemCardView;
        final TextView mIngredientNameTextView;
        final TextView mIngredientQuantityTextView;
        final TextView mIngredientMeasureTextView;

        ViewHolder(View itemView) {
            super(itemView);

            mIngredientItemCardView = itemView.findViewById(R.id.cv_ingredient_list_item);
            mIngredientNameTextView = itemView.findViewById(R.id.tv_ingredient_name);
            mIngredientQuantityTextView = itemView.findViewById(R.id.tv_ingredient_quantity);
            mIngredientMeasureTextView = itemView.findViewById(R.id.tv_ingredient_measure);
        }
    }
}
