package young.project.android.com.bakeaide.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import young.project.android.com.bakeaide.R;
import young.project.android.com.bakeaide.domain.model.Ingredient;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    public List<Ingredient> mIngredientList;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        Gson gson = new Gson();
        mContext = context;
        String jsonString = String.valueOf(intent.getData().getSchemeSpecificPart());
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();
        mIngredientList = gson.fromJson(jsonString, listType);
    }
    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }



    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_ingredient_list_item);
        rv.setTextViewText(R.id.tv_widget_ingredient_name, mIngredientList.get(position).getIngredient());
        rv.setTextViewText(R.id.tv_widget_ingredient_quantity, String.valueOf(mIngredientList.get(position).getQuantity()));
        rv.setTextViewText(R.id.tv_widget_ingredient_measure, " " + mIngredientList.get(position).getMeasure());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
