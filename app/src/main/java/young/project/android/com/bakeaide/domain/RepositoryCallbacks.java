package young.project.android.com.bakeaide.domain;

public interface RepositoryCallbacks<T> {
    void onSuccess(T t);
    void onFailure(Throwable throwable);
}
