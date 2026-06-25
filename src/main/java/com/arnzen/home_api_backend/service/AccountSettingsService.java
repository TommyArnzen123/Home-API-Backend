package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.AccountSettingsDao;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.accountSettings.AccountSettingsException;
import com.arnzen.home_api_backend.model.accountSettings.UpdateTemperatureDisplaySettingRequest;
import com.arnzen.home_api_backend.model.accountSettings.UpdateTimeDisplaySettingRequest;
import com.arnzen.home_api_backend.model.base.AccountSettingsEntity;
import com.arnzen.home_api_backend.model.reducedData.GetAccountSettingsResponse;
import com.arnzen.home_api_backend.model.reducedData.UpdateTemperatureDisplaySettingResponse;
import com.arnzen.home_api_backend.model.reducedData.UpdateTimeDisplaySettingResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountSettingsService {

    @Autowired
    AccountSettingsDao accountSettingsDao;

    public ResponseEntity<GetAccountSettingsResponse> getAccountSettings(int userId) {
        String userNotFoundExceptionMessage = "There was an error getting the account settings.";
        Optional<AccountSettingsEntity> accountSettings = accountSettingsDao.findByUserEntityId(userId);

        if (accountSettings.isPresent()) {
            // Generate an object to return needed account settings information for the
            // specified userId.
            GetAccountSettingsResponse response = new GetAccountSettingsResponse(
                    accountSettings.get().getId(),
                    accountSettings.get().getUserEntity().getId(),
                    accountSettings.get().getTimeDisplaySetting(),
                    accountSettings.get().getTemperatureDisplaySetting()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // There is not an account settings entity for the specified userId, return an error.
            throw new AccountSettingsException(userNotFoundExceptionMessage);
        }
    }

    @Transactional
    public ResponseEntity<UpdateTimeDisplaySettingResponse> updateTimeDisplaySetting(
            UpdateTimeDisplaySettingRequest timeDisplayRequest) {
        String timeDisplayExceptionMessage = "There was an error updating the time display setting.";
        try {
            Optional<AccountSettingsEntity> accountSettingsEntity
                    = accountSettingsDao.findByUserEntityId(timeDisplayRequest.getUserId());

            // The specified account settings entity exists.
            if (accountSettingsEntity.isPresent()) {

                // Update the time display setting for the specified user.
                accountSettingsEntity.get().setTimeDisplaySetting(timeDisplayRequest.getSetting());

                // Save the time display setting for the specified user.
                AccountSettingsEntity savedEntity = accountSettingsDao.save(accountSettingsEntity.get());

                // Generate a new time display setting response.
                UpdateTimeDisplaySettingResponse response =
                        new UpdateTimeDisplaySettingResponse(
                                savedEntity.getUserEntity().getId(),
                                savedEntity.getId(),
                                savedEntity.getTimeDisplaySetting()
                        );

                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                // The specified account settings entity was not found. Return an error.
                throw new AccountSettingsException(timeDisplayExceptionMessage);

            }

        } catch (AccountSettingsException exception) {
            throw exception;    // Throw any UpdateSettingExceptions to the Global Error Handler.
        } catch (Exception exception) {
            throw new AccountSettingsException(timeDisplayExceptionMessage);
        }
    }

    @Transactional
    public ResponseEntity<UpdateTemperatureDisplaySettingResponse> updateTemperatureDisplaySetting(
            UpdateTemperatureDisplaySettingRequest temperatureDisplayRequest) {
        String temperatureDisplayExceptionMessage = "There was an error updating the temperature display setting.";
        try {
            Optional<AccountSettingsEntity> accountSettingsEntity
                    = accountSettingsDao.findByUserEntityId(temperatureDisplayRequest.getUserId());

            // The specified account settings entity exists.
            if (accountSettingsEntity.isPresent()) {

                // Update the temperature display setting for the specified user.
                accountSettingsEntity.get().setTemperatureDisplaySetting(temperatureDisplayRequest.getSetting());

                // Save the temperature display setting for the specified user.
                AccountSettingsEntity savedEntity = accountSettingsDao.save(accountSettingsEntity.get());

                // Generate a new time display setting response.
                UpdateTemperatureDisplaySettingResponse response =
                        new UpdateTemperatureDisplaySettingResponse(
                                savedEntity.getUserEntity().getId(),
                                savedEntity.getId(),
                                savedEntity.getTemperatureDisplaySetting()
                        );

                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                // The specified account settings entity was not found. Return an error.
                throw new AccountSettingsException(temperatureDisplayExceptionMessage);
            }
        } catch (AccountSettingsException exception) {
            throw exception;    // Throw any UpdateSettingExceptions to the Global Error Handler.
        } catch (Exception exception) {
            throw new AccountSettingsException(temperatureDisplayExceptionMessage);
        }
    }
}
