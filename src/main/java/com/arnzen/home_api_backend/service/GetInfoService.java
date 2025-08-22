package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.DeviceDao;
import com.arnzen.home_api_backend.dao.HomeDao;
import com.arnzen.home_api_backend.model.DeviceEntity;
import com.arnzen.home_api_backend.model.HomeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetInfoService {

    @Autowired
    HomeDao homeDao;

    @Autowired
    DeviceDao deviceDao;

    public ResponseEntity<List<HomeEntity>> getHomesByUser(int userId) {
        List<HomeEntity> homes = homeDao.findByUserEntityId(userId);

        if (homes.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(homes, HttpStatus.OK);
        }
    }

    public ResponseEntity<List<DeviceEntity>> getDevicesByLocation(int locationId) {

        List<DeviceEntity> devices = deviceDao.findByLocationEntityId(locationId);

        if (devices.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(devices, HttpStatus.OK);
        }
    }
}
