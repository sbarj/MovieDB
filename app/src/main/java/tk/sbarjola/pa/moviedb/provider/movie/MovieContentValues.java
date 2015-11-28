package tk.sbarjola.pa.moviedb.provider.movie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import tk.sbarjola.pa.moviedb.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code movie} table.
 */
public class MovieContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return MovieColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable MovieSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable MovieSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public MovieContentValues putTitle(@Nullable String value) {
        mContentValues.put(MovieColumns.TITLE, value);
        return this;
    }

    public MovieContentValues putTitleNull() {
        mContentValues.putNull(MovieColumns.TITLE);
        return this;
    }

    public MovieContentValues putReleasedate(@Nullable String value) {
        mContentValues.put(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieContentValues putReleasedateNull() {
        mContentValues.putNull(MovieColumns.RELEASEDATE);
        return this;
    }

    public MovieContentValues putPopularity(@Nullable String value) {
        mContentValues.put(MovieColumns.POPULARITY, value);
        return this;
    }

    public MovieContentValues putPopularityNull() {
        mContentValues.putNull(MovieColumns.POPULARITY);
        return this;
    }

    public MovieContentValues putPosterpath(@Nullable String value) {
        mContentValues.put(MovieColumns.POSTERPATH, value);
        return this;
    }

    public MovieContentValues putPosterpathNull() {
        mContentValues.putNull(MovieColumns.POSTERPATH);
        return this;
    }

    public MovieContentValues putDescription(@Nullable String value) {
        mContentValues.put(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieContentValues putDescriptionNull() {
        mContentValues.putNull(MovieColumns.DESCRIPTION);
        return this;
    }

    public MovieContentValues putCategory(@Nullable String value) {
        mContentValues.put(MovieColumns.CATEGORY, value);
        return this;
    }

    public MovieContentValues putCategoryNull() {
        mContentValues.putNull(MovieColumns.CATEGORY);
        return this;
    }

    public MovieContentValues putLanguage(@Nullable String value) {
        mContentValues.put(MovieColumns.LANGUAGE, value);
        return this;
    }

    public MovieContentValues putLanguageNull() {
        mContentValues.putNull(MovieColumns.LANGUAGE);
        return this;
    }

    public MovieContentValues putOriginaltitle(@Nullable String value) {
        mContentValues.put(MovieColumns.ORIGINALTITLE, value);
        return this;
    }

    public MovieContentValues putOriginaltitleNull() {
        mContentValues.putNull(MovieColumns.ORIGINALTITLE);
        return this;
    }
}
