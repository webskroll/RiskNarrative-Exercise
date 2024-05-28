package com.trunarrative.exercise.searchcompany.backend.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Creates a Pair
 * @param <F>
 * @param <S>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class Pair<F, S> {

    private F first;
    private S second;
}
