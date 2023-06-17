package edu.seu.mtyx.common.exception;

import edu.seu.mtyx.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<String> error(Exception e){
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(MtyxException.class)
    @ResponseBody
    public Result<String> error(MtyxException e){
        return Result.build(null, e.getCode(), e.getMessage());
    }
}
