package com.example.WeibisWeb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {

    int responseSize;
    T responseData;
}
