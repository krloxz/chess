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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.krloxz.chess.player.Command;

/**
 * Unit tests {@link Player}.
 *
 * @author Carlos Gomez
 */
public class PlayerTest {

    private Player player;
    private PlayerStrategy strategy;
    private Board board;
    private Player opponent;

    @Before
    public void setUp() {
        this.strategy = mock(PlayerStrategy.class);
        this.board = mock(Board.class);
        this.opponent = mock(Player.class);
        this.player = new Player(this.strategy, this.board, this.opponent);
    }

    @Test
    public void play() {
        // Arrange
        final Command command = mock(Command.class);
        when(this.strategy.play(this.board))
                .thenReturn(command);

        // Act
        this.player.play();

        // Assert
        verify(command).execute(this.player);
    }

    @Test
    public void acceptDraw() {
        // Arrange
        final boolean acceptDraw = true;
        when(this.strategy.acceptDraw()).thenReturn(acceptDraw);

        // Act
        final boolean result = this.player.acceptDraw();

        // Assert
        assertEquals(acceptDraw, result);
    }

    @Test
    public void gameOver() {
        // Arrange
        final GameState gameState = GameState.DRAW_BY_AGREEMENT;

        // Act
        this.player.gameOver(gameState);

        // Assert
        verify(this.strategy).gameOver(gameState);
    }

}
