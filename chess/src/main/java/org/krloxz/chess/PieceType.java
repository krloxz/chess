/*
 * Copyright 2016 Carlos Gomez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.krloxz.chess;

/**
 * @author Carlos Gomez
 */
public enum PieceType {

    PAWN(""),
    KNIGHT("N"),
    QUEEN("Q");

    private final String sanAbbreviation;

    private PieceType(final String sanAbbreviation) {
        this.sanAbbreviation = sanAbbreviation;
    }

    public static PieceType fromSanAbbreviation(final String abbreviation) {
        for (final PieceType type : PieceType.values()) {
            if (abbreviation.equals(type.sanAbbreviation)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Illegal SAN abbreviation: " + abbreviation);
    }

}
