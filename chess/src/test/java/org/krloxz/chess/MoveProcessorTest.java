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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests {@link MoveProcessor}.
 *
 * @author Carlos Gomez
 */
public class MoveProcessorTest {

    private MoveProcessor processor;
    private RulesBook book;
    private Move move;
    private Board board;
    private Piece pieceAtSource;
    private Piece pieceAtTarget;

    @Before
    public void setUp() {
        this.book = mock(RulesBook.class);
        this.processor = new MoveProcessor(this.book);
        this.move = new Move(mock(Square.class), mock(Square.class));
        this.board = mock(Board.class);
        this.pieceAtSource = mock(Piece.class);
        this.pieceAtTarget = mock(Piece.class);
    }

    @Test
    public void process() {
        // Arrange
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.isPathClear(this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.move.getTarget()))
                .thenReturn(Optional.empty());

        // Act
        final boolean processed = this.processor.process(this.move, this.board);

        // Assert
        assertTrue("Move must be accepted and processed ", processed);
    }

    @Test
    public void processMovesPieceAtSource() {
        // Arrange
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.isPathClear(this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.move.getTarget()))
                .thenReturn(Optional.empty());

        // Act
        this.processor.process(this.move, this.board);

        // Assert
        verify(this.pieceAtSource).move(this.move.getTarget());
    }

    @Test
    public void processCapturesPieceAtTarget() {
        // Arrange
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.isPathClear(this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.move.getTarget()))
                .thenReturn(Optional.of(this.pieceAtTarget));
        when(this.pieceAtSource.isFellow(this.pieceAtTarget))
                .thenReturn(false);

        // Act
        this.processor.process(this.move, this.board);

        // Assert
        verify(this.pieceAtTarget).captured();
    }

    @Test
    public void processOnPieceNotFoundAtSource() {
        // Arrange
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.empty());

        // Act
        final boolean processed = this.processor.process(this.move, this.board);

        // Assert
        assertFalse("Move must not be processed", processed);
    }

    @Test
    public void processOnIllegalMove() {
        // Arrange
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(false);

        // Act
        final boolean processed = this.processor.process(this.move, this.board);

        // Assert
        assertFalse("Move must not be processed", processed);
    }

    @Test
    public void processOnPathNotClear() {
        // Arrange
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.isPathClear(this.move.getSource(), this.move.getTarget()))
                .thenReturn(false);

        // Act
        final boolean processed = this.processor.process(this.move, this.board);

        // Assert
        assertFalse("Move must not be processed", processed);
    }

    @Test
    public void processOnTargetOccupiedByFellow() {
        // Arrange
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.isPathClear(this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.move.getTarget()))
                .thenReturn(Optional.of(this.pieceAtTarget));
        when(this.pieceAtSource.isFellow(this.pieceAtTarget))
                .thenReturn(true);

        // Act
        final boolean processed = this.processor.process(this.move, this.board);

        // Assert
        assertFalse("Move must not be processed", processed);
    }

    @Test
    public void processOnKingInCheck() {
        // Arrange
        final PieceMemento sourceMemento = mock(PieceMemento.class);
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.isPathClear(this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.move.getTarget()))
                .thenReturn(Optional.empty());
        when(this.board.isKingInCheck())
                .thenReturn(true);
        when(this.pieceAtSource.move(this.move.getTarget()))
                .thenReturn(sourceMemento);

        // Act
        final boolean processed = this.processor.process(this.move, this.board);

        // Assert
        assertFalse("Move must not be processed", processed);
    }

    @Test
    public void processOnKingInCheckRestoresPieceAtSource() {
        // Arrange
        final PieceMemento sourceMemento = mock(PieceMemento.class);
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.isPathClear(this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.move.getTarget()))
                .thenReturn(Optional.empty());
        when(this.pieceAtSource.move(this.move.getTarget()))
                .thenReturn(sourceMemento);
        when(this.board.isKingInCheck())
                .thenReturn(true);

        // Act
        this.processor.process(this.move, this.board);

        // Assert
        verify(this.pieceAtSource).restoreTo(sourceMemento);
    }

    @Test
    public void processOnKingInCheckRestoresPieceAtTarget() {
        // Arrange
        final PieceMemento targetMemento = mock(PieceMemento.class);
        when(this.board.getPieceAt(this.move.getSource()))
                .thenReturn(Optional.of(this.pieceAtSource));
        when(this.book.isLegalMove(this.pieceAtSource.getType(), this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.isPathClear(this.move.getSource(), this.move.getTarget()))
                .thenReturn(true);
        when(this.board.getPieceAt(this.move.getTarget()))
                .thenReturn(Optional.of(this.pieceAtTarget));
        when(this.pieceAtSource.isFellow(this.pieceAtTarget))
                .thenReturn(false);
        when(this.pieceAtTarget.captured())
                .thenReturn(targetMemento);
        when(this.board.isKingInCheck())
                .thenReturn(true);

        // Act
        this.processor.process(this.move, this.board);

        // Assert
        verify(this.pieceAtTarget).restoreTo(targetMemento);
    }

}
