package com.score.backend.domain.user;

import com.score.backend.domain.rank.group.GroupRankService;
import com.score.backend.domain.school.School;
import com.score.backend.dtos.*;
import com.score.backend.exceptions.ExceptionType;
import com.score.backend.exceptions.ScoreCustomException;
import com.score.backend.security.AuthService;
import com.score.backend.domain.notification.NotificationService;
import com.score.backend.domain.school.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.sql.SQLIntegrityConstraintViolationException;

@Tag(name = "User", description = "회원 정보 관리를 위한 API입니다.")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final UserService userService;
    private final SchoolService schoolService;
    private final NotificationService notificationService;
    private final GroupRankService groupRankService;

    // 닉네임 중복 검사
    @Operation(summary = "닉네임 중복 검사", description = "온보딩이나 회원 정보 수정 시 닉네임 중복 검사를 위한 api입니다.")
    @RequestMapping(value = "/score/public/{nickname}/exists", method = RequestMethod.GET)
    @ApiResponse(responseCode = "200", description = "닉네임 중복 검사 완료. ResponseBody의 내용이 0이면 필드에 아무 것도 입력되지 않은 경우, 1이면 중복되지 않은 닉네임인 경우, -1이면 이미 존재하는 닉네임인 경우.")
    public ResponseEntity<Integer> checkNicknameUniqueness(@Parameter(description = "유저가 필드에 입력한 닉네임") @PathVariable(name = "nickname") String nickname) {
        if (nickname.isEmpty()) {
            return ResponseEntity.ok(0); // 필드에 아무것도 입력되지 않은 상태인 경우
        }
        if (userService.findUserByNickname(nickname).isEmpty()) {
            return ResponseEntity.ok(1); // 중복되지 않는 닉네임인 경우
        } else {
            return ResponseEntity.ok(-1); // 이미 존재하는 닉네임인 경우
        }
    }

    @Operation(summary = "유저 정보 응답", description = "유저의 정보를 응답하기 위한 api입니다.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "유저 정보 응답 완료"),
                    @ApiResponse(responseCode = "404", description = "user not found")}
    )
    @GetMapping("/score/user/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@Parameter(description = "정보를 요청한 유저의 고유 id 값") @RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(UserInfoResponse.of(userService.findUserById(id)));
    }

    @Operation(summary = "유저의 알림 수신 여부 설정 현황", description = "유저가 각 알림 항목별 설정 현황을 응답하기 위한 api입니다.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "유저의 알림 설정 현황 정보 응답 완료"),
                    @ApiResponse(responseCode = "404", description = "user not found")}
    )
    @GetMapping(value = "/score/user/info/notification")
    public ResponseEntity<NotificationStatusResponse> getNotificationStatus(@Parameter(description = "유저의 고유 id 값") @RequestParam(name = "id") Long userId) {
        return ResponseEntity.ok(NotificationStatusResponse.of(userService.findUserById(userId)));
    }

    // 온보딩에서 회원 정보 입력 완료시
    @Operation(summary = "신규 회원 정보 저장", description = "온보딩에서 회원 정보가 입력이 완료될 경우 수행되는 요청입니다. 해당 정보를 db에 저장하고 로그인을 진행합니다.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "신규 회원 정보 저장 완료. 회원의 고유 ID 값 응답."),
                    @ApiResponse(responseCode = "400", description = "Bad Request")}
    )
    @RequestMapping(value = "/score/public/onboarding/fin", method = RequestMethod.POST)
    public ResponseEntity<JwtTokenResponse> saveNewUser(@Parameter(description = "회원 정보 전달을 위한 DTO", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) @RequestPart(value = "userDto") UserDto userDto,
                                              @Parameter(description = "학교 정보 전달을 위한 DTO", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) @RequestPart(value = "schoolDto") SchoolDto schoolDto,
                                              @Parameter(description = "프로필 사진", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws ParseException, IOException {

        // 유저의 학교 정보가 이미 db에 존재하면 그 학교 정보를 찾기, 없으면 새로운 학교 엔티티 생성하기.
        School school = schoolService.findOrSave(schoolDto);
        // User 엔티티 생성
        User user = userDto.toEntity(authService.getUserId(userDto.getProvider(), userDto.getIdToken()));
        // 유저 엔티티에 학교 정보 set
        user.setSchoolAndStudent(school);
        return ResponseEntity.ok(new JwtTokenResponse(userService.saveUser(user, userDto.getProfileImgId(), multipartFile), authService.setJwtToken(userDto.getProvider(), userDto.getIdToken())));
    }

    // 회원 탈퇴
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴 요청 발생시 해당 회원의 모든 정보를 db에서 삭제합니다.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "회원 탈퇴 완료"),
                    @ApiResponse(responseCode = "404", description = "User Not Found"),
                    @ApiResponse(responseCode = "400", description = "그룹의 방장인 경우 탈퇴할 수 없음")
            })
    @RequestMapping(value = "/score/user/withdrawal", method = RequestMethod.DELETE)
    public ResponseEntity<String> withdrawUser(@Parameter(description = "회원 탈퇴를 요청한 유저의 고유 id 값") @RequestParam(name = "id") Long id,
                                                   @Parameter(description = "회원 탈퇴 사유") @RequestParam(name = "reason") String reason) {
        try {
            groupRankService.handleWithdrawUsersRankingInfo(id);
            userService.withdrawUser(id);
            Logger logger = LoggerFactory.getLogger("withdrawal-logger");
            logger.info("User ID: {}, Reason: {}", id, reason);
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new ScoreCustomException(ExceptionType.GROUP_ADMIN_WITHDRAWAL);
        }
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    // 회원 정보 수정
    @Operation(summary = "회원 정보 수정", description = "수정된 회원 정보를 db에 업데이트합니다.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "회원 정보 수정 완료"),
                    @ApiResponse(responseCode = "409", description = "마지막 학교 정보 수정 후 30일이 경과되기 전 학교 정보 수정 시도"),
                    @ApiResponse(responseCode = "404", description = "User Not Found")
            })
    @PatchMapping(value = "/score/user/update/{id}")
    public ResponseEntity<String> updateUserInfo(@Parameter(description = "회원 정보 수정을 요청한 유저의 고유 id 값") @PathVariable(name = "id") Long userId,
                                                 @Parameter(description = "수정된 회원 정보 전달을 위한 DTO", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)) @RequestPart(value = "userUpdateDto") UserUpdateDto userUpdateDto,
                                                 @Parameter(description = "프로필 사진", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        User user = userService.findUserById(userId);
        if (userUpdateDto.getSchool() != null && !user.getSchool().getSchoolCode().equals(userUpdateDto.getSchool().getSchoolCode())
                && ChronoUnit.DAYS.between(LocalDateTime.now(), user.getSchoolUpdatedAt()) < 30) {
            throw new ScoreCustomException(ExceptionType.TOO_FREQUENT_SCHOOL_CHANGING);
        }
        userService.updateUser(userId, userUpdateDto, multipartFile);
        return ResponseEntity.ok("회원 정보 수정이 완료되었습니다.");
    }

    @Operation(summary = "알림 수신 여부 설정 수정", description = "알림 수신 여부 변경 사항을 db에 업데이트합니다.")
    @ApiResponses(
            value = {@ApiResponse(responseCode = "200", description = "알림 수신 여부 수정 완료"),
                    @ApiResponse(responseCode = "404", description = "User Not Found")
            })
    @RequestMapping(value = "/score/user/setting/notification", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUserNotificationStatus(@Parameter(description = "수정된 알림 수신 여부 정보 전달을 위한 DTO") @RequestBody NotificationStatusRequest request) {
        notificationService.changeNotificationReceivingStatus(userService.findUserById(request.getUserId()), request);
        return ResponseEntity.ok("알림 수신 여부 수정이 완료되었습니다.");
    }
}
