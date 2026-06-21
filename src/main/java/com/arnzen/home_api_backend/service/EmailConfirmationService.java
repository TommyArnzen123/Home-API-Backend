package com.arnzen.home_api_backend.service;

import com.arnzen.home_api_backend.dao.EmailConfirmationDao;
import com.arnzen.home_api_backend.dao.UserDao;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.confirmCode.ConfirmCodeErrorTypes;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.confirmCode.ConfirmCodeException;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.generateCode.GenerateCodeErrorTypes;
import com.arnzen.home_api_backend.model.emailConfirmation.confirmCode.ConfirmCodeRequest;
import com.arnzen.home_api_backend.model.base.EmailConfirmationEntity;
import com.arnzen.home_api_backend.model.base.UserEntity;
import com.arnzen.home_api_backend.globalExceptionHandler.customErrors.emailConfirmation.generateCode.GenerateCodeException;
import com.arnzen.home_api_backend.model.emailConfirmation.ConfirmCodeResponse;
import com.arnzen.home_api_backend.model.messageResponse.MessageResponse;
import com.arnzen.home_api_backend.service.notification.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmailConfirmationService {

    @Autowired
    EmailService emailService;

    @Autowired
    UserDao userDao;

    @Autowired
    EmailConfirmationDao emailConfirmationDao;

    private final int maximumConfirmationAttempts = 5;

    public ResponseEntity<ConfirmCodeResponse> generateEmailConfirmationCode(int userId) {

        try {
            Optional<UserEntity> user = userDao.findById(userId);

            // Check 1: Verify the specified user exists.
            if (user.isEmpty()) {
                // The specified user was not found. Throw an error.
                throw new GenerateCodeException(GenerateCodeErrorTypes.BAD_REQUEST);
            }

            // Check 2: Verify the user's email address is not already confirmed.
            if (user.get().isEmailConfirmed()) {
                // The user's email address is already confirmed. Throw an error.
                throw new GenerateCodeException(GenerateCodeErrorTypes.EMAIL_ALREADY_CONFIRMED);
            }

            // Set all previous email confirmation codes for the specified user ID to
            // be not active.
            emailConfirmationDao.updateStatusByUserId(userId, false);

            // Get the three most recent email confirmation code entities generated
            // in the past 30 minutes.
            List<EmailConfirmationEntity> confirmationEntities =
                    emailConfirmationDao.findAllCreatedEntitiesInPast30Minutes(
                            user.get().getId(),
                            LocalDateTime.now().minusMinutes(30),
                            PageRequest.of(0,3));

            // Check 3: Make sure the user has not made three email confirmation code
            //          generation requests in the past 30 minutes.
            if (confirmationEntities.size() >= 3) {
                // The user has made three confirmation code generation requests in the past 30 minutes.
                // Return an error with information about the third most recent confirmation code entity.
                // The third most recent confirmation code entity's createdAtDate field will determine
                // when the next confirmation code generation request can be made (30 minutes after).
                long timeUntilAvailable = generateSecondsUntilEmailCodeConfirmationAvailable(confirmationEntities.get(2));
                throw new GenerateCodeException(GenerateCodeErrorTypes.TOO_MANY_REQUESTS, timeUntilAvailable);
            }


            // Generate a random 6-digit number.
            int confirmationCode = generateRandomEmailConfirmationCode();

            // Send email message with email confirmation code to the email
            // used to register the account associated with the specified user ID.
            emailService.sendEmailAddressConfirmationEmail(user.get(), confirmationCode);

            // Generate a hashed version of the email confirmation code to be
            // stored in the database.
            String confirmationCodeHash = generateEmailConfirmationCodeHash(confirmationCode);

            // Generate a new email confirmation code entity.
            EmailConfirmationEntity entity = generateEmailConfirmationCodeEntity(user.get(), confirmationCodeHash);

            // Save the new email confirmation code entity in the database.
            EmailConfirmationEntity newEntity = emailConfirmationDao.save(entity);

            return new ResponseEntity<>(generateEmailConfirmationCodeResponse(newEntity), HttpStatus.OK);

        } catch (GenerateCodeException exception) {
            throw exception;    // Throw any GenerateCodeExceptions to the Global Error Handler.
        } catch (Exception exception) {
            throw new GenerateCodeException(GenerateCodeErrorTypes.BAD_REQUEST);
        }
    }

    private int generateRandomEmailConfirmationCode() {
        // Generate a random 6-digit number.
        Random rand = new Random();
        return 100000 + rand.nextInt(900000);
    }

    private String generateEmailConfirmationCodeHash(int emailConfirmationCode) throws Exception {
        // Generate a hashed version of the email confirmation code to be
        // stored in the database.
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] intBytes = ByteBuffer.allocate(Integer.BYTES).putInt(emailConfirmationCode).array();
        byte[] secureHash = digest.digest(intBytes);

        StringBuilder hashCode = new StringBuilder();
        for (byte b : secureHash) {
            hashCode.append(String.format("%02x", b));
        }

        return hashCode.toString();
    }

    private EmailConfirmationEntity generateEmailConfirmationCodeEntity(UserEntity user,
                                                                        String confirmationCodeHash) {
        EmailConfirmationEntity entity = new EmailConfirmationEntity();
        entity.setUserEntity(user);
        entity.setEmailConfirmationCode(confirmationCodeHash);
        LocalDateTime currentTime = LocalDateTime.now();
        entity.setCreatedAtDateTime(currentTime);
        entity.setExpiresAtDateTime(currentTime.plusMinutes(5));
        entity.setActive(true);
        entity.setIncorrectAttempts(0);

        return entity;
    }

    private ConfirmCodeResponse generateEmailConfirmationCodeResponse(EmailConfirmationEntity entity) {
        ConfirmCodeResponse response = new ConfirmCodeResponse();
        response.setConfirmationId(entity.getId());
        response.setExpiresAt(entity.getExpiresAtDateTime());
        response.setAttemptsRemaining(maximumConfirmationAttempts - entity.getIncorrectAttempts());
        return response;
    }

    private long generateSecondsUntilEmailCodeConfirmationAvailable(EmailConfirmationEntity entity) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime entityCreatedAtTime = entity.getCreatedAtDateTime();
        LocalDateTime entityLifeExpired = entityCreatedAtTime.plusMinutes(30);
        return ChronoUnit.SECONDS.between(currentTime, entityLifeExpired);
    }

    public ResponseEntity<MessageResponse> confirmCode(ConfirmCodeRequest confirmationRequest) {


        try {
            Optional<UserEntity> user = userDao.findById(confirmationRequest.getUserId());

            // Check 1: Verify specified user exists.
            if (user.isEmpty()) {
                // The specified user does not exist. Throw an error.
                // NO_USER
                throw new ConfirmCodeException(ConfirmCodeErrorTypes.BAD_REQUEST);
            }

            Optional<EmailConfirmationEntity> code =
                    emailConfirmationDao.findById(confirmationRequest.getConfirmationId());

            // Check 2: Verify specified confirmation code entity exists.
            if (code.isEmpty()) {
                // The specified code does not exist. Throw an error.
                // NO_CODE
                throw new ConfirmCodeException(ConfirmCodeErrorTypes.BAD_REQUEST);
            }

            // Check 3: Verify specified user ID and specified email confirmation entity match
            //          (e.g. the email confirmation entity was generated for the specified user ID).
            if (user.get().getId() != code.get().getUserEntity().getId()) {
                // The specified user ID does not match the user ID of the specified email confirmation entity.
                // BAD_REQUEST
                throw new ConfirmCodeException(ConfirmCodeErrorTypes.BAD_REQUEST);
            }

            // Check 4: Verify the code is active.
            if (!code.get().isActive()) {
                // The specified code is not active. Throw an error.
                // NOT_ACTIVE
                throw new ConfirmCodeException(ConfirmCodeErrorTypes.NOT_ACTIVE);
            }

            LocalDateTime verifiedAt = code.get().getConfirmedAtDateTime();

            // Check 5: Verify the code has not been confirmed.
            if (verifiedAt != null) {
                code.get().setActive(false);
                emailConfirmationDao.save(code.get());
                // The specified code is already confirmed. Throw an error.
                // ALREADY_CONFIRMED
                throw new ConfirmCodeException(ConfirmCodeErrorTypes.ALREADY_CONFIRMED);
            }

            LocalDateTime expiresAt = code.get().getExpiresAtDateTime();
            LocalDateTime now = LocalDateTime.now();

            // Check 6: Make sure the confirmation code is not expired.
            if (expiresAt.isBefore(now)) {
                code.get().setActive(false);
                emailConfirmationDao.save(code.get());
                // The specified code is expired. Throw an error.
                // CODE_EXPIRED
                throw new ConfirmCodeException(ConfirmCodeErrorTypes.CODE_EXPIRED);
            }

            int failedAttempts = code.get().getIncorrectAttempts();

            // Check 7: Make sure failed attempts is less than five
            //          for the confirmation code entity.
            if (failedAttempts >= 5) {
                code.get().setActive(false);
                emailConfirmationDao.save(code.get());
                // The confirmation code has reach the maximum of
                // five failed confirmation attempts. Throw an error.
                // TOO_MANY_ATTEMPTS
                throw new ConfirmCodeException(ConfirmCodeErrorTypes.TOO_MANY_ATTEMPTS);
            }

            String storedCode = code.get().getEmailConfirmationCode();
            String userEnteredCode =
                    generateEmailConfirmationCodeHash(confirmationRequest.getEmailConfirmationCode());

            // Check 8: User entered email confirmation code must
            //          match email confirmation code in the database.
            if (storedCode.equals(userEnteredCode)) {
                // The codes match.
                // Set the confirmation code to confirmed and inactive.
                code.get().setConfirmedAtDateTime(LocalDateTime.now());
                code.get().setActive(false);

                // Set the user's email address to confirmed.
                user.get().setEmailConfirmed(true);

                // Save the settings for the user and the email confirmation code entities in the database.
                emailConfirmationDao.save(code.get());
                userDao.save(user.get());

                // Return success response.
                return new ResponseEntity<>(new MessageResponse("EMAIL_CONFIRMED"), HttpStatus.OK);
            } else {

                // Increment failed attempts by one for the specified email confirmation code entity.
                code.get().setIncorrectAttempts(code.get().getIncorrectAttempts() + 1);

                // Check 9: See if failed attempts now equals five (maximum number of attempts for a code entity).
                if (code.get().getIncorrectAttempts() == 5) {

                    // Set isActive to be false (deactivate the email confirmation code entity).
                    code.get().setActive(false);
                    emailConfirmationDao.save(code.get());

                    // The confirmation code has reached the maximum of
                    // five failed confirmation attempts. Throw an error.
                    // TOO_MANY_ATTEMPTS
                    throw new ConfirmCodeException(ConfirmCodeErrorTypes.TOO_MANY_ATTEMPTS);
                } else {

                    emailConfirmationDao.save(code.get());

                    // The code entered by the user does not match the code in the database
                    // for the specified email confirmation code entity. Throw an error.
                    // CODE_MISMATCH
                    throw new ConfirmCodeException(
                            ConfirmCodeErrorTypes.CODE_MISMATCH,
                            code.get().getId(),
                            code.get().getExpiresAtDateTime(),
                            maximumConfirmationAttempts - code.get().getIncorrectAttempts());
                }
            }
        } catch (ConfirmCodeException exception) {
            throw exception;    // Throw any ConfirmCodeExceptions to the Global Error Handler.
        } catch (Exception exception) {
            throw new ConfirmCodeException(ConfirmCodeErrorTypes.BAD_REQUEST);
        }
    }
}