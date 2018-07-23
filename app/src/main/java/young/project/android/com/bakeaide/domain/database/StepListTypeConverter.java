package young.project.android.com.bakeaide.domain.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import young.project.android.com.bakeaide.domain.model.Step;

public class StepListTypeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static List<Step> jsonToObject(String jsonString) {
        if(jsonString == null){
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Step>>() {}.getType();
        return gson.fromJson(jsonString, listType);
    }

    @TypeConverter
    public static String objectToJson(List<Step> stepList){
        return gson.toJson(stepList);
    }
}
