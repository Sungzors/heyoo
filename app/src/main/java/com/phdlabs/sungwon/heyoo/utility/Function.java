package com.phdlabs.sungwon.heyoo.utility;

/**
 * Created by SungWon on 4/25/2017.
 */

public interface Function<Input, Result> {
    Result apply(Input inputValue);
}
