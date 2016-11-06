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
public class Piece {

    private final PieceBehavior behavior;
    private final Square position;

    /**
     * @param behavior
     * @param initialPosition
     */
    public Piece(final PieceBehavior behavior, final Square initialPosition) {
        this.behavior = behavior;
        this.position = initialPosition;
    }

    /**
     * @param target
     * @param board
     * @return
     */
    public boolean move(final Square target, final Board board) {
        if (this.behavior.isLegalMove(this.position, target, board)) {
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void captured() {
        // TODO Auto-generated method stub

    }

}
