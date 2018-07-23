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
import young.project.android.com.bakeaide.domain.model.Recipe;

public class RecipeListFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecipeListFragmentRecyclerViewAdapter.ViewHolder>{
    private Context mContext;
    private List<Recipe> mRecipeList;
    private FragmentChangeListener fragmentChangeListener;

    public RecipeListFragmentRecyclerViewAdapter(Context context, List<Recipe> recipeList){
        mContext = context;
        mRecipeList = recipeList;
        fragmentChangeListener = (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Recipe recipe = mRecipeList.get(position);
        final int recipePosition = position;
        String servingText = mContext.getString(R.string.serves) + String.valueOf(recipe.getServings());
        holder.mRecipeNameTextView.setText(recipe.getName());
        holder.mRecipeServingSizeTextView.setText(servingText);
        holder.mRecipeItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentChangeListener.replaceFragment(new RecipeOverviewFragment(),
                        recipePosition,
                        MainActivity.RECIPE_OVERVIEW_FRAGMENT_TAG);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        final CardView mRecipeItemCardView;
        final TextView mRecipeNameTextView;
        final TextView mRecipeServingSizeTextView;

        ViewHolder(View itemView) {
            super(itemView);

            mRecipeItemCardView = itemView.findViewById(R.id.cv_recipe_list_item);
            mRecipeNameTextView = itemView.findViewById(R.id.tv_recipe_name);
            mRecipeServingSizeTextView = itemView.findViewById(R.id.tv_serving_size);
        }
    }

}
