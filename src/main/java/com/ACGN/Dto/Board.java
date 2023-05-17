package com.ACGN.Dto;

import lombok.Data;

@Data
public class Board {
    private int boardId;
    private String boardName;

    public Board(int boardId, String boardName) {
        this.boardId = boardId;
        this.boardName = boardName;
    }
}
