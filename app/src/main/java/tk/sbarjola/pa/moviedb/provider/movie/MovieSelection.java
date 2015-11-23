package tk.sbarjola.pa.moviedb.provider.movie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import tk.sbarjola.pa.moviedb.provider.base.AbstractSelection;

/**
 * Selection for the {@code movie} table.
 */
public class MovieSelection extends AbstractSelection<MovieSelection> {
    @Override
    protected Uri baseUri() {
        return MovieColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MovieCursor} object, which is positioned before the first entry, or null.
     */
    public MovieCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MovieCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public MovieCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code MovieCursor} object, which is positioned before the first entry, or null.
     */
    public MovieCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new MovieCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public MovieCursor query(Context context) {
        return query(context, null);
    }


    public MovieSelection id(long... value) {
        addEquals("movie." + MovieColumns._ID, toObjectArray(value));
        return this;
    }

    public MovieSelection idNot(long... value) {
        addNotEquals("movie." + MovieColumns._ID, toObjectArray(value));
        return this;
    }

    public MovieSelection orderById(boolean desc) {
        orderBy("movie." + MovieColumns._ID, desc);
        return this;
    }

    public MovieSelection orderById() {
        return orderById(false);
    }

    public MovieSelection title(String... value) {
        addEquals(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleNot(String... value) {
        addNotEquals(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleLike(String... value) {
        addLike(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleContains(String... value) {
        addContains(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleStartsWith(String... value) {
        addStartsWith(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection titleEndsWith(String... value) {
        addEndsWith(MovieColumns.TITLE, value);
        return this;
    }

    public MovieSelection orderByTitle(boolean desc) {
        orderBy(MovieColumns.TITLE, desc);
        return this;
    }

    public MovieSelection orderByTitle() {
        orderBy(MovieColumns.TITLE, false);
        return this;
    }

    public MovieSelection releasedate(String... value) {
        addEquals(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateNot(String... value) {
        addNotEquals(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateLike(String... value) {
        addLike(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateContains(String... value) {
        addContains(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateStartsWith(String... value) {
        addStartsWith(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection releasedateEndsWith(String... value) {
        addEndsWith(MovieColumns.RELEASEDATE, value);
        return this;
    }

    public MovieSelection orderByReleasedate(boolean desc) {
        orderBy(MovieColumns.RELEASEDATE, desc);
        return this;
    }

    public MovieSelection orderByReleasedate() {
        orderBy(MovieColumns.RELEASEDATE, false);
        return this;
    }

    public MovieSelection popularity(String... value) {
        addEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public MovieSelection popularityNot(String... value) {
        addNotEquals(MovieColumns.POPULARITY, value);
        return this;
    }

    public MovieSelection popularityLike(String... value) {
        addLike(MovieColumns.POPULARITY, value);
        return this;
    }

    public MovieSelection popularityContains(String... value) {
        addContains(MovieColumns.POPULARITY, value);
        return this;
    }

    public MovieSelection popularityStartsWith(String... value) {
        addStartsWith(MovieColumns.POPULARITY, value);
        return this;
    }

    public MovieSelection popularityEndsWith(String... value) {
        addEndsWith(MovieColumns.POPULARITY, value);
        return this;
    }

    public MovieSelection orderByPopularity(boolean desc) {
        orderBy(MovieColumns.POPULARITY, desc);
        return this;
    }

    public MovieSelection orderByPopularity() {
        orderBy(MovieColumns.POPULARITY, false);
        return this;
    }

    public MovieSelection posterpath(String... value) {
        addEquals(MovieColumns.POSTERPATH, value);
        return this;
    }

    public MovieSelection posterpathNot(String... value) {
        addNotEquals(MovieColumns.POSTERPATH, value);
        return this;
    }

    public MovieSelection posterpathLike(String... value) {
        addLike(MovieColumns.POSTERPATH, value);
        return this;
    }

    public MovieSelection posterpathContains(String... value) {
        addContains(MovieColumns.POSTERPATH, value);
        return this;
    }

    public MovieSelection posterpathStartsWith(String... value) {
        addStartsWith(MovieColumns.POSTERPATH, value);
        return this;
    }

    public MovieSelection posterpathEndsWith(String... value) {
        addEndsWith(MovieColumns.POSTERPATH, value);
        return this;
    }

    public MovieSelection orderByPosterpath(boolean desc) {
        orderBy(MovieColumns.POSTERPATH, desc);
        return this;
    }

    public MovieSelection orderByPosterpath() {
        orderBy(MovieColumns.POSTERPATH, false);
        return this;
    }

    public MovieSelection description(String... value) {
        addEquals(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionNot(String... value) {
        addNotEquals(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionLike(String... value) {
        addLike(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionContains(String... value) {
        addContains(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionStartsWith(String... value) {
        addStartsWith(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection descriptionEndsWith(String... value) {
        addEndsWith(MovieColumns.DESCRIPTION, value);
        return this;
    }

    public MovieSelection orderByDescription(boolean desc) {
        orderBy(MovieColumns.DESCRIPTION, desc);
        return this;
    }

    public MovieSelection orderByDescription() {
        orderBy(MovieColumns.DESCRIPTION, false);
        return this;
    }
}
