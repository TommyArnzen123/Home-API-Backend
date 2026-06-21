package com.arnzen.home_api_backend.controller;
import com.arnzen.home_api_backend.model.emailConfirmation.confirmCode.ConfirmCodeRequest;
import com.arnzen.home_api_backend.model.emailConfirmation.ConfirmCodeResponse;
import com.arnzen.home_api_backend.model.messageResponse.MessageResponse;
import com.arnzen.home_api_backend.service.EmailConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("confirmEmail")
public class EmailConfirmationController {

    @Autowired
    EmailConfirmationService emailConfirmationService;

    @GetMapping("generateEmailConfirmationCode/{userId}")
    public ResponseEntity<ConfirmCodeResponse> generateEmailConfirmationCode(@PathVariable int userId) {
        return emailConfirmationService.generateEmailConfirmationCode(userId);
    }

    @PostMapping("confirmCode")
    public ResponseEntity<MessageResponse> confirmCode(@RequestBody ConfirmCodeRequest confirmationRequest) {
        return emailConfirmationService.confirmCode(confirmationRequest);
    }
}
