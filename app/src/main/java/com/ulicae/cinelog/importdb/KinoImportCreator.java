package com.ulicae.cinelog.importdb;

import com.ulicae.cinelog.dao.LocalKino;

import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
class KinoImportCreator {
    private CSVFormatWrapper csvFormatWrapper;
    private LocalKinoBuilder localKinoBuilder;

    KinoImportCreator() {
        this(new CSVFormatWrapper(), new LocalKinoBuilder());
    }

    KinoImportCreator(CSVFormatWrapper csvFormatWrapper, LocalKinoBuilder localKinoBuilder) {
        this.csvFormatWrapper = csvFormatWrapper;
        this.localKinoBuilder = localKinoBuilder;
    }

    List<LocalKino> getKinos(FileReader fileReader) throws ImportException {
        Iterable<CSVRecord> csvRecords;
        try {
            csvRecords = csvFormatWrapper.parse(fileReader);
        } catch (IOException e) {
            throw new ImportException("Error while parsing CSV file. Please verify it.", e);
        }

        List<LocalKino> kinos = new ArrayList<>();
        for (CSVRecord csvRecord : csvRecords) {
            kinos.add(localKinoBuilder.build(csvRecord));
        }

        return kinos;
    }

}
