package tk.sbarjola.pa.moviedb.provider.movie;

import android.net.Uri;
import android.provider.BaseColumns;

import tk.sbarjola.pa.moviedb.provider.MovieProvider;
import tk.sbarjola.pa.moviedb.provider.movie.MovieColumns;

/**
 * A human being which is part of a team.
 */
public class MovieColumns implements BaseColumns {
    public static final String TABLE_NAME = "movie";
    public static final Uri CONTENT_URI = Uri.parse(MovieProvider.CONTENT_URI_BASE + "/" + TABLE_NAME);

    /**
     * Primary key.
     */
    public static final String _ID = BaseColumns._ID;

    public static final String TITLE = "title";

    public static final String RELEASEDATE = "releaseDate";

    public static final String POPULARITY = "popularity";

    public static final String POSTERPATH = "posterpath";

    public static final String DESCRIPTION = "description";

    public static final String CATEGORY = "category";

    public static final String LANGUAGE = "language";

    public static final String ORIGINALTITLE = "originalTitle";


    public static final String DEFAULT_ORDER = TABLE_NAME + "." +_ID;

    // @formatter:off
    public static final String[] ALL_COLUMNS = new String[] {
            _ID,
            TITLE,
            RELEASEDATE,
            POPULARITY,
            POSTERPATH,
            DESCRIPTION,
            CATEGORY,
            LANGUAGE,
            ORIGINALTITLE
    };
    // @formatter:on

    public static boolean hasColumns(String[] projection) {
        if (projection == null) return true;
        for (String c : projection) {
            if (c.equals(TITLE) || c.contains("." + TITLE)) return true;
            if (c.equals(RELEASEDATE) || c.contains("." + RELEASEDATE)) return true;
            if (c.equals(POPULARITY) || c.contains("." + POPULARITY)) return true;
            if (c.equals(POSTERPATH) || c.contains("." + POSTERPATH)) return true;
            if (c.equals(DESCRIPTION) || c.contains("." + DESCRIPTION)) return true;
            if (c.equals(CATEGORY) || c.contains("." + CATEGORY)) return true;
            if (c.equals(LANGUAGE) || c.contains("." + LANGUAGE)) return true;
            if (c.equals(ORIGINALTITLE) || c.contains("." + ORIGINALTITLE)) return true;
        }
        return false;
    }

}
