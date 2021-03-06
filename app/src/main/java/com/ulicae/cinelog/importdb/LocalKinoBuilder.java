package com.ulicae.cinelog.importdb;

import android.annotation.SuppressLint;

import com.ulicae.cinelog.dao.LocalKino;
import com.ulicae.cinelog.dao.TmdbKino;

import org.apache.commons.csv.CSVRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CineLog Copyright 2018 Pierre Rognon
 *
 *
 * This file is part of CineLog.
 * CineLog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CineLog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CineLog. If not, see <https://www.gnu.org/licenses/>.
 *
 */
class LocalKinoBuilder {

    LocalKino build(CSVRecord csvRecord) throws ImportException {
        try {
            TmdbKino tmdbKino = new TmdbKino();
            tmdbKino.setPoster_path(csvRecord.get("poster_path"));
            tmdbKino.setMovie_id(formatLong(csvRecord.get("movie_id")));
            tmdbKino.setOverview(csvRecord.get("overview"));
            tmdbKino.setRelease_date(csvRecord.get("release_date"));
            tmdbKino.setYear(formatInteger(csvRecord.get("year")));

            return new LocalKino(
                    formatFloat(csvRecord.get("rating")),
                    csvRecord.get("review"),
                    csvRecord.get("title"),
                    formatDate(csvRecord.get("review_date")),
                    tmdbKino
            );
        } catch (ParseException e) {
            throw new ImportException(String.format("Can't save import movie with name %s.", csvRecord.get("title")), e);
        }
    }

    private int formatInteger(String integerToFormat) {
        return integerToFormat != null && !integerToFormat.isEmpty() ? Integer.parseInt(integerToFormat) : 0;
    }

    private long formatLong(String longToFormat) {
        return longToFormat != null && !longToFormat.isEmpty() ? Long.parseLong(longToFormat) : 0;
    }

    @SuppressLint("SimpleDateFormat")
    private Date formatDate(String dateToFormat) throws ParseException {
        return dateToFormat != null && !dateToFormat.isEmpty() ? new SimpleDateFormat().parse(dateToFormat) : null;
    }

    private float formatFloat(String rating) {
        return rating != null && !rating.isEmpty() ? Float.parseFloat(rating.replace(",", ".")) : 0f;
    }
}
