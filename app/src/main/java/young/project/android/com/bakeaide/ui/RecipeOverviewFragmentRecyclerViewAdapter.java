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
import young.project.android.com.bakeaide.domain.model.Step;

public class RecipeOverviewFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RecipeOverviewFragmentRecyclerViewAdapter.ViewHolder>{
    private Context mContext;
    private int mRecipePosition;
    private List<Step> mStepList;
    private FragmentChangeListener fragmentChangeListener;

    public RecipeOverviewFragmentRecyclerViewAdapter(Context context, int recipePosition, List<Step> stepList){
        mContext = context;
        mRecipePosition = recipePosition;
        mStepList = stepList;
        fragmentChangeListener = (MainActivity) context;
    }

    @NonNull
    @Override
    public RecipeOverviewFragmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_recipe_overview_step_list_item, parent, false);
        return new RecipeOverviewFragmentRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeOverviewFragmentRecyclerViewAdapter.ViewHolder holder, int position) {
        final Step step = mStepList.get(position);
        final int stepPosition = position;
        holder.mStepNumberTextView.setText(String.valueOf(position + 1));
        holder.mStepShortDescriptionTextView.setText(step.getShortDescription());
        holder.mStepItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentChangeListener.replaceFragment(
                        new StepDetailFragment(),
                        mRecipePosition,
                        stepPosition,
                        MainActivity.STEP_DETAIL_FRAGMENT_TAG);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        final CardView mStepItemCardView;
        final TextView mStepNumberTextView;
        final TextView mStepShortDescriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);

            mStepItemCardView = itemView.findViewById(R.id.cv_recipe_step_item);
            mStepNumberTextView = itemView.findViewById(R.id.tv_step_number);
            mStepShortDescriptionTextView = itemView.findViewById(R.id.tv_step_short_description);

        }
    }
}
