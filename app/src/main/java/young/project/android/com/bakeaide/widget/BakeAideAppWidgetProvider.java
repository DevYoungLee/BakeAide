package young.project.android.com.bakeaide.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.util.List;

import young.project.android.com.bakeaide.R;
import young.project.android.com.bakeaide.domain.Repository;
import young.project.android.com.bakeaide.domain.RepositoryCallbacks;
import young.project.android.com.bakeaide.domain.model.Ingredient;
import young.project.android.com.bakeaide.domain.model.Recipe;

public class BakeAideAppWidgetProvider extends AppWidgetProvider{

    private Repository mRepository;
    private static List<Recipe> mRecipeList;
    private static int mInd;
    private static String PREV_CLICK_ACTION = "prev_click_action";
    private static String NEXT_CLICK_ACTION = "next_click_action";
    private static String JSON_URI_SCHEME = "content";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Gson gson = new Gson();
        if(mRecipeList == null) {
            getRecipeList(context, appWidgetManager, appWidgetIds);
            mInd = 0;
        }
        else {

            for (int appWidgetId : appWidgetIds) {
                RemoteViews views = new RemoteViews(
                        context.getPackageName(),
                        R.layout.widget_layout
                );

                List<Ingredient> ingredientList = mRecipeList.get(mInd).getIngredients();
                String jsonIngredientList= gson.toJson(ingredientList);

                Intent intent = new Intent(context, WidgetRemoteViewService.class);
                intent.setData(Uri.fromParts(JSON_URI_SCHEME, jsonIngredientList, null));
                views.setRemoteAdapter(R.id.lv_widget, intent);
                views.setTextViewText(R.id.tv_widget_recipe_name, mRecipeList.get(mInd).getName());

                Intent nextIntent = new Intent(context, BakeAideAppWidgetProvider.class);
                nextIntent.setAction(NEXT_CLICK_ACTION);
                PendingIntent nextPendingIntent = PendingIntent.getBroadcast(context, 0, nextIntent, 0);
                views.setOnClickPendingIntent(R.id.button_widget_next, nextPendingIntent);
                if(mInd == mRecipeList.size() - 1){
                    views.setViewVisibility(R.id.button_widget_next, View.INVISIBLE);
                }

                Intent prevIntent = new Intent(context, BakeAideAppWidgetProvider.class);
                prevIntent.setAction(PREV_CLICK_ACTION);
                PendingIntent prevPendingIntent = PendingIntent.getBroadcast(context, 0, prevIntent, 0);
                views.setOnClickPendingIntent(R.id.button_widget_prev, prevPendingIntent);
                if(mInd == 0){
                    views.setViewVisibility(R.id.button_widget_prev, View.INVISIBLE);
                }

                appWidgetManager.updateAppWidget(appWidgetId, null);
                appWidgetManager.updateAppWidget(appWidgetId, views);
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_widget);
            }
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, BakeAideAppWidgetProvider.class);
        if(mRecipeList != null && intent.getAction() != null) {
            if (intent.getAction().equals(NEXT_CLICK_ACTION) && mInd < mRecipeList.size() - 1) {
                mInd++;
                onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
            } else if (intent.getAction().equals(PREV_CLICK_ACTION) && mInd > 0) {
                mInd--;
                onUpdate(context, appWidgetManager, appWidgetManager.getAppWidgetIds(componentName));
            }
        }
    }

    private void getRecipeList(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds){
        mRepository = Repository.getRepositoryInstance(context);
        mRepository.getRecipeListForWidget(new RepositoryCallbacks<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> recipeList) {
                mRecipeList = recipeList;
                onUpdate(context, appWidgetManager, appWidgetIds);

            }

            @Override
            public void onFailure(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }




}
