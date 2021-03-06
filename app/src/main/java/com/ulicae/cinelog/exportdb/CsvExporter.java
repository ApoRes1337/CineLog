package com.ulicae.cinelog.exportdb;

import com.ulicae.cinelog.dao.LocalKino;
import com.ulicae.cinelog.db.LocalKinoRepository;

import java.io.IOException;
import java.util.List;

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
public class CsvExporter {
    private LocalKinoRepository localKinoRepository;
    private final CsvExportWriter csvExportWriter;

    public CsvExporter(LocalKinoRepository localKinoRepository, Appendable out) throws IOException {
        this(localKinoRepository, new CsvExportWriter(out));
    }

    CsvExporter(LocalKinoRepository localKinoRepository, CsvExportWriter csvExportWriter) {
        this.localKinoRepository = localKinoRepository;
        this.csvExportWriter = csvExportWriter;
    }

    public void export() throws IOException {
        List<LocalKino> localKinoList = localKinoRepository.findAll();

        for (LocalKino localKino : localKinoList) {
            csvExportWriter.write(localKino);
        }

        csvExportWriter.endWriting();
    }
}
