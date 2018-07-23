package young.project.android.com.bakeaide.widget;

import android.content.Intent;

public class WidgetRemoteViewService extends android.widget.RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
