package red.fuyun.aspect;

import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import red.fuyun.model.R;
import red.fuyun.model.ReturnCode;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {



    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<R<String>> handleValidatedException(Exception e) {
        R<String> resp = null;

        if (e instanceof MethodArgumentNotValidException) {
            // BeanValidation exception
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            resp = R.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getBindingResult().getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof ConstraintViolationException) {
            // BeanValidation GET simple param
            ConstraintViolationException ex = (ConstraintViolationException) e;
            resp = R.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof BindException) {
            // BeanValidation GET object param
            BindException ex = (BindException) e;
            resp = R.fail(HttpStatus.BAD_REQUEST.value(),
                    ex.getAllErrors().stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining("; "))
            );
        }

        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
    }


    /**
     * 默认Feign 调用异常处理。
     * @param e the e
     * @return ResultData
     */
//    @ExceptionHandler(value = {
//            FeignException.BadRequest.class,
//            FeignException.Unauthorized.class,
//            FeignException.BadGateway.class,
//            FeignException.Conflict.class,
//            FeignException.FeignClientException.class,
//            FeignException.FeignServerException.class,
//            FeignException.Forbidden.class,
//            FeignException.GatewayTimeout.class,
//            FeignException.Gone.class,
//            FeignException.InternalServerError.class,
//            FeignException.MethodNotAllowed.class,
//            FeignException.NotAcceptable.class,
//            FeignException.NotFound.class,
//            FeignException.NotImplemented.class,
//            FeignException.UnsupportedMediaType.class,
//            FeignException.ServiceUnavailable.class,
//            FeignException.TooManyRequests.class,
//            FeignException.UnprocessableEntity.class
//
//    })
//    @ExceptionHandler(FeignException.class)
//    @ExceptionHandler(FeignException.BadRequest.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public R<String> FeignExceptionBadRequest(FeignException.BadRequest e) {

//    }


    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> FeignExceptionBadRequest(FeignException e) {

        String contentUTF8 = e.contentUTF8();
        String error_description = "未知错误!";
        if (!Strings.isBlank(contentUTF8)){
            log.error("FeignExceptionBadRequest异常contentUTF8信息 ex={}", contentUTF8);
            JSONObject content = JSONObject.parseObject(contentUTF8);
            log.error("FeignExceptionBadRequest异常content信息 ex={}", content.toJSONString());
            error_description = content.getString("error_description");
        }

        return R.fail(ReturnCode.RC500.getCode(),error_description);
    }

    private static FeignException.FeignClientException clientErrorStatus(int status, String message, Request request, byte[] body) {
        switch(status) {
            case 400:
                return new FeignException.BadRequest(message, request, body);
            case 401:
                return new FeignException.Unauthorized(message, request, body);
            case 402:
            case 407:
            case 408:
            case 411:
            case 412:
            case 413:
            case 414:
            case 416:
            case 417:
            case 418:
            case 419:
            case 420:
            case 421:
            case 423:
            case 424:
            case 425:
            case 426:
            case 427:
            case 428:
            default:
                return new FeignException.FeignClientException(status, message, request, body);
            case 403:
                return new FeignException.Forbidden(message, request, body);
            case 404:
                return new FeignException.NotFound(message, request, body);
            case 405:
                return new FeignException.MethodNotAllowed(message, request, body);
            case 406:
                return new FeignException.NotAcceptable(message, request, body);
            case 409:
                return new FeignException.Conflict(message, request, body);
            case 410:
                return new FeignException.Gone(message, request, body);
            case 415:
                return new FeignException.UnsupportedMediaType(message, request, body);
            case 422:
                return new FeignException.UnprocessableEntity(message, request, body);
            case 429:
                return new FeignException.TooManyRequests(message, request, body);
        }
    }

    private static boolean isServerError(int status) {
        return status >= 500 && status <= 599;
    }

    private static FeignException.FeignServerException serverErrorStatus(int status, String message, Request request, byte[] body) {
        switch(status) {
            case 500:
                return new FeignException.InternalServerError(message, request, body);
            case 501:
                return new FeignException.NotImplemented(message, request, body);
            case 502:
                return new FeignException.BadGateway(message, request, body);
            case 503:
                return new FeignException.ServiceUnavailable(message, request, body);
            case 504:
                return new FeignException.GatewayTimeout(message, request, body);
            default:
                return new FeignException.FeignServerException(status, message, request, body);
        }
    }


    /**
     * 默认全局异常处理。
     * @param e the e
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<String> exception(Exception e) {

        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return R.fail(ReturnCode.RC500.getCode(),e.getMessage());
    }



}
