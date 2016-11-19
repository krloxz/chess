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
public class Rook extends Piece {

    /**
     * @param color
     */
    public Rook(final Color color) {
        super(color);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.krloxz.chess.Piece#confirmLegalMove(org.krloxz.chess.Move, org.krloxz.chess.Board)
     */
    @Override
    protected boolean confirmLegalMove(final BasicMovement move, final Board board) {
        return isLegalMove(move);
    }

    /**
     * @param move
     * @return
     */
    protected static boolean isLegalMove(final BasicMovement move) {
        return move.getSource().getX() == move.getTarget().getX()
                || move.getSource().getY() == move.getTarget().getY();
    }

    /**
     * @return
     */
    public boolean castle() {
        // TODO Auto-generated method stub
        return false;
    }

}