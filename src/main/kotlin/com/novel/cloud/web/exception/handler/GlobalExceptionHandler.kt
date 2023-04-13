package com.novel.cloud.web.exception.handler

import com.novel.cloud.web.endpoint.ErrorResponse
import com.novel.cloud.web.exception.GeneralHttpException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(GeneralHttpException::class)
    fun generalHttpExceptionHandler(e: GeneralHttpException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            statusCode = e.httpStatus.value(),
            reason = e.httpStatus.reasonPhrase,
            message = e.message
        )
        return ResponseEntity(errorResponse, e.httpStatus)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableExceptionHandler(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            reason = e.message,
            message = "Json 타입 메시지를 읽을 수 없습니다."
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            statusCode = HttpStatus.BAD_REQUEST.value(),
            reason = e.message,
            message = "형식에 맞지 않은 요청입니다."
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(e: RuntimeException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            reason = e.message,
            message = "알 수 없는 에러입니다. 서버 관리자에게 문의 하세요."
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}