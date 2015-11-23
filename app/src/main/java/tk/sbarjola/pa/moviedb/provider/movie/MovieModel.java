package tk.sbarjola.pa.moviedb.provider.movie;

import tk.sbarjola.pa.moviedb.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A human being which is part of a team.
 */
public interface MovieModel extends BaseModel {

    /**
     * Get the {@code title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getTitle();

    /**
     * Get the {@code releasedate} value.
     * Can be {@code null}.
     */
    @Nullable
    String getReleasedate();

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPopularity();

    /**
     * Get the {@code posterpath} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPosterpath();

    /**
     * Get the {@code description} value.
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();
}
