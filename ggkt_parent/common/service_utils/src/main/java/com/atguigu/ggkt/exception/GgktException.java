package com.atguigu.ggkt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nancheng
 * @version 1.0
 * @date 2022/10/23 20:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GgktException extends RuntimeException{
    private Integer code;
    private String msg;
}
