package com.arnzen.home_api_backend.controller;

import com.arnzen.home_api_backend.model.delete.DeleteDeviceResponseEntity;
import com.arnzen.home_api_backend.model.delete.DeleteHomeResponseEntity;
import com.arnzen.home_api_backend.model.delete.DeleteLocationResponseEntity;
import com.arnzen.home_api_backend.model.edit.EditRequest;
import com.arnzen.home_api_backend.model.messageResponse.MessageResponse;
import com.arnzen.home_api_backend.model.reducedData.GetHomeResponse;
import com.arnzen.home_api_backend.model.registration.RegisterItem;
import com.arnzen.home_api_backend.service.DeleteService;
import com.arnzen.home_api_backend.service.EditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("edit")
public class EditController {

    @Autowired
    EditService editService;

    // Updates the information of the specified home.
    @PutMapping("home")
    public ResponseEntity<MessageResponse> registerHome(@RequestBody EditRequest home) {
        return editService.editHome(home);
    }
}
