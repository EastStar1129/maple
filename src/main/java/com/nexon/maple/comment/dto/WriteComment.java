package com.nexon.maple.comment.dto;

public record WriteComment (
        Long characterId,
        Long userId,
        String comment
) {}
