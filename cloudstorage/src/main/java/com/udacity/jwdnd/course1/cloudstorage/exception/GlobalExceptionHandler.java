package com.udacity.jwdnd.course1.cloudstorage.exception;

import com.udacity.jwdnd.course1.cloudstorage.Constants;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handle file size exceeding limit
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, Model model) {
        model.addAttribute("success", false);
        model.addAttribute("message",  Constants.FILE_SIZE_EXCEEDED_ERROR);
        return new ModelAndView("result");
    }
}
